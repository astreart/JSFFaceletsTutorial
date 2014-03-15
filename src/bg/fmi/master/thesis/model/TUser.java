package bg.fmi.master.thesis.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

@Entity
@Table(name = "user", uniqueConstraints = {
		@UniqueConstraint(columnNames = "USERNAME"),
		@UniqueConstraint(columnNames = "EMAIL"),
		@UniqueConstraint(columnNames = "WEBSITE") })
public class TUser implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;
	@Column(name = "username", unique = true, nullable = false, length = 30)
	private String username;
	@Column(name = "first_name", length = 30)
	private String firstName;
	@Column(name = "last_name", nullable = false, length = 30)
	private String lastName;
	@Column(name = "email", nullable = false, length = 70)
	private String email;
	@Column(name = "address", length = 30)
	private String address;
	// ///////////////////////////////////////////////////////
	@Column(name = "password", nullable = false, length = 15)
	private String password;
	@Column(name = "phone", nullable = false, length = 15)
	private String phone;
	@Column(name = "working_time", length = 150)
	private String workingTime;
	@Column(name = "website", length = 100)
	private String website;
	@Column(name = "city", length = 30)
	private String city;
	@Column(name = "is_agency", length = 1)
	private Boolean isAgency;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "agency_event_type", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "event_type_id", nullable = false, updatable = false) })
	private Set<TEventType> tEventTypes; // = new HashSet<TEventType>(0);

	public TUser() {
		super();
	}

	public TUser(TUser tUser) {
		super();
		this.username = tUser.username;
		this.email = tUser.email;
	}

	public TUser(long id, String address, String city, String email,
			Set<TEventType> tEventTypes, String firstName, Boolean isAgency,
			String lastName, String password, String phone, String username,
			String website, String workingTime) {
		this.id = id;
		this.address = address;
		this.city = city;
		this.email = email;
		this.tEventTypes = tEventTypes;
		this.firstName = firstName;
		this.isAgency = isAgency;
		this.lastName = lastName;
		this.password = password;
		this.phone = phone;
		this.username = username;
		this.website = website;
		this.workingTime = workingTime;

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(String workingTime) {
		this.workingTime = workingTime;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Boolean getIsAgency() {
		return isAgency;
	}

	public void setIsAgency(Boolean isAgency) {
		this.isAgency = isAgency;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Override
	public String toString() {
		return "TUser [id=" + id + ", username=" + username + ", email=" + email
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", address=" + address + "]";
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

	public Set<TEventType> getEventTypes() {
		return tEventTypes;
	}

	public void setEventTypes(Set<TEventType> tEventTypes) {
		this.tEventTypes = tEventTypes;
	}
	
	public void addEventType(TEventType tEventType) {
	    //prevent endless loop
	    if (tEventTypes.contains(tEventType))
	      return ;
	    //add new eventType
	    tEventTypes.add(tEventType);    
	  }

	 
	  public void removeEventType(TEventType tEventType) {
	    //prevent endless loop
	    if (!tEventTypes.contains(tEventTypes))
	      return ;
	    //remove the eventType
	    tEventTypes.remove(tEventTypes);
	    //remove myself from the follower
	  }
}
