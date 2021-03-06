package bg.fmi.master.thesis.beans;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import bg.fmi.master.thesis.models.TAgency;
import bg.fmi.master.thesis.models.TAgencyEventType;
import bg.fmi.master.thesis.models.TComment;
import bg.fmi.master.thesis.models.TEventType;
import bg.fmi.master.thesis.models.TFilterType;
import bg.fmi.master.thesis.models.TImage;
import bg.fmi.master.thesis.models.TRequestFilter;
import bg.fmi.master.thesis.utils.HibernateUtil;

@ManagedBean(name = "commentBean")
@RequestScoped
public class CommentBean implements Serializable {

	private List<TComment> allComments;
	
	public CommentBean() {
	    EntityManager em = HibernateUtil.getEntityManager();
		Query query = em
				.createQuery(" from TComment t");

		setAllComments(query.getResultList());
	}

	public List<TComment> getAllComments() {
		return allComments;
	}

	public void setAllComments(List<TComment> allComments) {
		this.allComments = allComments;
	}
}
