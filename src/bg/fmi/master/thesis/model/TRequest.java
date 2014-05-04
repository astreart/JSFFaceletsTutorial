package bg.fmi.master.thesis.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * Заявка за организиране на събитиe
 */
@Entity
@Table(name = "T_REQUEST")
public class TRequest implements java.io.Serializable {
	/**
	 * Първичен ключ на таблицата
	 */
	private Long id;

	/**
	 * Автор на запитването
	 */
	private TUser author;
	
    /**
     * Заглавие на запитването
     */
	private String title;
	
	/**
	 * Описание
	 */
	private String description;

	/**
	 * Дата на запитването
	 */
	private Date requestDate;

	/**
	 * Наета агенция
	 */
	private TAgency hiredAgency;

	/**
	 * Оценка за това как е било организирано събитието от наетата агенция
	 */
	private int assessment;

	/**
	 * Флаг, показващ дали запитването е активно
	 */
	private Boolean isActive;

	/**
	 * Флаг, показващ дали запитването е отменено
	 */
	private Boolean isCancelled;
	

	/**
	 * Тип на събитието, за което е пуснато запитване
	 */
	private TEventType tEventType;

	/**
	 * Избрани филтри
	 */
	private Set<TRequestFilter> tRequestFilters = new HashSet<TRequestFilter>(0);

	/**
	 * Агенции, до които е направено запитването
	 */
	private Set<TAgencyRequest> tAgencyRequests = new HashSet<TAgencyRequest>(0);

	public TRequest() {	
		}

	public TRequest(TUser author, String description, Date requestDate) {
		this.author = author;
		this.description = description;
		this.requestDate = requestDate;
	}	

	public TRequest(TUser author, String title, String description, Date requestDate,
			TAgency hiredAgency, int assessment, Boolean isActive,
			Boolean isCancelled, Set<TRequestFilter> tRequestFilters,
			Set<TAgencyRequest> tAgencyRequests, TEventType tEventType) {
		super();
		this.author = author;
		this.title = title;
		this.description = description;
		this.requestDate = requestDate;
		this.hiredAgency = hiredAgency;
		this.assessment = assessment;
		this.isActive = isActive;
		this.isCancelled = isCancelled;
		this.tRequestFilters = tRequestFilters;
		this.tAgencyRequests = tAgencyRequests;
		this.tEventType = tEventType;
	}

	public TRequest(TRequest tRequest) {
		this.author = tRequest.author;
		this.title = tRequest.title;
		this.description = tRequest.description;
		this.requestDate = tRequest.requestDate;
		this.hiredAgency = tRequest.hiredAgency;
		this.assessment = tRequest.assessment;
		this.isActive = tRequest.isActive;
		this.isCancelled = tRequest.isCancelled;
		this.tRequestFilters = tRequest.tRequestFilters;
		this.tEventType = tRequest.tEventType;
	}

	@SequenceGenerator(name = "generator", sequenceName = "SEQ_T_REQUEST", allocationSize = 1)
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
	@JoinColumn(name = "AUTHOR_ID", nullable = false)
	public TUser getAuthor() {
		return this.author;
	}

	public void setAuthor(TUser author) {
		this.author = author;
	}
	
	@Column(name = "TITLE", nullable = false, length = 128)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}	

	@Column(name = "DESCRIPTION", nullable = false, length = 4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "REQUEST_DATE", nullable = false, length = 7)
	public Date getRequestDate() {
		return this.requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HIRED_AGENCY_ID")
	public TAgency getHiredAgency() {
		return this.hiredAgency;
	}

	public void setHiredAgency(TAgency hiredAgency) {
		this.hiredAgency = hiredAgency;
	}

	@Column(name = "ASSESSMENT")
	public int getAssessment() {
		return this.assessment;
	}

	public void setAssessment(int assessment) {
		this.assessment = assessment;
	}

	@Column(name = "IS_ACTIVE", length = 1)
	@Type(type = "yes_no")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "IS_CANCELLED", length = 1)
	@Type(type = "yes_no")
	public Boolean getIsCancelled() {
		return isCancelled;
	}

	public void setIsCancelled(Boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tRequest")
	public Set<TAgencyRequest> gettAgencyRequests() {
		return tAgencyRequests;
	}

	public void settAgencyRequests(Set<TAgencyRequest> tAgencyRequests) {
		this.tAgencyRequests = tAgencyRequests;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tRequest")
	public Set<TRequestFilter> gettRequestFilters() {
		return tRequestFilters;
	}

	public void settRequestFilters(Set<TRequestFilter> tRequestFilters) {
		this.tRequestFilters = tRequestFilters;
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
		TRequest other = (TRequest) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EVENT_TYPE_ID")
	public TEventType gettEventType() {
		return tEventType;
	}

	public void settEventType(TEventType tEventType) {
		this.tEventType = tEventType;
	}
}
