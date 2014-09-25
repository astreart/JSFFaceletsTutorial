package bg.fmi.master.thesis.utils;

import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.hibernate.Session;

import bg.fmi.master.thesis.models.TAgencyEventType;
import bg.fmi.master.thesis.models.TEventType;
import bg.fmi.master.thesis.models.TRole;
import bg.fmi.master.thesis.models.TUser;
import bg.fmi.master.thesis.utils.HibernateUtil;

public class App {

	public static void main(String[] args) {

		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		
		String version = FacesContext.class.getPackage().getImplementationVersion();
		System.out.println("JSF version : " + version);

		System.out.println(System.getProperty("file.encoding"));
		em.close();
	}
}
