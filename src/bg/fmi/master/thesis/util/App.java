package bg.fmi.master.thesis.util;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.hibernate.Session;

import bg.fmi.master.thesis.model.TEventType;
import bg.fmi.master.thesis.model.TUser;
import bg.fmi.master.thesis.util.HibernateUtil;


public class App {
	

	public static void main(String[] args) {

		System.out.println("Hibernate many to many (Annotation)");
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();

		TUser newUser = new TUser();
		newUser.setId('1');
		newUser.setUsername("Ivan");
		newUser.setPassword("123456");
		newUser.setLastName("Ivanov");
		newUser.setEmail("ivan@abv.bg");
		newUser.setPhone("+359888100100");
		

		TEventType tEventType = new TEventType((long) 1,"CONSUMER", "CONSUMER COMPANY");
		TEventType eventType2 = new TEventType((long) 2,"INVESTMENT", "INVESTMENT COMPANY");

		Set<TEventType> tEventTypes = new HashSet<TEventType>();
		tEventTypes.add(tEventType);
		tEventTypes.add(eventType2);

	    //newUser.setTAgencyEventTypes(tAgencyEventTypes)(tEventTypes);

		em.merge(newUser);

		em.getTransaction().commit();
		System.out.println("Done");
		em.close();
	}
}
