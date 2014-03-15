package bg.fmi.master.thesis.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "formBean")
@SessionScoped
public class FormBean implements Serializable {

	private boolean value1;

	public boolean isValue1() {
		return value1;
	}

	public void setValue1(boolean value1) {
		this.value1 = value1;
	}

}
