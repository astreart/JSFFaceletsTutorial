package bg.fmi.master.thesis.beans;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import bg.fmi.master.thesis.model.TAgency;
import bg.fmi.master.thesis.util.HibernateUtil;

@ManagedBean(name = "agencyBean")
@ViewScoped
public class AgencyBean {

	private TAgency tAgency = new TAgency();
	private List<TAgency> listAgencies;

	/*public List<TAgency> listAgencies() {
		EntityManager em = HibernateUtil.getEntityManager();
		// read the existing entries and write to console
		Query q = em.createQuery("select u from TAgency u");
		List<TAgency> agencyList = q.getResultList();
		for (TAgency agency : agencyList) {
			System.out.println("NAme: " + agency.gettUser().getName());

		}
		return agencyList;
	}
*/

	@PostConstruct
	public void init(){
		EntityManager em = HibernateUtil.getEntityManager();
		// read the existing entries and write to console
		Query q = em.createQuery("select u from TAgency u");
		listAgencies = q.getResultList();
		for (TAgency agency : listAgencies) {
			System.out.println("NAme: " + agency.gettUser().getName());

		}
	}
	
	public TAgency gettAgency() {
		return tAgency;
	}


	public void settAgency(TAgency tAgency) {
		this.tAgency = tAgency;
	}


	public List<TAgency> getListAgencies() {
		return listAgencies;
	}


	public void setListAgencies(List<TAgency> listAgencies) {
		this.listAgencies = listAgencies;
	}

	
}
