package bg.fmi.master.thesis.model;

import bg.fmi.master.thesis.model.TUser;
import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.UniqueConstraint;

/*
 * Таблица, пазеща специфична информация за агенция за организиране на събития
 */
@Entity
@Table(name = "T_AGENCY", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ADDRESS") })
public class TAgency implements java.io.Serializable {

	/**
	 * Първичен ключ
	 */
	private Long TUserId;

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

	TAgency() {
	}
	
	TAgency(TUser tUser, String city, String address, String information){
		this.tUser = tUser;
		this.city = city;
		this.address = address;
		this.information = information;
	}
	
	TAgency(TUser tUser, String website, String city, String address, String information){
		this.tUser = tUser;
		this.website = website;
		this.city = city;
		this.address = address;
		this.information = information;
	}
	
	
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_T_AGENCY", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "T_USER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getTUserId() {
		return TUserId;
	}

	public void setTUserId(Long tUserId) {
		TUserId = tUserId;
	}

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "T_USER_ID", unique = true, nullable = false, insertable = false, updatable = false)
	*/public TUser getTUser() {
		return tUser;
	}

	public void setTUser(TUser tUser) {
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

	@Column(name = "ADDRESS", length = 150, nullable = false)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "INFORMATION", length = 150, nullable = false)
	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((TUserId == null) ? 0 : TUserId.hashCode());
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
		if (TUserId == null) {
			if (other.TUserId != null)
				return false;
		} else if (!TUserId.equals(other.TUserId))
			return false;
		if (tUser == null) {
			if (other.tUser != null)
				return false;
		} else if (!tUser.equals(other.tUser))
			return false;
		return true;
	}

}
