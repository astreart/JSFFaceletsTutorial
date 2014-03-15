package util;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.hibernate.Session;

import model.EventType;
import model.User;
import util.HibernateUtil;

public class App {
	

	public static void main(String[] args) {

		System.out.println("Hibernate many to many (Annotation)");
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();

		User newUser = new User();
		newUser.setId('1');
		newUser.setUsername("Ivan");
		newUser.setPassword("123456");
		newUser.setLastName("Ivanov");
		newUser.setEmail("ivan@abv.bg");
		newUser.setPhone("+359888100100");
		

		EventType eventType = new EventType("CONSUMER", "CONSUMER COMPANY");
		EventType eventType2 = new EventType("INVESTMENT", "INVESTMENT COMPANY");

		Set<EventType> eventTypes = new HashSet<EventType>();
		eventTypes.add(eventType);
		eventTypes.add(eventType2);

		newUser.setEventTypes(eventTypes);

		em.merge(newUser);

		em.getTransaction().commit();
		System.out.println("Done");
		em.close();
	}
}
