package bg.fmi.master.thesis.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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
	private EventTypeBean eventTypeBean = new EventTypeBean();
	private TRequestFilter requestFilter = new TRequestFilter();
	private FilterTypeBean filterTypeBean =  new FilterTypeBean();
	private Map<TFilterType, Object> eventDateValues = new HashMap<TFilterType, Object>();
	private Map<TFilterType, Object> textFilterTypeValues = new HashMap<TFilterType, Object>();

	public TRequest gettRequest() {
		return tRequest;
	}

	public void settRequest(TRequest tRequest) {
		this.tRequest = tRequest;
	}

	public EventTypeBean getEventTypeBean() {
		return eventTypeBean;
	}

	public void setEventTypeBean(EventTypeBean eventTypeBean) {
		this.eventTypeBean = eventTypeBean;
	}
	
	public FilterTypeBean getFilterTypeBean() {
		return filterTypeBean;
	}

	public void setFilterTypeBean(FilterTypeBean filterTypeBean) {
		this.filterTypeBean = filterTypeBean;
	}

	public Map<TFilterType, Object> getEventDateValues() {
		return eventDateValues;
	}

	public void setEventDateValues(Map<TFilterType, Object> eventDateValues) {
		this.eventDateValues = eventDateValues;
	}

	public TRequestFilter getRequestFilter() {
		return requestFilter;
	}

	public void setRequestFilter(TRequestFilter requestFilter) {
		this.requestFilter = requestFilter;
	}

	public Map<TFilterType, Object> getTextFilterTypeValues() {
		return textFilterTypeValues;
	}

	public void setTextFilterTypeValues(
			Map<TFilterType, Object> textFilterTypeValues) {
		this.textFilterTypeValues = textFilterTypeValues;
	}

	public void addRequest() {

		EntityManager em = HibernateUtil.getEntityManager();
		
		em.getTransaction().begin();
		
		TRequest newRequest = new TRequest(tRequest);
		
		newRequest.setRequestDate(new Date());

		Query q = em.createQuery("select u from TUser u where u.id = 2");
		TUser author1 = (TUser) q.getSingleResult();
		newRequest.setAuthor(author1);
		
		if (eventTypeBean.getSelectedEventType() != null) {
			newRequest.settEventType(eventTypeBean.getSelectedEventType());
		}
		
		try {
			em.persist(newRequest);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		
		//Save BooleanFilterTypes
		List<TFilterType> selectedBooleanFilterTypes = filterTypeBean
				.getSelectedBooleanFilterTypes();
		if (selectedBooleanFilterTypes != null) {
			for (TFilterType type : selectedBooleanFilterTypes) {
				TRequestFilter reqFilter = new TRequestFilter(requestFilter);
				reqFilter.settRequest(newRequest);
				reqFilter.settFilterType(type);
				reqFilter.setFilterValue("TRUE");
				
				try {
					em.persist(reqFilter);
				} catch (Exception e) {
					System.out.println("Exception: " + e);
				}
			}
		}

		Iterator textFilterTypeIterator = textFilterTypeValues.entrySet().iterator();
		while (textFilterTypeIterator.hasNext()) {
			Map.Entry pairs = (Map.Entry) textFilterTypeIterator.next();
			if (pairs.getValue() != null
					&& !((String) pairs.getValue()).isEmpty()) {
				TRequestFilter reqFilter = new TRequestFilter(requestFilter);
				reqFilter.settFilterType((TFilterType) pairs.getKey());
				reqFilter.settRequest(newRequest);
				reqFilter.setFilterValue((String) pairs.getValue());
			
				try {
					em.persist(reqFilter);
				} catch (Exception e) {
					System.out.println("Exception: " + e);
				}
			}
			textFilterTypeIterator.remove(); // avoids a ConcurrentModificationException
		}
		

		// set Date
		Iterator dateIterator = eventDateValues.entrySet().iterator();
		while (dateIterator.hasNext()) {
			Map.Entry eventDatePairs = (Map.Entry) dateIterator.next();
			if (eventDatePairs.getValue() != null) {
				TRequestFilter reqFilter = new TRequestFilter(requestFilter);
				reqFilter.settRequest(newRequest);
				reqFilter.settFilterType((TFilterType) eventDatePairs.getKey());
				String convertedDate = new SimpleDateFormat("yyyy-mm-dd")
						.format(eventDatePairs.getValue());
				reqFilter.setFilterValue(convertedDate);
				try {
					em.persist(reqFilter);
				} catch (Exception e) {
					System.out.println("Exception: " + e);
				}
			}
			dateIterator.remove();
		}
		em.getTransaction().commit();
	}
}