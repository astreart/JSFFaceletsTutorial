package bg.fmi.master.thesis.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.Persistence;
import javax.persistence.Query;

import bg.fmi.master.thesis.model.TAgency;
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
	private TRequest request = new TRequest();
	private Map<TRequest, List<TUser>> requestAgencies = new HashMap<TRequest, List<TUser>>();
	public String selectedAgencyId;
	private TMessage message = new TMessage();

	public UserRequestBean() {
	    EntityManager em = HibernateUtil.getEntityManager();
		Query query = em
				.createQuery("select r "
						+ "from TUser u join u.userRequests r where u.id = :userId and r.isActive = 'Y'");
		//TODO: Depends on the logged user
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
			}
		}
	}

	public String cancelRequest() {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		request.setIsCancelled(true);
		request.setIsActive(false);
		em.merge(request);
		em.getTransaction().commit();
		// remove the object from the List
		userActiveRequests.remove(request);
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

	// HERE I AM
	public List<Object> showMessages(TRequest requestVar, Long agencyId) {
		EntityManager em = HibernateUtil.getEntityManager();
		
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();

		System.out.println("AgencyID: " + agencyId);

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
	
	public void addMessage(TRequest requestVar){//, Long messageGroupVar){
		System.out.println("TEST");
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		TMessage msg= new TMessage(message);

		msg.setDateSent(new Date());
		msg.settRequest(requestVar);
		//msg.setMessageGroup(messageGroupVar);
		msg.setMessageGroup(Long.valueOf(1));

		//TODO: Depends on the logged user
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
}