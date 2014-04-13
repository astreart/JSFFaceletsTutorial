package bg.fmi.master.thesis.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RateEvent;

import bg.fmi.master.thesis.model.TRequest;

@ManagedBean(name = "requestBean")
@SessionScoped
public class RequestBean implements Serializable {

	private TRequest tRequest;

	public TRequest gettRequest() {
		return tRequest;
	}

	public void settRequest(TRequest tRequest) {
		this.tRequest = tRequest;
	}

}