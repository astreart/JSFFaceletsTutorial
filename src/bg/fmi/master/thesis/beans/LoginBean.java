/**
 * LoginBean.java
 * 
 */

package bg.fmi.master.thesis.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {
	private String name;
	private String password;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String logout() {
		this.name = null;
		this.password = null;
		return "logout";
	}

	public String login(String name, String password) {
		this.name = name;
		this.password = password;
		return "login";
	}
}