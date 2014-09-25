package bg.fmi.master.thesis.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

	private static final String PERSISTENCE_UNIT_NAME = "myapp";

	private static final EntityManager em = buildEntityManager();

	private static EntityManager buildEntityManager() {
		try {

			EntityManagerFactory factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();
			;

			System.out.println("createEntityManager");

			return em;

		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial EntityManager creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static EntityManager getEntityManager() {
		return em;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getEntityManager().close();
	}
}