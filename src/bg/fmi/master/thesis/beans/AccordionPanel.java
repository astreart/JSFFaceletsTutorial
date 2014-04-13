package bg.fmi.master.thesis.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class AccordionPanel {
	private String messageOne = "Hello JavaBeat One";
	private String messageTwo = "Hello JavaBeat Two";
	private String messageThree = "Hello JavaBeat Three";

	public String getMessageOne() {
		return messageOne;
	}
	public void setMessageOne(String messageOne) {
		this.messageOne = messageOne;
	}
	public String getMessageTwo() {
		return messageTwo;
	}
	public void setMessageTwo(String messageTwo) {
		this.messageTwo = messageTwo;
	}
	public String getMessageThree() {
		return messageThree;
	}
	public void setMessageThree(String messageThree) {
		this.messageThree = messageThree;
	}
}
