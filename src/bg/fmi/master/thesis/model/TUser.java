package bg.fmi.master.thesis.model;

import bg.fmi.master.thesis.model.TAgency;
import static javax.persistence.GenerationType.SEQUENCE;

import java.util.HashSet;
import java.util.Set;
import bg.fmi.master.thesis.model.TRole;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "T_USER", uniqueConstraints = {
		@UniqueConstraint(columnNames = "USERNAME"),
		@UniqueConstraint(columnNames = "PHONE"),
		@UniqueConstraint(columnNames = "EMAIL") })
public class TUser implements java.io.Serializable {

	/*
	 * ИД на потребителя
	 */
	private long id;

	/*
	 * Потребителско име
	 */
	private String username;

	/*
	 * Парола
	 */
	private String password;

	/*
	 * Име
	 */
	private String name;

	/*
	 * Телефон
	 */
	private String phone;

	/*
	 * Имейл
	 */
	private String email;

	/*
	 * Роля на потребителя в системата
	 */
	private TRole userRole;

	/**
	 * Заявки, направени от даден потребител
	 */
	private Set<TRequest> userRequests = new HashSet<TRequest>(0);
	/**
	 * Агенции, до които е направено запитването
	 */
	private Set<TAgencyRequest> tAgencyRequests = new HashSet<TAgencyRequest>(0);

	/*
	 * Изпратени съобщения
	 */
	private Set<TMessage> sentMessages = new HashSet<TMessage>(0);

	/*
	 * Получени съобщения
	 */
	private Set<TMessageUser> receivedMessages = new HashSet<TMessageUser>(0);

	/*
	 * Агенции за организиране на събития
	 */
	private Set<TAgency> tAgencies = new HashSet<TAgency>(0);

	/**
	 * Снимка
	 */
	private byte[] photo;

	public TUser() {
	}

	public TUser(String username, String password, String name, String phone,
			String email, TRole userRole) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.userRole = userRole;
	}

	public TUser(String username, String password, String name, String phone,
			String email, TRole userRole, Set<TRequest> userRequests,
			Set<TAgencyRequest> tAgencyRequests, Set<TMessage> sentMessages,
			Set<TMessageUser> receivedMessages, Set<TAgency> tAgencies,
			byte[] photo) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.userRole = userRole;
		this.userRequests = userRequests;
		this.tAgencyRequests = tAgencyRequests;
		this.sentMessages = sentMessages;
		this.receivedMessages = receivedMessages;
		this.tAgencies = tAgencies;
		this.photo = photo;
	}

	public TUser(TUser tUser) {
		super();
		this.username = tUser.username;
		this.email = tUser.email;
		this.password = tUser.password;
		this.name = tUser.name;
		this.phone = tUser.phone;
		this.userRole = tUser.userRole;
		this.userRequests = tUser.userRequests;
		this.tAgencyRequests = tUser.tAgencyRequests;
		this.sentMessages = tUser.sentMessages;
		this.receivedMessages = tUser.receivedMessages;
		this.tAgencies = tUser.tAgencies;
		this.photo = tUser.photo;
	}

	/*@Id
	@GeneratedValue(strategy = GenerationType.AUTO)*/
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_T_USER", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "USERNAME", nullable = false, length = 30)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD", nullable = false, length = 60)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "NAME", nullable = false, length = 60)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PHONE", nullable = false, length = 15)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "EMAIL", nullable = false, length = 70)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", nullable = false)
	public TRole getUserRole() {
		return userRole;
	}

	public void setUserRole(TRole userRole) {
		this.userRole = userRole;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
	public Set<TRequest> getUserRequests() {
		return userRequests;
	}

	public void setUserRequests(Set<TRequest> userRequests) {
		this.userRequests = userRequests;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tUser")
	public Set<TMessage> getSentMessages() {
		return sentMessages;
	}

	public void setSentMessages(Set<TMessage> sentMessages) {
		this.sentMessages = sentMessages;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tUser")
	public Set<TMessageUser> getReceivedMessages() {
		return receivedMessages;
	}

	public void setReceivedMessages(Set<TMessageUser> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tUser")
	public Set<TAgencyRequest> gettAgencyRequests() {
		return tAgencyRequests;
	}

	public void settAgencyRequests(Set<TAgencyRequest> tAgencyRequests) {
		this.tAgencyRequests = tAgencyRequests;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tUser")
	public Set<TAgency> getTAgency() {
		return tAgencies;
	}

	public void setTAgency(Set<TAgency> tAgencies) {
		this.tAgencies = tAgencies;
	}

	@Lob
	@Column(name = "PHOTO", length = 100000)
	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "TUser [id=" + id + ", username=" + username + ", name=" + name
				+ ", phone=" + phone + ", email=" + email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TUser other = (TUser) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
