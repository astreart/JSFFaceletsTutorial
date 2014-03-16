package bg.fmi.master.thesis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;

/*
 * �������, ������ ���������� �� �������� �������, ����� ���������� ������ �������
 */

@Entity
@Table(name = "T_AGENCY_EVENT_TYPE")
public class TAgencyEventType {
	
	/**
	 * �� �� ������
	 */
	private Long id;
	
	/*
	 * �������
	 */
	private TUser agency;
	
	/*
	 * ��� �������
	 */
	private TEventType tEventType;
	
	
	TAgencyEventType() {
	}
	
	TAgencyEventType(Long id, TUser agency, TEventType tEventType){
		this.id = id;
		this.agency = agency;
		this.tEventType = tEventType;
	}
	
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_T_AGENCY_EVENT_TYPE", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENCY_ID", nullable = false)
	public TUser getAgency() {
		return agency;
	}

	public void setAgency(TUser agency) {
		this.agency = agency;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "T_EVENT_TYPE_ID")
	public TEventType getTEventType() {
		return tEventType;
	}

	public void setTEventType(TEventType tEventType) {
		this.tEventType = tEventType;
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
		TAgencyEventType other = (TAgencyEventType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}