/**
 * LoginBean.java
 * 
 */

package bg.fmi.master.thesis.beans;

import java.sql.CallableStatement;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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

	public String logout() {
		this.name = null;
		this.password = null;
		return "logout";
	}

	public String login(String username, String password) {
		EntityManager em = HibernateUtil.getEntityManager();
		
	System.out.println("username: " + username +", password: " + password);

		/*Query validateUser = em
				.createQuery("CALL valid_user1 (:username, :password) ");
		validateUser.setParameter("username", username);
		validateUser.setParameter("password", password);
		
		try {
             Object result = (Object) validateUser.getSingleResult();
     		 this.name = username;
     		System.out.println(result.toString());
        } catch (Exception e) {
        }*/
		
		 
	
		Object valid = (Object)em.createNativeQuery("SELECT user_security.valid_user(:username, :password) FROM DUAL")
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();
		System.out.println("valid: " + valid);

		return "login";
	}
}