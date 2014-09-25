package bg.fmi.master.thesis.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import bg.fmi.master.thesis.models.TFilterType;
import bg.fmi.master.thesis.utils.HibernateUtil;

@ManagedBean(name = "filterTypeBean")
@RequestScoped
public class FilterTypeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private TFilterType tFilterType = new TFilterType();
	private List<TFilterType> selectedFilterTypes;
	private List<String> selectedFilterTypesNames;
	private List<TFilterType> selectedBooleanFilterTypes = new ArrayList<TFilterType>();

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
		setSelectedFilterTypesNames(selectedFilterTypes);
	}

	public String getSelectedFilterTypesArray() {
		return selectedFilterTypes.toString();
	}

	public List<String> getSelectedFilterTypesNames() {
		return selectedFilterTypesNames;
	}

	public void setSelectedFilterTypesNames(
			List<TFilterType> selectedFilterTypes) {
		for (TFilterType filterType : selectedFilterTypes) {
			selectedFilterTypesNames.add(filterType.getFilterTypeName());
		}
	}

	public List<TFilterType> getSelectedBooleanFilterTypes() {
		return selectedBooleanFilterTypes;
	}

	public void setSelectedBooleanFilterTypes(
			List<TFilterType> selectedBooleanFilterTypes) {
		this.selectedBooleanFilterTypes = selectedBooleanFilterTypes;
	}

	public void addFilterType() {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		TFilterType newFilterType = new TFilterType(tFilterType);
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
		return eventFilterList;
	}

	public List<TFilterType> listBooleanFilterTypes() {
		EntityManager em = HibernateUtil.getEntityManager();
		Query q = em
				.createQuery("select u from TFilterType u where u.filterType = 'B'");
		List<TFilterType> resultList = q.getResultList();
		return resultList;
	}

	public List<TFilterType> listTextFilterTypes() {
		EntityManager em = HibernateUtil.getEntityManager();
		Query q = em
				.createQuery("select u from TFilterType u where u.filterType like 'T'");
		List<TFilterType> resultList = q.getResultList();
		return resultList;
	}

	public List<TFilterType> listDateFilterType() {
		EntityManager em = HibernateUtil.getEntityManager();
		Query q = em
				.createQuery("select u from TFilterType u where u.filterType = 'D'");
		List<TFilterType> filterDateElements = q.getResultList();
		return filterDateElements;
	}
}
