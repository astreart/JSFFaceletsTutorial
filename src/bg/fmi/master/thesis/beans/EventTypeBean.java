package bg.fmi.master.thesis.beans;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import bg.fmi.master.thesis.model.TEventType;
import bg.fmi.master.thesis.model.TUser;
import bg.fmi.master.thesis.util.HibernateUtil;

@ManagedBean(name = "eventTypeBean")
@RequestScoped
public class EventTypeBean {

	private TEventType tEventType = new TEventType();

	public void addEventType() {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		TEventType newEventType = new TEventType(tEventType);
		System.out.println("newEventType: " + newEventType);
		try {
			em.persist(newEventType);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		em.getTransaction().commit();
	}

	public void listEventType() {
		EntityManager em = HibernateUtil.getEntityManager();
		// read the existing entries and write to console
		Query q = em.createQuery("select u from TEventType u");
		List<TEventType> eventTypeList = q.getResultList();
		for (TEventType eventType : eventTypeList) {
			System.out.println(eventType);
		}
		System.out.println("Size: " + eventTypeList.size());
	}

	public TEventType gettEventType() {
		return tEventType;
	}

	public void settEventType(TEventType tEventType) {
		this.tEventType = tEventType;
	}

	private List<SelectItem> selectItems;
	private TEventType selectedItem;
	private List<TEventType> listTEventTypes;

	@PostConstruct
	public void init() {
		System.out.println("PostConstructor!!!");
		EntityManager em = HibernateUtil.getEntityManager();
		Query q = em.createQuery("select u from TEventType u");
		listTEventTypes = q.getResultList();

		tEventTypeMap = new LinkedHashMap<Long, TEventType>();
		for (TEventType eventType : listTEventTypes) {
			tEventTypeMap.put(eventType.getId(), eventType);
		}

		selectItems = new ArrayList<SelectItem>();
		for (TEventType tEventType : list()) {
			selectItems.add(new SelectItem(tEventType, tEventType
					.getEventTypeName()));
		}
	}

	// DAO (includes postconstructor too)
	private static Map<Long, TEventType> tEventTypeMap;

	public TEventType find(String key) {
		// print it
		Long keyToLong = Long.valueOf(key);
		System.out.println("It fails here: " + keyToLong);
		System.out.println("It fails here key: " + tEventTypeMap.containsKey(keyToLong));
		System.out.println("It fails here value: " + tEventTypeMap.containsValue(keyToLong));
		System.out.println("It fails here size: " + tEventTypeMap.size());
		System.out.println("It fails here values: " + tEventTypeMap.values());
		System.out.println("It fails here keySet: " + tEventTypeMap.keySet());
		return tEventTypeMap.get(keyToLong);
	}

	public List<TEventType> list() {
		return new ArrayList<TEventType>(tEventTypeMap.values());
	}

	public Map<Long, TEventType> map() {
		return tEventTypeMap;
	}

	public TEventType getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(TEventType selectedItem) {
		this.selectedItem = selectedItem;
	}

	public List<SelectItem> getSelectItems() {
		return selectItems;
	}

	// Actions
	// ------------------------------------------------------------------------------------

	public void action() {
		System.out.println("Selected TEventType item: " + selectedItem);
	}

}
