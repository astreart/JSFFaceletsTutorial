﻿package bg.fmi.master.thesis.beans;

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
import bg.fmi.master.thesis.models.TUser;
import bg.fmi.master.thesis.utils.HibernateUtil;

@ManagedBean(name = "agencyBean")
@SessionScoped
public class AgencyBean implements Serializable {

	private TAgency tAgency = new TAgency();
	private List<TAgency> agencyList;
	private Map<Long, String> rating = new HashMap<Long, String>();
	private Map<TAgency, String> agencyEventTypes = new HashMap<TAgency, String>();
	private List<TComment> comments;

	public AgencyBean() {
		EntityManager em = HibernateUtil.getEntityManager();
		Query queryEventTypes = em
				.createQuery("select agency, et.eventTypeName "
						+ "from TAgency agency join agency.tAgencyEventTypes eat "
						+ "join eat.tEventType et");
		List<Object[]> list2 = queryEventTypes.getResultList();

		TAgency agencyObj = new TAgency();
		String eventTypeText = "";

		Iterator agencyIterator = list2.iterator();
		while (agencyIterator.hasNext()) {
			Object[] agencyEventTypePairs = (Object[]) agencyIterator.next();
			agencyObj = (TAgency) agencyEventTypePairs[0];
			eventTypeText = (String) agencyEventTypePairs[1];
			if (agencyEventTypes.containsKey(agencyObj)) {
				eventTypeText += ", " + agencyEventTypes.get(agencyObj);
			}

			agencyEventTypes.put(agencyObj, eventTypeText);
			agencyIterator.remove();

		}
	}

	@PostConstruct
	public void init() {
		EntityManager em = HibernateUtil.getEntityManager();
		// read the existing entries and write to console
		Query q = em.createQuery("select u from TAgency u");
		agencyList = q.getResultList();
	}

	public Map<Long, String> getRating() {
		return rating;
	}

	public void setRating(Map<Long, String> rating) {
		this.rating = rating;
	}

	public List<TAgency> getAgencyList() {
		return agencyList;
	}

	public void setAgencyList(List<TAgency> agencyList) {
		this.agencyList = agencyList;
	}

	public TAgency gettAgency() {
		return tAgency;
	}

	public void settAgency(TAgency tAgency) {
		this.tAgency = tAgency;
	}

	public Map<TAgency, String> getAgencyEventTypes() {
		return agencyEventTypes;
	}

	public void setAgencyEventTypes(Map<TAgency, String> agencyEventTypes) {
		this.agencyEventTypes = agencyEventTypes;
	}

	public List<TComment> getComments() {
		return comments;
	}

	public void setComments(List<TComment> comments) {
		this.comments = comments;
	}

	public StreamedContent getImage() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			String agencyId = context.getExternalContext()
					.getRequestParameterMap().get("agencyId");
			TAgency currentAgency = new TAgency();
			for (TAgency currAgency : agencyList) {
				if (currAgency.gettUserId() == Long.valueOf(agencyId))
					currentAgency = currAgency;

			}
			byte[] image = (byte[]) currentAgency.gettUser().getPhoto();
			if (image == null) {
				StreamedContent imageData;
				File file = new File(
						"F:\\MasterThesis_Radi\\workspace\\MasterThesis\\WebContent\\resources\\images\\agency.png");
				imageData = new DefaultStreamedContent(
						new FileInputStream(file));
				return imageData;
			} else {
				return new DefaultStreamedContent(new ByteArrayInputStream(
						image));
			}
		}
	}

	public List<TComment> getCommentByAgency(TAgency agency) {
		EntityManager em = HibernateUtil.getEntityManager();

		Query queryAgencyComments = em
				.createQuery("select u from TComment u join u.tRequest r where r.hiredAgency = :agency order by r.author");
		queryAgencyComments.setParameter("agency", agency);
		comments = queryAgencyComments.getResultList();
		return comments;
	}
}
