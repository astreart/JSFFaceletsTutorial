package bg.fmi.master.thesis.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import bg.fmi.master.thesis.model.TAgency;

@ManagedBean(name = "agencyDetailBean")
@SessionScoped
public class AgencyDetailBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private TAgency agency;

	public void setAgency(TAgency agency) {
		this.agency = agency;
	}

	public TAgency getAgency() {
		System.out.println("Agency: " + agency.gettUser().getName());
		return agency;
	}
}
