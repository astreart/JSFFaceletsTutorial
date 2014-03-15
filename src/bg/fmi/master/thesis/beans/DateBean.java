package bg.fmi.master.thesis.beans;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import bg.fmi.master.thesis.model.TUser;


@ManagedBean(name="dateBean")
@SessionScoped
public class DateBean {
	
	private Date date;
	private String name = "some_name";
    private static final String PERSISTENCE_UNIT_NAME = "myapp";
	private static EntityManagerFactory factory;
	private TUser tUser = new TUser();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		//test();
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void addUser() {
		    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		    EntityManager em = factory.createEntityManager();
		    // read the existing entries and write to console
		    /*Query q = em.createQuery("select u from TUser u");
		    List<TUser> todoList = q.getResultList();
		    for (TUser todo : todoList) {
		      System.out.println(todo);
		    }
		    System.out.println("Size: " + todoList.size());
				*/
		    // create new todo
		    em.getTransaction().begin();
		    //TUser todo = new TUser();
		    //todo.setUsername("This is a test");
		    //todo.setEmail("This is a test");
		    TUser newUser = new TUser(tUser);
		    em.persist(newUser);
		    em.getTransaction().commit();

		    em.close();
		  }
	public void listUsers() {
	    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    EntityManager em = factory.createEntityManager();
	    // read the existing entries and write to console
	   Query q = em.createQuery("select u from TUser u");
	    List<TUser> todoList = q.getResultList();
	    for (TUser todo : todoList) {
	      System.out.println(todo);
	    }
	    System.out.println("Size: " + todoList.size());
		
	    // create new todo
	   

	    em.close();
	  }

	public TUser getUser() {
		return tUser;
	}

	public void setUser(TUser tUser) {
		this.tUser = tUser;
	}
	
}
