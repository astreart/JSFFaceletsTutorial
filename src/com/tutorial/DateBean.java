package com.tutorial;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.User;

@ManagedBean(name="dateBean")
@SessionScoped
public class DateBean {
	
	private Date date;
	private String name = "some_name";
    private static final String PERSISTENCE_UNIT_NAME = "myapp";
	private static EntityManagerFactory factory;
	private User user = new User();
	
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
		    /*Query q = em.createQuery("select u from User u");
		    List<User> todoList = q.getResultList();
		    for (User todo : todoList) {
		      System.out.println(todo);
		    }
		    System.out.println("Size: " + todoList.size());
				*/
		    // create new todo
		    em.getTransaction().begin();
		    //User todo = new User();
		    //todo.setUsername("This is a test");
		    //todo.setEmail("This is a test");
		    User newUser = new User(user);
		    em.persist(newUser);
		    em.getTransaction().commit();

		    em.close();
		  }
	public void listUsers() {
	    factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    EntityManager em = factory.createEntityManager();
	    // read the existing entries and write to console
	   Query q = em.createQuery("select u from User u");
	    List<User> todoList = q.getResultList();
	    for (User todo : todoList) {
	      System.out.println(todo);
	    }
	    System.out.println("Size: " + todoList.size());
		
	    // create new todo
	   

	    em.close();
	  }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
