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

	public List<TAgency> getAgencyList() {
		return agencyList;
	}

	public void setAgencyList(List<TAgency> agencyList) {
		this.agencyList = agencyList;
	}

	@PostConstruct
	public void init() {
		EntityManager em = HibernateUtil.getEntityManager();
		// read the existing entries and write to console
		Query q = em.createQuery("select u from TAgency u");
		agencyList = q.getResultList();

		TAgency agency = agencyList.get(1);
		
		Query query = em
			    .createQuery("select hiredAgency, count(r.assessment), sum(r.assessment), " +
					    		"sum(r.assessment)/(count(r.assessment)) " +
					    		"from TAgency hiredAgency join hiredAgency.executedRequests r " +
					    		"group by hiredAgency.tUserId, hiredAgency.address, hiredAgency.city, hiredAgency.information," +
					    		"hiredAgency.website");
		
		/*query.setParameter("agency", agency);*/
		List<Object> list = query.getResultList();
		System.out.println("Stop");
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

	public Map<TFilterType, Object> getRating() {
		return rating;
	}

	public void setRating(Map<TFilterType, Object> rating) {
		this.rating = rating;
	}

	private Map<TFilterType, Object> rating = new HashMap<TFilterType, Object>(); 
}
