package bg.fmi.master.thesis.beans;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import bg.fmi.master.thesis.model.TAgency;
import bg.fmi.master.thesis.model.TUser;
import bg.fmi.master.thesis.util.HibernateUtil;

@ManagedBean(name = "profileBean")
@SessionScoped
public class ProfileBean implements Serializable {

	private List<TUser> userList;
	

	
	
	@PostConstruct
	public void init() {
		EntityManager em = HibernateUtil.getEntityManager();
		Query q = em.createQuery("select u from TUser u where u.id = 11");
		//q.setParameter("id", Long.valueOf(11));
		 userList =   q.getResultList();
	}

	public List<TUser> getUserList() {
		return userList;
	}

	public void setUserList(List<TUser> userList) {
		this.userList = userList;
	}

}