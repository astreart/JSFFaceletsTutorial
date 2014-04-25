package bg.fmi.master.thesis.model;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/*
 * Таблица, пазеща информация за типовете събития
 */
@Entity
@Table(name = "T_EVENT_TYPE")
public class TEventType implements java.io.Serializable {

	/*
	 * ИД на тип събитие
	 */
	private Long id;

	/*
	 * Тип събитие
	 */
	private String eventTypeName;

	/*
	 * Описание
	 */
	private String eventTypeDesc;

	/*
	 * Връзка към мапинг таблицата, пазеща информация какви типове събития
	 * организира дадена агенция
	 */
	private Set<TAgencyEventType> tAgencyEventTypes = new HashSet<TAgencyEventType>(
			0);
	
	/**
	 * Запитвания за организиране на събитие от избрания тип на събитие
	 */
	private Set<TRequest> eventTypeRequests = new HashSet<TRequest>(0);

	public TEventType() {
	}

	public TEventType(String name) {
		this.eventTypeName = name;
	}

	public TEventType(TEventType eventType) {
		this.eventTypeName = eventType.eventTypeName;
		this.eventTypeDesc = eventType.eventTypeDesc;
	}

	public TEventType(String name, String description,
			Set<TAgencyEventType> tAgencyEventTypes,  Set<TRequest>  eventTypeRequests) {
		this.eventTypeName = name;
		this.eventTypeDesc = description;
		this.tAgencyEventTypes = tAgencyEventTypes;
		this.eventTypeRequests = eventTypeRequests;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_T_EVENT_TYPE", allocationSize = 1)
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "EVENT_TYPE_NAME", nullable = false, unique=true, length = 64)
	public String getEventTypeName() {
		return eventTypeName;
	}

	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}

	@Column(name = "EVENT_TYPE_DESC", length = 512)
	public String getEventTypeDesc() {
		return eventTypeDesc;
	}

	public void setEventTypeDesc(String eventTypeDesc) {
		this.eventTypeDesc = eventTypeDesc;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tEventType")
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
		TEventType other = (TEventType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TEventType [id=" + id + ", eventTypeName=" + eventTypeName
				+ ", eventTypeDesc=" + eventTypeDesc + "]";
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tEventType")
	public Set<TRequest> getEventTypeRequests() {
		return eventTypeRequests;
	}

	public void setEventTypeRequests(Set<TRequest> eventTypeRequests) {
		this.eventTypeRequests = eventTypeRequests;
	}
}
