package bg.fmi.master.thesis.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
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
import bg.fmi.master.thesis.model.TFilterType;
import bg.fmi.master.thesis.model.TImage;
import bg.fmi.master.thesis.util.HibernateUtil;

@ManagedBean(name = "agencyBean")
@RequestScoped
public class AgencyBean implements Serializable {

	private TAgency tAgency = new TAgency();
	private List<TAgency> agencyList;
	private Map<TAgency, String> rating = new HashMap<TAgency, String>();
	
	public AgencyBean(){
		EntityManager em = HibernateUtil.getEntityManager();
		Query query = em
				.createQuery("select hiredAgency, sum(r.assessment)/(count(r.assessment)), "
						+ "count(r.assessment) "
						+ "from TAgency hiredAgency join hiredAgency.executedRequests r "
						+ "group by hiredAgency.tUserId, hiredAgency.address, hiredAgency.city, hiredAgency.information,"
						+ "hiredAgency.website");

		List<Object[]> list = query.getResultList();
		for (Object[] element : list) {
			TAgency ratingForAgency = (TAgency) element[0];
			String ratingText = "Оценка " + element[1] + "/10, гласували: " + element[2];
			
			System.out.println("Агенция:" + ratingForAgency.gettUser().getName());
			System.out.println("Рейтинг:" + ratingText);
			
			rating.put(ratingForAgency, ratingText);
		}
	}

	@PostConstruct
	public void init() {
		EntityManager em = HibernateUtil.getEntityManager();
		// read the existing entries and write to console
		Query q = em.createQuery("select u from TAgency u");
		agencyList = q.getResultList();
	}

	
	public Map<TAgency, String> getRating() {
		return rating;
	}
	
	public void setRating(Map<TAgency, String> rating) {
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
				return new DefaultStreamedContent();
			} else {
				return new DefaultStreamedContent(new ByteArrayInputStream(
						image));
			}
		}
	}
}
