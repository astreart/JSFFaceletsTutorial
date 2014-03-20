package bg.fmi.master.thesis.beans;

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

@ManagedBean(name="eventTypeBean")
@SessionScoped
public class EventTypeBean {
	
    private static final String PERSISTENCE_UNIT_NAME = "myapp";
	private static EntityManagerFactory factory;
	private TEventType tEventType = new TEventType();
	
	
	public void addEventType() {
		    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		    EntityManager em = factory.createEntityManager();
		    em.getTransaction().begin();
		    System.out.println("tEventType: " + tEventType);
		    TEventType newEventType = new TEventType(tEventType);
		    System.out.println("newEventType: " + newEventType);
		    em.persist(newEventType);
		    em.getTransaction().commit();

		    em.close();
		  }
	public void listEventType() {
	    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    EntityManager em = factory.createEntityManager();
	    // read the existing entries and write to console
	   Query q = em.createQuery("select u from TEventType u");
	    List<TEventType> eventTypeList = q.getResultList();
	    for (TEventType eventType : eventTypeList) {
	      System.out.println(eventType);
	    }
	    System.out.println("Size: " + eventTypeList.size());
	    em.close();
	}
	
	public TEventType gettEventType() {
		return tEventType;
	}
	public void settEventType(TEventType tEventType) {
		this.tEventType = tEventType;
	}	

}
