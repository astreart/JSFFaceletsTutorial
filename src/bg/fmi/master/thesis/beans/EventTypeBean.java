package bg.fmi.master.thesis.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import bg.fmi.master.thesis.models.TEventType;
import bg.fmi.master.thesis.utils.EventTypeDataUtil;
import bg.fmi.master.thesis.utils.HibernateUtil;

@ManagedBean(name = "eventTypeBean")
@ViewScoped
public class EventTypeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private TEventType tEventType = new TEventType();
	private static Map<Long, TEventType> tEventTypeMap;
	private List<SelectItem> selectItems;

	private TEventType selectedEventType;
	private EventTypeDataUtil eventTypeModel;
	private List<TEventType> eventTypesList;

	public EventTypeBean() {
		eventTypeModel = new EventTypeDataUtil(getEventTypesList());

		tEventTypeMap = new LinkedHashMap<Long, TEventType>();
		for (TEventType eventType : eventTypeModel) {
			tEventTypeMap.put(eventType.getId(), eventType);
		}

		selectItems = new ArrayList<SelectItem>();
		for (TEventType tEventType : list()) {
			selectItems.add(new SelectItem(tEventType, tEventType
					.getEventTypeName()));
		}
	}

	public TEventType gettEventType() {
		return tEventType;
	}

	public void settEventType(TEventType tEventType) {
		this.tEventType = tEventType;
	}

	public TEventType find(String key) {
		Long keyToLong = Long.valueOf(key);
		return tEventTypeMap.get(keyToLong);
	}

	public List<TEventType> list() {
		return new ArrayList<TEventType>(tEventTypeMap.values());
	}

	public Map<Long, TEventType> map() {
		return tEventTypeMap;
	}

	public List<SelectItem> getSelectItems() {
		return selectItems;
	}

	public TEventType getSelectedEventType() {
		return selectedEventType;
	}

	public void setSelectedEventType(TEventType selectedEventType) {
		this.selectedEventType = selectedEventType;
	}

	public EventTypeDataUtil getEventTypeModel() {
		return eventTypeModel;
	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("Избран тип събитие",
				((TEventType) event.getObject()).getEventTypeName());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("Отменя на избрания тип събитие",
				((TEventType) event.getObject()).getEventTypeName());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<TEventType> getEventTypesList() {
		EntityManager em = HibernateUtil.getEntityManager();
		Query q = em.createQuery("select u from TEventType u");
		eventTypesList = q.getResultList();
		return eventTypesList;
	}

	// Add EventType
	public void addEventType() {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		TEventType newEventType = new TEventType(tEventType);
		try {
			em.persist(newEventType);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		em.getTransaction().commit();
	}

	// Update EventType
	public void saveChanges() throws IOException {
		EntityManager em = HibernateUtil.getEntityManager();

		em.getTransaction().begin();
		em.merge(selectedEventType);
		em.getTransaction().commit();
	}

	// Delete EventType
	public void deleteEventType() {
		EntityManager em = HibernateUtil.getEntityManager();

		em.getTransaction().begin();

		// You need to check if the entity is managed by
		// EntityManager#contains()
		// and if not, then make it managed it EntityManager#merge().
		try {
			em.remove(em.contains(selectedEventType) ? selectedEventType : em
					.merge(selectedEventType));
			eventTypeModel.removeEventType(eventTypesList, selectedEventType);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}

		em.getTransaction().commit();
	}
}
