package bg.fmi.master.thesis.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.primefaces.event.RateEvent;

import bg.fmi.master.thesis.model.TEventType;
import bg.fmi.master.thesis.model.TRequest;
import bg.fmi.master.thesis.model.TUser;
import bg.fmi.master.thesis.util.HibernateUtil;

@ManagedBean(name = "requestBean")
@SessionScoped
public class RequestBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private TRequest tRequest = new TRequest();

	public TRequest gettRequest() {
		return tRequest;
	}

	public void settRequest(TRequest tRequest) {
		this.tRequest = tRequest;
	}
	
	public void addRequest() {
		System.out.println("RequestBean " );
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		
		System.out.println("request: " + tRequest.getTitle());
		TRequest newRequest = new TRequest(tRequest);
		
		System.out.println("newRequest: " + newRequest);
		
		newRequest.setRequestDate(new Date());
		
		Query q = em.createQuery("select u from TUser u");
		List<TUser> usersList = q.getResultList();
		for (TUser user : usersList) {
			System.out.println("User: " + user);
		}
		
		TUser author1 = new TUser();
		for (TUser tUser : usersList) {
			if (tUser.getId() == (Long.valueOf(2)))
				author1 = tUser;
		}

		System.out.println("Author: " + author1.getName()); 
		
		newRequest.setAuthor(author1);
		try {
			em.persist(newRequest);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		em.getTransaction().commit();
	}

}