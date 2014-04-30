package bg.fmi.master.thesis.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import bg.fmi.master.thesis.model.TEventType;
import bg.fmi.master.thesis.model.TFilterType;
import bg.fmi.master.thesis.model.TRequest;
import bg.fmi.master.thesis.model.TRequestFilter;
import bg.fmi.master.thesis.model.TUser;
import bg.fmi.master.thesis.util.HibernateUtil;

@ManagedBean(name = "requestBean")
@RequestScoped
public class RequestBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private TRequest tRequest = new TRequest();

	private TEventType selectedEventType;

	private List<TFilterType> selectedBooleanFilterTypes;

	private TRequestFilter requestFilter = new TRequestFilter();

	public TRequest gettRequest() {
		return tRequest;
	}

	public void settRequest(TRequest tRequest) {
		this.tRequest = tRequest;
	}

	public void addRequest() {

		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		TRequest newRequest = new TRequest(tRequest);

		newRequest.setRequestDate(new Date());

		Query q = em.createQuery("select u from TUser u");
		List<TUser> usersList = q.getResultList();

		TUser author1 = new TUser();
		for (TUser tUser : usersList) {
			if (tUser.getId() == (Long.valueOf(2)))
				author1 = tUser;
		}

		System.out.println("Author: " + author1.getName());

		newRequest.setAuthor(author1);
		if (selectedEventType != null) {
			newRequest.settEventType(selectedEventType);
		}

		try {
			em.persist(newRequest);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		em.getTransaction().commit();

		if (selectedBooleanFilterTypes != null) {
			for (TFilterType type : selectedBooleanFilterTypes) {
				TRequestFilter reqFilter = new TRequestFilter(requestFilter);
				reqFilter.settRequest(newRequest);
				reqFilter.settFilterType(type);
				reqFilter.setFilterValue("true");
				// new transaction
				em.getTransaction().begin();

				try {
					em.persist(reqFilter);
				} catch (Exception e) {
					System.out.println("Exception: " + e);
				}
				em.getTransaction().commit();
			}
		}

		Iterator it = values.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			if (pairs.getValue() != null
					&& !((String) pairs.getValue()).isEmpty()) {
				TRequestFilter reqFilter = new TRequestFilter(requestFilter);
				reqFilter.settFilterType((TFilterType) pairs.getKey());
				reqFilter.settRequest(newRequest);
				reqFilter.setFilterValue((String) pairs.getValue());
				em.getTransaction().begin();
				try {
					em.persist(reqFilter);
				} catch (Exception e) {
					System.out.println("Exception: " + e);
				}
				em.getTransaction().commit();
			}
			it.remove(); // avoids a ConcurrentModificationException
		}

		// set Date
		Iterator iter = eventDateValue.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry eventDatePairs = (Map.Entry) iter.next();
			if (eventDatePairs.getValue() != null) {
				TRequestFilter reqFilter = new TRequestFilter(requestFilter);
				reqFilter.settRequest(newRequest);
				reqFilter.settFilterType((TFilterType) eventDatePairs.getKey());
				String convertedDate = new SimpleDateFormat("yyyy-mm-dd")
						.format(eventDatePairs.getValue());
				reqFilter.setFilterValue(convertedDate);
				em.getTransaction().begin();
				try {
					em.persist(reqFilter);
				} catch (Exception e) {
					System.out.println("Exception: " + e);
				}
				em.getTransaction().commit();
			}
			iter.remove();
		}
	}

	public TEventType getSelectedEventType() {
		return selectedEventType;
	}

	public void setSelectedEventType(TEventType selectedEventType) {
		this.selectedEventType = selectedEventType;
	}

	public List<TFilterType> getSelectedBooleanFilterTypes() {
		return selectedBooleanFilterTypes;
	}

	public void setSelectedBooleanFilterTypes(
			List<TFilterType> selectedBooleanFilterTypes) {
		for (TFilterType f : selectedBooleanFilterTypes) {
			System.out.println("Set selectedBooleanFilterTypes: "
					+ f.getFilterTypeName());
		}
		this.selectedBooleanFilterTypes = selectedBooleanFilterTypes;
	}

	// /test
	private List<TFilterType> filterTypesList;

	public List<TFilterType> listTextFilterTypes() {

		EntityManager em = HibernateUtil.getEntityManager();
		Query q = em
				.createQuery("select u from TFilterType u where u.filterType like 'T'");
		filterTypesList = q.getResultList();
		return filterTypesList;
	}

	public List<TFilterType> getFilterTypesList() {
		return filterTypesList;
	}

	public Map<TFilterType, Object> getValues() {
		return values;
	}

	/*
	 * public void setValues(Map<TFilterType, Object> values) { this.values =
	 * values; }
	 */

	public Map<TFilterType, Object> getEventDateValue() {
		return eventDateValue;
	}

	private Map<TFilterType, Object> values = new HashMap<TFilterType, Object>();

	private Map<TFilterType, Object> eventDateValue = new HashMap<TFilterType, Object>();

	public void setEventDateValue(Map<TFilterType, Object> eventDateValue) {
		this.eventDateValue = eventDateValue;
	}

	private List<TFilterType> filterDateElement;

	public List<TFilterType> listDateFilterType() {
		EntityManager em = HibernateUtil.getEntityManager();
		Query q = em
				.createQuery("select u from TFilterType u where u.filterType = 'D'");
		filterDateElement = q.getResultList();
		return filterDateElement;
	}

}