package bg.fmi.master.thesis.models;

import java.util.HashSet;
import java.util.Set;

import bg.fmi.master.thesis.models.TUser;
import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.UniqueConstraint;

/*
 * Таблица, пазеща специфична информация за агенция за организиране на събития
 */
@Entity
@Table(name = "T_AGENCY", uniqueConstraints = { @UniqueConstraint(columnNames = "ADDRESS") })
public class TAgency implements java.io.Serializable {

	/**
	 * Първичен ключ
	 */
	private Long tUserId;

	/**
	 * Първичен ключ (връзка с TUser)
	 */
	private TUser tUser;

	/*
	 * Уеб сайт на агенцията
	 */
	private String website;

	/*
	 * Град
	 */
	private String city;

	/*
	 * Адрес
	 */
	private String address;

	/*
	 * Информация
	 */
	private String information;

	/**
	 * Заявки, взети от дадена агенция
	 */
	private Set<TRequest> executedRequests = new HashSet<TRequest>(0);

	/**
	 * Типове събития, които огранизира дадена агенция
	 */
	private Set<TAgencyEventType> tAgencyEventTypes = new HashSet<TAgencyEventType>(
			0);

	public TAgency() {
	}

	public TAgency(TUser tUser, String city, String address, String information) {
		this.tUser = tUser;
		this.city = city;
		this.address = address;
		this.information = information;
	}

	public TAgency(TUser tUser, String website, String city, String address,
			String information, Set<TRequest> executedRequests,
			Set<TAgencyEventType> tAgencyEventTypes) {
		this.tUser = tUser;
		this.website = website;
		this.city = city;
		this.address = address;
		this.information = information;
		this.executedRequests = executedRequests;
		this.tAgencyEventTypes = tAgencyEventTypes;
	}

	@SequenceGenerator(name = "generator", sequenceName = "SEQ_T_AGENCY", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "T_USER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long gettUserId() {
		return tUserId;
	}

	public void settUserId(Long tUserId) {
		this.tUserId = tUserId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "T_USER_ID", unique = true, nullable = false, insertable = false, updatable = false)
	public TUser gettUser() {
		return tUser;
	}

	public void settUser(TUser tUser) {
		this.tUser = tUser;
	}

	@Column(name = "WEBSITE", length = 150)
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Column(name = "CITY", length = 60, nullable = false)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "ADDRESS", length = 400)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "INFORMATION", length = 4000, nullable = false)
	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tUserId == null) ? 0 : tUserId.hashCode());
		result = prime * result + ((tUser == null) ? 0 : tUser.hashCode());
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
		TAgency other = (TAgency) obj;
		if (tUserId == null) {
			if (other.tUserId != null)
				return false;
		} else if (!tUserId.equals(other.tUserId))
			return false;
		if (tUser == null) {
			if (other.tUser != null)
				return false;
		} else if (!tUser.equals(other.tUser))
			return false;
		return true;
	}
}
