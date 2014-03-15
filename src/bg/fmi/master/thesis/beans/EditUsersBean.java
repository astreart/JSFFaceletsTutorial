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

import bg.fmi.master.thesis.model.TUser;


@ManagedBean(name = "editUsersBean")
@ViewScoped
public class EditUsersBean {

	private TUser selectedUser;
	private UserDataModel usersModel;
	private static final String PERSISTENCE_UNIT_NAME = "myapp";
	private static EntityManagerFactory factory;

	public TUser getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(TUser selectedUser) {
		this.selectedUser = selectedUser;
	}

	public UserDataModel getUsersModel() {
		return usersModel;
	}

	public List<TUser> listUsers() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		// read the existing entries and write to console
		Query q = em.createQuery("select u from TUser u");
		List<TUser> usersList = q.getResultList();
		for (TUser todo : usersList) {
			System.out.println(todo);
		}
		System.out.println("Size: " + usersList.size());

		// create new todo

		em.close();
		return usersList;
	}

	public void saveChanges() {

		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();
		// read the existing entries and write to console
		/*
		 * Query q = em.createQuery("select u from TUser u"); List<TUser> todoList
		 * = q.getResultList(); for (TUser todo : todoList) {
		 * System.out.println(todo); } System.out.println("Size: " +
		 * todoList.size());
		 */
		// create new todo
		em.getTransaction().begin();
		
		
		em.merge(selectedUser);
		em.getTransaction().commit();

		em.close();

	}

	public EditUsersBean() {

		usersModel = new UserDataModel(listUsers());
	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("TUser Selected",
				((TUser) event.getObject()).getUsername());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("TUser Unselected",
				((TUser) event.getObject()).getUsername());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}
