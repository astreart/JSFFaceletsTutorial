/**
 * LoginBean.java
 * 
 */

package bg.fmi.master.thesis.beans;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import bg.fmi.master.thesis.model.TUser;
import bg.fmi.master.thesis.util.HibernateUtil;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {
	private String name;
	private String password;
	private String confirmpassword;
	private TUser tUser = new TUser();
	private String role;
	private List<String> roles = Arrays.asList("Агенция", "Потребител");
	private TUser newUser;

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
	}

	public void login(String username, String password) {
		EntityManager em = HibernateUtil.getEntityManager();

		BigDecimal valid = (BigDecimal) em
				.createNativeQuery(
						"SELECT user_security.valid_user(:username, :password) FROM DUAL")
				.setParameter("username", username)
				.setParameter("password", password).getSingleResult();

		if (valid.intValue() == 0) {
			name = null;
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Въвели сте грешен потребител/ парола", " ");

			FacesContext.getCurrentInstance().addMessage(null, message);

		}
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public TUser gettUser() {
		return tUser;
	}

	public void settUser(TUser tUser) {
		this.tUser = tUser;
	}

	public String addUser() {
		FacesMessage message = null;
		newUser = new TUser(tUser);

		System.out.println("username: " + newUser.getUsername());
		System.out.println("password: " + newUser.getPassword());
		System.out.println("confirmpassword: " + confirmpassword);
		System.out.println("name: " + newUser.getName());
		System.out.println("phone: " + newUser.getPhone());
		System.out.println("email: " + newUser.getEmail());
		System.out.println("role: " + role);

		if (!newUser.getPassword().equals(confirmpassword)) {
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Паролите не съвпадат!", " ");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}

		int role_id;

		if (role == "Агенция") {
			role_id = 1;
		} else {
			role_id = 2;
		}

		System.out.println("role_id: " + role_id);

		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();
		Query query = em
				.createNativeQuery("BEGIN user_security.add_user(p_username => :p_username, "
						+ "p_password => :p_password,"
						+ "p_email => :p_email,"
						+ "p_phone => :p_phone,"
						+ "p_name => :p_name, "
						+ "p_role_id => :p_role_id); END;");

		query.setParameter("p_username", newUser.getUsername());
		query.setParameter("p_password", newUser.getPassword());
		query.setParameter("p_email", newUser.getPhone());
		query.setParameter("p_phone", newUser.getPhone());
		query.setParameter("p_name", newUser.getName());
		query.setParameter("p_role_id", role_id);

		try {
			query.executeUpdate();
		} catch (Exception e) {
			return null;
		}

		em.getTransaction().commit();
		return "addedUser";
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}