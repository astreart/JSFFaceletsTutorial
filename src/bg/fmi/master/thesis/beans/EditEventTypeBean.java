package bg.fmi.master.thesis.beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import bg.fmi.master.thesis.model.TEventType;
import bg.fmi.master.thesis.model.TUser;

@ManagedBean(name = "editEventTypeBean")
@ViewScoped
public class EditEventTypeBean {

	private TEventType selectedEventType;
	private EventTypeDataModel eventTypeModel;
	private static final String PERSISTENCE_UNIT_NAME = "myapp";
	private static EntityManagerFactory factory;

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
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		// read the existing entries and write to console
		Query q = em.createQuery("select u from TEventType u");
		List<TEventType> eventTypeLists = q.getResultList();
		for (TEventType eventType : eventTypeLists) {
			System.out.println(eventType);
		}
		System.out.println("Size: " + eventTypeLists.size());
		em.close();

		return eventTypeLists;
	}

	public void saveChanges() {

		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();

		em.merge(selectedEventType);
		em.getTransaction().commit();

		em.close();

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

}
