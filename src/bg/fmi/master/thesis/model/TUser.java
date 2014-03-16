package bg.fmi.master.thesis.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	 * Собствено име
	 */
	private String firstName;

	/*
	 * Фамилно име
	 */
	private String lastName;

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
	private TRole tRole;

	/**
	 * Заявки, направени от даден потребител
	 */
	private Set<TRequest> userRequests = new HashSet<TRequest>(0);

	/**
	 * Заявки, взети от дадена агенция
	 */
	private Set<TRequest> executedRequests = new HashSet<TRequest>(0);

	/**
	 * Типове събития, които огранизира дадена агенция
	 */
	private Set<TAgencyEventType> tAgencyEventTypes = new HashSet<TAgencyEventType>(
			0);

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

	public TUser() {
	}

	public TUser(String username, String password, String firstName,
			String lastName, String phone, String email, TRole tRole) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.tRole = tRole;
	}

	public TUser(String username, String password, String firstName,
			String lastName, String phone, String email, TRole tRole,
			Set<TRequest> userRequests, Set<TRequest> executedRequests,
			Set<TAgencyEventType> tAgencyEventTypes,
			Set<TAgencyRequest> tAgencyRequests, Set<TMessage> sentMessages,
			Set<TMessageUser> receivedMessages, Set<TAgency> tAgencies) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.tRole = tRole;
		this.userRequests = userRequests;
		this.executedRequests = executedRequests;
		this.tAgencyEventTypes = tAgencyEventTypes;
		this.tAgencyRequests = tAgencyRequests;
		this.sentMessages = sentMessages;
		this.receivedMessages = receivedMessages;
		this.tAgencies = tAgencies;
	}

	@SequenceGenerator(name = "generator", sequenceName = "SEQ_T_USER", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
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

	@Column(name = "PASSWORD", nullable = false, length = 15)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "FIRSTNAME", nullable = false, length = 15)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LASTNAME", nullable = false, length = 15)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "phone", nullable = false, length = 15)
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
	@JoinColumn(name = "T_ROLE_ID", nullable = false)
	public TRole getTRole() {
		return tRole;
	}

	public void setTRole(TRole tRole) {
		this.tRole = tRole;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
	public Set<TRequest> getUserRequests() {
		return userRequests;
	}

	public void setUserRequests(Set<TRequest> userRequests) {
		this.userRequests = userRequests;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hiredAgency")
	public Set<TRequest> getExecutedRequests() {
		return executedRequests;
	}

	public void setExecutedRequests(Set<TRequest> executedRequests) {
		this.executedRequests = executedRequests;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "agency")
	public Set<TAgencyEventType> getTAgencyEventTypes() {
		return tAgencyEventTypes;
	}

	public void setTAgencyEventTypes(Set<TAgencyEventType> tAgencyEventTypes) {
		this.tAgencyEventTypes = tAgencyEventTypes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tUser")
	public Set<TAgencyRequest> getTAgencyRequests() {
		return tAgencyRequests;
	}

	public void setTAgencyRequests(Set<TAgencyRequest> tAgencyRequests) {
		this.tAgencyRequests = tAgencyRequests;
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
	public Set<TAgency> getТAgencies() {
		return tAgencies;
	}

	public void setТAgencies(Set<TAgency> tAgencies) {
		this.tAgencies = tAgencies;
	}

	@Override
	public String toString() {
		return "TUser [id=" + id + ", username=" + username + ", firstName="
				+ firstName + ", lastName=" + lastName + ", phone=" + phone
				+ ", email=" + email;
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
