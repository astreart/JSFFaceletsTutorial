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

import bg.fmi.master.thesis.model.TAgency;
import bg.fmi.master.thesis.model.TEventType;
import bg.fmi.master.thesis.model.TUser;
import bg.fmi.master.thesis.util.HibernateUtil;

@ManagedBean(name = "agencyBean")
@SessionScoped
public class AgencyBean {

	private TAgency tEventType = new TAgency();

	public void listAgencies() {
		EntityManager em = HibernateUtil.getEntityManager();
		// read the existing entries and write to console
		Query q = em.createQuery("select u from TAgency u");
		List<TAgency> agencyList = q.getResultList();
		for (TAgency agency : agencyList) {
			System.out.println("NAme: " + agency.gettUser().getName());

		}
	}

	public TAgency gettEventType() {
		return tEventType;
	}

	public void settEventType(TAgency tEventType) {
		this.tEventType = tEventType;
	}



}
