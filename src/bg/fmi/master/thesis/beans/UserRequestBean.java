package bg.fmi.master.thesis.beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Parameter;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.primefaces.event.RateEvent;

import com.sun.mail.handlers.message_rfc822;

import bg.fmi.master.thesis.model.TAgency;
import bg.fmi.master.thesis.model.TComment;
import bg.fmi.master.thesis.model.TFilterType;
import bg.fmi.master.thesis.model.TMessage;
import bg.fmi.master.thesis.model.TRequest;
import bg.fmi.master.thesis.model.TRequestFilter;
import bg.fmi.master.thesis.model.TUser;
import bg.fmi.master.thesis.util.HibernateUtil;

@ManagedBean(name = "userRequestBean")
@SessionScoped
public class UserRequestBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<TRequest> userActiveRequests;
	private List<TRequest> userCompletedRequests;
	private List<TRequest> userCancelledRequests;
	private TRequest request = new TRequest();
	private Map<TRequest, List<TUser>> requestAgencies = new HashMap<TRequest, List<TUser>>();
	private Map<TRequest, List<TUser>> cancelledRequestAgencies = new HashMap<TRequest, List<TUser>>();
	private Map<TRequest, List<TUser>> completedRequestAgencies = new HashMap<TRequest, List<TUser>>();
	private Map<TRequest, Integer> requestComment = new HashMap<TRequest, Integer>();
	private Map<TRequest, Integer> completedRequestComment = new HashMap<TRequest, Integer>();
	private String selectedAgencyId;
	private TMessage message = new TMessage();
	private Long messageToAgencyId;
	private Integer assessment;
	private RatingBean ratingBean = new RatingBean();
	private Boolean isEvaluated;
	private String negativeComment;
	private String positiveComment;
	private Map<TRequest, String> requestPositiveComment = new HashMap<TRequest, String>();
	private Map<TRequest, String> requestNegativeComment = new HashMap<TRequest, String>();

	public String getNegativeComment() {
		return negativeComment;
	}

	public void setNegativeComment(String negativeComment) {
		this.negativeComment = negativeComment;
	}

	public String getPositiveComment() {
		return positiveComment;
	}

	public void setPositiveComment(String positiveComment) {
		this.positiveComment = positiveComment;
	}

	public void onrate(RateEvent rateEvent) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Оценяване",
				"За цялостното организиране на събитието, гласувахте с "
						+ ((Integer) rateEvent.getRating()).intValue()
						+ " точки."
						+ " За да се счита вашата заявка за приключила,"
						+ " трябва да маркирате заявката си като оценена!");

		FacesContext.getCurrentInstance().addMessage(null, message);

		rateAgency((Integer) rateEvent.getRating());

	}

	public void rateAgency(Integer rating) {
		EntityManager em = HibernateUtil.getEntityManager();
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();

		Query queryAgencyComment = em.createQuery("select comment "
				+ "from TRequest req join req.requestComments comment "
				+ "where req.id = :requestId ");

		Long requestId = (Long) request.getId();
		queryAgencyComment.setParameter("requestId", requestId);

		TComment agencyComment = null;
		try {
			agencyComment = (TComment) queryAgencyComment.getSingleResult();
		} catch (NoResultException nre) {
		}

		if (agencyComment == null) {
			agencyComment = new TComment();
			agencyComment.settRequest(request);
			agencyComment.setCommentDate(new Date());
			agencyComment.setAssessment(rating);
			em.persist(agencyComment);
		} else {
			agencyComment.setAssessment(rating);
			em.merge(agencyComment);
		}
		em.getTransaction().commit();
		ratingBean.agencyAssessment();
	}

	public void commentAgency() {
		EntityManager em = HibernateUtil.getEntityManager();
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();

		Query queryAgencyComment = em.createQuery("select comment "
				+ "from TRequest req join req.requestComments comment "
				+ "where req.id = :requestId ");

		Long requestId = (Long) request.getId();
		queryAgencyComment.setParameter("requestId", requestId);

		TComment agencyComment = null;
		try {
			agencyComment = (TComment) queryAgencyComment.getSingleResult();
		} catch (NoResultException nre) {
		}

		if (agencyComment == null) {
			agencyComment = new TComment();
			agencyComment.settRequest(request);
			agencyComment.setCommentDate(new Date());
			agencyComment.setPositiveComment(requestPositiveComment
					.get(request));
			agencyComment.setNegativeComment(requestNegativeComment
					.get(request));
			em.persist(agencyComment);
		} else {
			agencyComment.setPositiveComment(requestPositiveComment
					.get(request));
			agencyComment.setNegativeComment(requestNegativeComment
					.get(request));
			em.merge(agencyComment);
		}
		em.getTransaction().commit();
	}

	public String markRequestAsCompleted() {
		EntityManager em = HibernateUtil.getEntityManager();
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		request.setIsActive(false);
		em.merge(request);
		em.getTransaction().commit();
		userCompletedRequests.add(request);
		userActiveRequests.remove(request);
		return "userCompletedRequests";
	}

	public UserRequestBean() {
		EntityManager em = HibernateUtil.getEntityManager();
		Query query = em
				.createQuery("select r "
						+ "from TUser u join u.userRequests r where u.id = :userId and r.isActive = 'Y'");
		// TODO: Depends on the logged user
		query.setParameter("userId", Long.valueOf(11));
		userActiveRequests = query.getResultList();

		for (TRequest req : userActiveRequests) {
			if (req.getHiredAgency() == null) {
				Query queryAgencies = em
						.createQuery("select agency.name, agency.id "
								+ "from TUser agency join agency.sentMessages msg "
								+ "join msg.tRequest req "
								+ "where agency.userRole.id=1 and req.id = :requestId "
								+ "group by agency.id, agency.name");

				queryAgencies.setParameter("requestId", req.getId());

				List<TUser> agenciesAnsweredRequest = queryAgencies
						.getResultList();
				requestAgencies.put(req, agenciesAnsweredRequest);
			} else {
				Query queryAgencyRating = em.createQuery("select comment "
						+ "from TRequest req join req.requestComments comment "
						+ "where req.id = :requestId ");
				queryAgencyRating.setParameter("requestId", (Long) req.getId());
				/*
				 * try { assessment = (Integer)
				 * queryAgencyRating.getSingleResult(); } catch
				 * (NoResultException nre) { assessment = 0; }
				 */try {
					TComment result = (TComment) queryAgencyRating
							.getSingleResult();
					assessment = result.getAssessment();
					positiveComment = result.getPositiveComment();
					negativeComment = result.getNegativeComment();
				} catch (NoResultException nre) {
					assessment = 0;
					positiveComment = null;
					negativeComment = null;
				}

				requestComment.put(req, (Integer) assessment);
				requestNegativeComment.put(req, negativeComment);
				requestPositiveComment.put(req, positiveComment);
			}
		}

		// Completed Requests
		Query queryCompletedRequests = em
				.createQuery("select r "
						+ "from TUser u join u.userRequests r where u.id = :userId and r.isActive = 'N' and r.isCancelled='N'");
		// TODO: Depends on the logged user
		queryCompletedRequests.setParameter("userId", Long.valueOf(11));
		userCompletedRequests = queryCompletedRequests.getResultList();

		for (TRequest req : userCompletedRequests) {
			if (req.getHiredAgency() == null) {
				Query queryAgencies = em
						.createQuery("select agency.name, agency.id "
								+ "from TUser agency join agency.sentMessages msg "
								+ "join msg.tRequest req "
								+ "where agency.userRole.id=1 and req.id = :requestId "
								+ "group by agency.id, agency.name");

				queryAgencies.setParameter("requestId", req.getId());

				List<TUser> agenciesAnsweredRequest = queryAgencies
						.getResultList();
				completedRequestAgencies.put(req, agenciesAnsweredRequest);
			} else {
				Query queryAgencyRating = em
						.createQuery("select comment.assessment "
								+ "from TRequest req join req.requestComments comment "
								+ "where req.id = :requestId ");
				queryAgencyRating.setParameter("requestId", (Long) req.getId());
				try {
					assessment = (Integer) queryAgencyRating.getSingleResult();
				} catch (NoResultException nre) {
					assessment = 0;
				}
				completedRequestComment.put(req, (Integer) assessment);
			}
		}

		// Cancelled Requests
		Query queryCancelledRequests = em
				.createQuery("select r "
						+ "from TUser u join u.userRequests r where u.id = :userId and r.isActive = 'N' and r.isCancelled='Y'");
		// TODO: Depends on the logged user
		queryCancelledRequests.setParameter("userId", Long.valueOf(11));
		userCancelledRequests = queryCancelledRequests.getResultList();

		for (TRequest req : userCancelledRequests) {
			if (req.getHiredAgency() == null) {
				Query queryAgencies = em
						.createQuery("select agency.name, agency.id "
								+ "from TUser agency join agency.sentMessages msg "
								+ "join msg.tRequest req "
								+ "where agency.userRole.id=1 and req.id = :requestId "
								+ "group by agency.id, agency.name");

				queryAgencies.setParameter("requestId", req.getId());

				List<TUser> agenciesAnsweredRequest = queryAgencies
						.getResultList();
				cancelledRequestAgencies.put(req, agenciesAnsweredRequest);
			}
		}

	}

	public String cancelRequest() {
		EntityManager em = HibernateUtil.getEntityManager();
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		request.setIsCancelled(true);
		request.setIsActive(false);
		em.merge(request);
		em.getTransaction().commit();
		// remove the object from the List
		userActiveRequests.remove(request);
		userCancelledRequests.add(request);
		return "cancelRequest";
	}

	public String hireAgency() {
		EntityManager em = HibernateUtil.getEntityManager();
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();

		Query queryAgency = em.createQuery("select agency "
				+ "from TAgency agency " + "where agency.tUserId = :agencyId ");

		queryAgency.setParameter("agencyId", Long.valueOf(selectedAgencyId));

		TAgency agency = (TAgency) queryAgency.getSingleResult();
		request.setHiredAgency(agency);
		em.merge(request);
		em.getTransaction().commit();

		return "userActiveRequests";
	}

	public List<Object> showMessages(TRequest requestVar, Long agencyId) {
		EntityManager em = HibernateUtil.getEntityManager();

		if (!em.getTransaction().isActive())
			em.getTransaction().begin();

		Long messageGroup = null;

		Query queryMessageGroup = em.createQuery("select msg.messageGroup "
				+ "from TUser user join user.sentMessages msg "
				+ "where user.id = :agencyId and msg.tRequest.id = :requestID "
				+ "group by msg.messageGroup");

		queryMessageGroup.setParameter("requestID",
				Long.valueOf(requestVar.getId()));
		queryMessageGroup.setParameter("agencyId", agencyId);
		messageGroup = (Long) queryMessageGroup.getSingleResult();

		Query queryMessages = em
				.createQuery("select user.name, msg.messageBody, msg.dateSent, msg.messageGroup  "
						+ "from TUser user join user.sentMessages msg "
						+ "where user.id in (:userId, :agencyId) "
						+ "and msg.messageGroup = :messageGroup "
						+ "order by msg.dateSent asc");

		queryMessages.setParameter("userId",
				Long.valueOf(requestVar.getAuthor().getId()));
		queryMessages.setParameter("agencyId", agencyId);
		queryMessages.setParameter("messageGroup", messageGroup);

		List<Object> messages = queryMessages.getResultList();
		return messages;
	}

	public void addMessage(TRequest requestVar) {

		EntityManager em = HibernateUtil.getEntityManager();
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();

		TMessage msg = new TMessage(message);

		msg.setDateSent(new Date());
		msg.settRequest(requestVar);

		Query queryMessageGroup = em.createQuery("select msg.messageGroup "
				+ "from TUser user join user.sentMessages msg "
				+ "where user.id = :agencyId and msg.tRequest.id = :requestID "
				+ "group by msg.messageGroup");

		queryMessageGroup.setParameter("requestID",
				Long.valueOf(requestVar.getId()));
		queryMessageGroup.setParameter("agencyId", messageToAgencyId);
		msg.setMessageGroup((Long) queryMessageGroup.getSingleResult());

		msg.setMessageBody(message.getMessageBody());

		message.setMessageBody(null);

		// TODO: Depends on the logged user
		Query q = em.createQuery("select u from TUser u where u.id = 11");
		TUser tUser = (TUser) q.getSingleResult();
		msg.settUser(tUser);

		try {
			em.persist(msg);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}

		em.getTransaction().commit();

	}

	public List<TRequest> getUserActiveRequests() {
		return userActiveRequests;
	}

	public void setUserActiveRequests(List<TRequest> userActiveRequests) {
		this.userActiveRequests = userActiveRequests;
	}

	public TRequest getRequest() {
		return request;
	}

	public void setRequest(TRequest request) {
		this.request = request;
	}

	public Map<TRequest, List<TUser>> getRequestAgencies() {
		return requestAgencies;
	}

	public void setRequestAgencies(Map<TRequest, List<TUser>> requestAgencies) {
		this.requestAgencies = requestAgencies;
	}

	public String getSelectedAgencyId() {
		return selectedAgencyId;
	}

	public void setSelectedAgencyId(String selectedAgencyId) {
		this.selectedAgencyId = selectedAgencyId;
	}

	public TMessage getMessage() {
		return message;
	}

	public void setMessage(TMessage message) {
		this.message = message;
	}

	public void setMessageToAgencyIdValue(Long messageToAgencyId) {
		this.messageToAgencyId = messageToAgencyId;
	}

	public List<TRequest> getUserCompletedRequests() {
		return userCompletedRequests;
	}

	public void setUserCompletedRequests(List<TRequest> userCompletedRequests) {
		this.userCompletedRequests = userCompletedRequests;
	}

	public List<TRequest> getUserCancelledRequests() {
		return userCancelledRequests;
	}

	public void setUserCancelledRequests(List<TRequest> userCancelledRequests) {
		this.userCancelledRequests = userCancelledRequests;
	}

	public Map<TRequest, List<TUser>> getCancelledRequestAgencies() {
		return cancelledRequestAgencies;
	}

	public void setCancelledRequestAgencies(
			Map<TRequest, List<TUser>> cancelledRequestAgencies) {
		this.cancelledRequestAgencies = cancelledRequestAgencies;
	}

	public Map<TRequest, List<TUser>> getCompletedRequestAgencies() {
		return completedRequestAgencies;
	}

	public void setCompletedRequestAgencies(
			Map<TRequest, List<TUser>> completedRequestAgencies) {
		this.completedRequestAgencies = completedRequestAgencies;
	}

	public Map<TRequest, Integer> getRequestComment() {
		return requestComment;
	}

	public void setRequestComment(Map<TRequest, Integer> requestComment) {
		this.requestComment = requestComment;
	}

	public Map<TRequest, Integer> getCompletedRequestComment() {
		return completedRequestComment;
	}

	public void setCompletedRequestComment(
			Map<TRequest, Integer> completedRequestComment) {
		this.completedRequestComment = completedRequestComment;
	}

	public Boolean getIsEvaluated() {
		return isEvaluated;
	}

	public void setIsEvaluated(Boolean isEvaluated) {
		this.isEvaluated = isEvaluated;
	}

	public Map<TRequest, String> getRequestPositiveComment() {
		return requestPositiveComment;
	}

	public void setRequestPositiveComment(
			Map<TRequest, String> requestPositiveComment) {
		this.requestPositiveComment = requestPositiveComment;
	}

	public Map<TRequest, String> getRequestNegativeComment() {
		return requestNegativeComment;
	}

	public void setRequestNegativeComment(
			Map<TRequest, String> requestNegativeComment) {
		this.requestNegativeComment = requestNegativeComment;
	}
}