package bg.fmi.master.thesis.beans;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import bg.fmi.master.thesis.model.TEventType;
import bg.fmi.master.thesis.util.HibernateUtil;

@ManagedBean(name = "editEventTypeBean")
@ViewScoped
public class EditEventTypeBean {

	private TEventType selectedEventType;
	private EventTypeDataModel eventTypeModel;
	private List<TEventType> eventTypes;
	private List<TEventType> eventTypeLists;

	
	public EditEventTypeBean() {
		eventTypeModel = new EventTypeDataModel(listEventTypes());
	}

	public TEventType getSelectedEventType() {
		return selectedEventType;
	}

	public void setSelectedEventType(TEventType selectedEventType) {
		this.selectedEventType = selectedEventType;
	}

	public EventTypeDataModel getEventTypeModel() {
		return eventTypeModel;
	}

	public List<TEventType> listEventTypes() {
		EntityManager em = HibernateUtil.getEntityManager();

		//ако се нуждаем от нещо специфично за сесията
		//Session session = em.unwrap(Session.class);
		
		// read the existing entries and write to console
		Query q = em.createQuery("select u from TEventType u");
		eventTypeLists = q.getResultList();
		
		if(eventTypeLists.isEmpty()){
			prepareSampleData();
		}
		for (TEventType eventType : eventTypeLists) {
			System.out.println(eventType);
		}
		System.out.println("Size: " + eventTypeLists.size());
		eventTypes = eventTypeLists;
		return eventTypeLists;
	}

	private void prepareSampleData() {
		TEventType tEventType = new TEventType();
		tEventType.setId(Long.valueOf(1));
		tEventType.setEventTypeName("Сватби");
		tEventType.setEventTypeDesc("Годежи, Сватбени тържества");
		
		TEventType tEventType1 = new TEventType();
		tEventType1.setId(Long.valueOf(2));
		tEventType1.setEventTypeName("Рожденни дни");
		tEventType1.setEventTypeDesc("Рожденни, именни дни");
		
		eventTypeLists.add(tEventType);
		eventTypeLists.add(tEventType1);
		
	}

	public void saveChanges() throws IOException {
		EntityManager em = HibernateUtil.getEntityManager();
		
		em.getTransaction().begin();
		em.merge(selectedEventType);
		em.getTransaction().commit();
	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("EventType Selected",
				((TEventType) event.getObject()).getEventTypeName());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("EventType Unselected",
				((TEventType) event.getObject()).getEventTypeName());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	
	public void deleteEventType() {
		EntityManager em = HibernateUtil.getEntityManager();

		em.getTransaction().begin();

	     System.out.println("EventType for deleting: " + selectedEventType);
	     
	    //You need to check if the entity is managed by EntityManager#contains()
	     //and if not, then make it managed it EntityManager#merge().
	     em.remove(em.contains(selectedEventType) ? selectedEventType : em.merge(selectedEventType));
	   
	    Query q = em.createQuery("select u from TEventType u");
		List<TEventType> newEventTypeLists = q.getResultList();
		for (TEventType eventType : newEventTypeLists) {
			System.out.println(eventType);
		}
		System.out.println("Size: " + newEventTypeLists.size());
		
		eventTypeModel.removeEventType(eventTypes, selectedEventType);
	    em.getTransaction().commit();
	  }

}
