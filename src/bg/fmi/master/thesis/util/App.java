package bg.fmi.master.thesis.util;

import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.hibernate.Session;

import bg.fmi.master.thesis.model.TAgencyEventType;
import bg.fmi.master.thesis.model.TEventType;
import bg.fmi.master.thesis.model.TRole;
import bg.fmi.master.thesis.model.TUser;
import bg.fmi.master.thesis.util.HibernateUtil;

public class App {

	public static void main(String[] args) {

		System.out.println("Hibernate many to many (Annotation)");
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();

		System.out.println("begin");
		
		/*TRole tRole = new TRole("Agency");
		
		TUser newUser = new TUser("ivan123", "1234", "Ivan", "Ivanov", "91933", "2341", tRole);
		
		System.out.println(newUser);
		
		TEventType tEventType = new TEventType("CONSUMER", "CONSUMER COMPANY");

		Set<TEventType> tEventTypes = new HashSet<TEventType>();
		tEventTypes.add(tEventType);

		TAgencyEventType tAgencyEventType = new TAgencyEventType(newUser,
				tEventType);

		em.merge(newUser);

		em.getTransaction().commit();*/
		
		String version = FacesContext.class.getPackage().getImplementationVersion();
		System.out.println("JSF version : " + version);
		System.out.println("Done");
		em.close();
	}
}
