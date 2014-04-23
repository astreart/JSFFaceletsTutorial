package bg.fmi.master.thesis.beans;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import bg.fmi.master.thesis.model.TEventType;
import bg.fmi.master.thesis.model.TUser;
import bg.fmi.master.thesis.util.HibernateUtil;

@ManagedBean(name = "eventTypeBean")
@SessionScoped
public class EventTypeBean {

	private TEventType tEventType = new TEventType();

	public void addEventType() {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		TEventType newEventType = new TEventType(tEventType);
		System.out.println("newEventType: " + newEventType);
		try {
			em.persist(newEventType);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		em.getTransaction().commit();
	}

	public void listEventType() {
		EntityManager em = HibernateUtil.getEntityManager();
		// read the existing entries and write to console
		Query q = em.createQuery("select u from TEventType u");
		List<TEventType> eventTypeList = q.getResultList();
		for (TEventType eventType : eventTypeList) {
			System.out.println(eventType);
		}
		System.out.println("Size: " + eventTypeList.size());
	}

	public TEventType gettEventType() {
		return tEventType;
	}

	public void settEventType(TEventType tEventType) {
		this.tEventType = tEventType;
	}

}
