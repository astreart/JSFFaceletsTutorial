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

import bg.fmi.master.thesis.model.TAgency;
import bg.fmi.master.thesis.model.TAgencyEventType;
import bg.fmi.master.thesis.model.TComment;
import bg.fmi.master.thesis.model.TEventType;
import bg.fmi.master.thesis.model.TFilterType;
import bg.fmi.master.thesis.model.TImage;
import bg.fmi.master.thesis.model.TRequestFilter;
import bg.fmi.master.thesis.model.TUser;
import bg.fmi.master.thesis.util.HibernateUtil;

@ManagedBean(name = "ratingBean")
@RequestScoped
public class RatingBean implements Serializable {

	private Map<Long, String> rating = new HashMap<Long, String>();
	
	public Map<Long, String> getRating() {
		return rating;
	}

	public void setRating(Map<Long, String> rating) {
		this.rating = rating;
	}
	
	public RatingBean() {
		agencyAssessment();	
	}

	public void agencyAssessment() {
		EntityManager em = HibernateUtil.getEntityManager();
		Query query = em.createQuery("select r.hiredAgency.tUserId, "
				+ "sum(u.assessment)/(count(u.assessment)), "
				+ "count(u.assessment) "
				+ "from TRequest r join r.requestComments u "
				+ "group by r.hiredAgency.tUserId");

		List<Object[]> list = query.getResultList();
		for (Object[] element : list) {
			Long ratingForAgency = (Long) element[0];
			String ratingText = "ќценка " + element[1] + "/10, гласували: "
					+ element[2];
			rating.put(ratingForAgency, ratingText);
		}
	}

}
