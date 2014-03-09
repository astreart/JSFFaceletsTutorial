package util;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.EventType;
import model.User;


public class App {
	private static final String PERSISTENCE_UNIT_NAME = "myapp";
	private static EntityManagerFactory factory;

	public static void main(String[] args) {
	
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    EntityManager em = factory.createEntityManager();
 
	    em.getTransaction().begin();
		
       // user.setUsername("PADINI");
        

	    
	    
	    User newUser = new User();
	    em.persist(newUser);
        
        EventType eventType = new EventType("CONSUMER", "CONSUMER COMPANY");
        EventType eventType2 = new EventType("INVESTMENT", "INVESTMENT COMPANY");
 
        Set<EventType> eventTypes = new HashSet<EventType>();
        eventTypes.add(eventType);
        eventTypes.add(eventType2);
 
        newUser.setEventTypes(eventTypes);
        
        em.persist(newUser);
	    em.getTransaction().commit();

	System.out.println("Done");
	}

}