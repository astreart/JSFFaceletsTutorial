package bg.fmi.master.thesis.model;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Таблица за връзка много-много м/у T_REQUEST и T_USER
 */
@Entity
@Table(name = "T_AGENCY_REQUEST")
public class TAgencyRequest {

	/**
	 * ИД на филтър
	 */
	private Long id;
	
	/**
	 * Запитване, което е изпратено
	 */
	private TRequest tRequest;

	/**
	 * Aгенция, на която е изпратено запитването
	 */
	private TUser tUser;
	
	TAgencyRequest(){
	}
	
	TAgencyRequest(Long id, TRequest tRequest, TUser tUser){
		this.id = id;
		this.tRequest = tRequest;
		this.tUser = tUser;
	}

	@SequenceGenerator(name = "generator", sequenceName = "SEQ_T_AGENCY_REQUEST", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "T_REQUEST_ID", nullable = false)
	public TRequest getTRequest() {
		return tRequest;
	}

	public void setTRequest(TRequest tRequest) {
		this.tRequest = tRequest;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "T_USER_ID", nullable = false)
	public TUser getTUser() {
		return tUser;
	}

	public void setTUser(TUser tUser) {
		this.tUser = tUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		TAgencyRequest other = (TAgencyRequest) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
