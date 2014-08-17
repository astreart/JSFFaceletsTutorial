/**
 * LoginBean.java
 * 
 */

package bg.fmi.master.thesis.beans;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;

import bg.fmi.master.thesis.model.TAgency;
import bg.fmi.master.thesis.model.TComment;
import bg.fmi.master.thesis.model.TUser;
import bg.fmi.master.thesis.util.HibernateUtil;

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

	public void logout() {
		this.name = null;
		this.password = null;
		// return "logout";
	}

	public void login(String username, String password) {
		EntityManager em = HibernateUtil.getEntityManager();

		BigDecimal valid = (BigDecimal) em
				.createNativeQuery(
						"SELECT user_security.valid_user(:username, :password) FROM DUAL")
				.setParameter("username", username)
				.setParameter("password", password).getSingleResult();

		if (valid.intValue() == 0){
			name = null;
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Въвели сте грешен потребител/ парола", " ");

			FacesContext.getCurrentInstance().addMessage(null, message);

		}
	}
}