package bg.fmi.master.thesis.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import bg.fmi.master.thesis.model.TImage;
import bg.fmi.master.thesis.util.HibernateUtil;

@ManagedBean(name = "agencyBean")
@SessionScoped
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

		/*
		 * for (TAgency agency : agencyList) { System.out.println("List new: " +
		 * agency.gettUser().getUsername()); }
		 */
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
			System.out.println("Agency Name photo: " + currentAgency.gettUser().getId());
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
