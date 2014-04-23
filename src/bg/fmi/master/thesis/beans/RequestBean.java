package bg.fmi.master.thesis.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.primefaces.event.RateEvent;

import bg.fmi.master.thesis.model.TEventType;
import bg.fmi.master.thesis.model.TRequest;
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
		try {
			em.persist(newRequest);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		em.getTransaction().commit();
	}

}