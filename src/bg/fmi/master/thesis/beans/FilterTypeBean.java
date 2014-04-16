package bg.fmi.master.thesis.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import bg.fmi.master.thesis.model.TEventType;
import bg.fmi.master.thesis.model.TFilterType;
import bg.fmi.master.thesis.model.TUser;
import bg.fmi.master.thesis.util.HibernateUtil;

@ManagedBean(name = "filterTypeBean")
@SessionScoped
public class FilterTypeBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private TFilterType tFilterType = new TFilterType();
	
	private List<TFilterType> selectedFilterTypes;

	public void addFilterType() {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		System.out.println("tFilterType: " + tFilterType);
		TFilterType newFilterType = new TFilterType(tFilterType);
		System.out.println("newEventType: " + newFilterType);
		try {
			em.persist(newFilterType);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		em.getTransaction().commit();
	}

	public List<TFilterType> listFilterTypes() {
		EntityManager em = HibernateUtil.getEntityManager();
		// read the existing entries and write to console
		Query q = em.createQuery("select u from TFilterType u");
		List<TFilterType> eventFilterList = q.getResultList();
		for (TFilterType filterType : eventFilterList) {
			System.out.println(filterType.getFilterTypeName());
		}
		return eventFilterList;
	}

	public TFilterType gettFilterType() {
		return tFilterType;
	}

	public void settFilterType(TFilterType tFilterType) {
		this.tFilterType = tFilterType;
	}

	public List<TFilterType> getSelectedFilterTypes() {
		return selectedFilterTypes;
	}

	public void setSelectedFilterTypes(List<TFilterType> selectedFilterTypes) {
		this.selectedFilterTypes = selectedFilterTypes;
	}

}
