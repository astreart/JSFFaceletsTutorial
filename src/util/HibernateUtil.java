package util;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
 
public class HibernateUtil {
 
	private static final String PERSISTENCE_UNIT_NAME = "myapp";
	private static EntityManagerFactory factory;
	private static EntityManager em = buildSessionFactory();
 
	private static EntityManager buildSessionFactory() {
		try {
			
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();
			
			return em;
 
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
 
	public static EntityManager getEntityManager() {
		return em;
	}
 
}