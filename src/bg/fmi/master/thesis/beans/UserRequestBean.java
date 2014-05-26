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
	

	public UserRequestBean(){
		 System.out.println(request);
		EntityManager em = HibernateUtil.getEntityManager();
		Query query = em.createQuery("select r " +
				"from TUser u join u.userRequests r where u.id = :userId and r.isActive = 'Y'");
		query.setParameter("userId", Long.valueOf(11));
		userActiveRequests = query.getResultList();
		for (TRequest a: userActiveRequests){
			System.out.println(a.getTitle());
		}
	}

	public void cancelRequest (){
        EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		request.setIsCancelled(true);
		request.setIsActive(false);
		em.merge(request);
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
	
}