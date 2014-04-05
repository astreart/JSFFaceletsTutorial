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
	 * �� �� �����������
	 */
	private long id;

	/*
	 * ������������� ���
	 */
	private String username;

	/*
	 * ������
	 */
	private String password;

	/*
	 * ���
	 */
	private String name;

	/*
	 * �������
	 */
	private String phone;

	/*
	 * �����
	 */
	private String email;

	/*
	 * ���� �� ����������� � ���������
	 */
	private TRole userRole;

	/**
	 * ������, ��������� �� ����� ����������
	 */
	private Set<TRequest> userRequests = new HashSet<TRequest>(0);

	/**
	 * ������, ����� �� ������ �������
	 */
	private Set<TRequest> executedRequests = new HashSet<TRequest>(0);

	/**
	 * ������ �������, ����� ���������� ������ �������
	 */
	private Set<TAgencyEventType> tAgencyEventTypes = new HashSet<TAgencyEventType>(
			0);

	/**
	 * �������, �� ����� � ��������� �����������
	 */
	private Set<TAgencyRequest> tAgencyRequests = new HashSet<TAgencyRequest>(0);

	/*
	 * ��������� ���������
	 */
	private Set<TMessage> sentMessages = new HashSet<TMessage>(0);

	/*
	 * �������� ���������
	 */
	private Set<TMessageUser> receivedMessages = new HashSet<TMessageUser>(0);

	/*
	 * ������� �� ������������ �� �������
	 */
	private Set<TAgency> tAgencies = new HashSet<TAgency>(0);

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
			Set<TRequest> executedRequests,
			Set<TAgencyEventType> tAgencyEventTypes,
			Set<TAgencyRequest> tAgencyRequests, Set<TMessage> sentMessages,
			Set<TMessageUser> receivedMessages, Set<TAgency> tAgencies) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.userRole = userRole;
		this.userRequests = userRequests;
		this.executedRequests = executedRequests;
		this.tAgencyEventTypes = tAgencyEventTypes;
		this.tAgencyRequests = tAgencyRequests;
		this.sentMessages = sentMessages;
		this.receivedMessages = receivedMessages;
		this.tAgencies = tAgencies;
	}

	public TUser(TUser tUser) {
		super();
		this.username = tUser.username;
		this.email = tUser.email;
	}

	/*
	 * @SequenceGenerator(name = "generator", sequenceName = "SEQ_T_USER",
	 * allocationSize = 1)
	 * 
	 * @Id
	 * 
	 * @GeneratedValue(strategy = SEQUENCE, generator = "generator")
	 * 
	 * @Column(name = "ID", unique = true, nullable = false, precision = 22,
	 * scale = 0)
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	@Column(name = "PASSWORD", nullable = false, length = 15)
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hiredAgency")
	public Set<TRequest> getExecutedRequests() {
		return executedRequests;
	}

	public void setExecutedRequests(Set<TRequest> executedRequests) {
		this.executedRequests = executedRequests;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "agency")
	public Set<TAgencyEventType> gettAgencyEventTypes() {
		return tAgencyEventTypes;
	}

	public void settAgencyEventTypes(Set<TAgencyEventType> tAgencyEventTypes) {
		this.tAgencyEventTypes = tAgencyEventTypes;
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
	public Set<TAgency> gettAgency() {
		return tAgencies;
	}

	public void settAgency(Set<TAgency> tAgencies) {
		this.tAgencies = tAgencies;
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
	/*
	 * @OneToOne(fetch = FetchType.LAZY, mappedBy = "test", cascade =
	 * CascadeType.ALL)
	 * 
	 * @Column(name = "TEST") public TAgency getTestAgency() { return
	 * testAgency; }
	 * 
	 * public void setTestAgency(TAgency testAgency) { this.testAgency =
	 * testAgency; }
	 * 
	 * private TAgency testAgency;
	 */
}
