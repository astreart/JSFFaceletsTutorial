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

	private TAgency tAgency = new TAgency();
	private List<TUser> userList;
	private Map<TAgency, String> agencyEventTypes = new HashMap<TAgency, String>();

	public ProfileBean() {
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
		Query q = em.createQuery("select u from TUser u where u.id = 11");
		// q.setParameter("id", Long.valueOf(11));
		userList = q.getResultList();
	}

	public List<TUser> getUserList() {
		return userList;
	}

	public void setUserList(List<TUser> userList) {
		this.userList = userList;
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

	public StreamedContent getImage() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			String userId = context.getExternalContext()
					.getRequestParameterMap().get("userId");
			TUser currentUser = new TUser();
			for (TUser currUser : userList) {
				if (currUser.getId() == Long.valueOf(userId))
					currentUser = currUser;

			}
			byte[] image = (byte[]) currentUser.getPhoto();
			if (image == null) {
				StreamedContent imageData;
				File file = new File(
						"F:\\MasterThesis_Radi\\workspace\\MasterThesis\\WebContent\\resources\\images\\profilePic.png");
				imageData = new DefaultStreamedContent(
						new FileInputStream(file));
				return imageData;
			} else {
				return new DefaultStreamedContent(new ByteArrayInputStream(
						image));
			}
		}
	}
}