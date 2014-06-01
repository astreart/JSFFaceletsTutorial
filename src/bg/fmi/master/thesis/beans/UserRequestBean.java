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
	private Map<TRequest, List<TAgency>> requestAgencies = new HashMap<TRequest, List<TAgency>>();
	
	public UserRequestBean(){
		EntityManager em = HibernateUtil.getEntityManager();
		Query query = em.createQuery("select r " +
				"from TUser u join u.userRequests r where u.id = :userId and r.isActive = 'Y'");
		query.setParameter("userId", Long.valueOf(11));
		userActiveRequests = query.getResultList();
		
		for (TRequest req: userActiveRequests){
			if (req.getHiredAgency() == null){
				Query queryAgencies = em
						.createQuery("select agency.id "
								+ "from TUser agency join agency.sentMessages msg "
								+ "join msg.tRequest req "
								+ "where agency.userRole.id=1 and req.id = :requestId "
								+ "group by agency.id");

				queryAgencies.setParameter("requestId", req.getId());
				
				List<TAgency> agenciesAnsweredRequest = queryAgencies.getResultList();
				requestAgencies.put(req, agenciesAnsweredRequest);
			}
			System.out.print("TEST");
		}
	}

	public String cancelRequest(){
        EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		request.setIsCancelled(true);
		request.setIsActive(false);
		em.merge(request);
		em.getTransaction().commit();
		//remove the object from the List
		userActiveRequests.remove(request);
		return "cancelRequest";
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

	public Map<TRequest, List<TAgency>> getRequestAgencies() {
		return requestAgencies;
	}

	public void setRequestAgencies(Map<TRequest, List<TAgency>> requestAgencies) {
		this.requestAgencies = requestAgencies;
	}
}