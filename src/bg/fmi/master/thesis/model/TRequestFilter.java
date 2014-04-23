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

/**
 * Таблица, пазеща информация за типовете филтри, свързани с дадена заявка за
 * организиране на събитие
 */

@Entity
@Table(name = "T_REQUEST_FILTER")
public class TRequestFilter implements java.io.Serializable {

	/**
	 * ИД на записа
	 */
	private Long id;

	/**
	 * Филтър
	 */
	private TFilterType tFilterType;

	/**
	 * Заявка за организиране на събитие
	 */
	private TRequest tRequest;

	/**
	 * Стойност на избраният филтър
	 */
	private String filterValue;

	TRequestFilter() {
	}

	TRequestFilter(TFilterType tFilterType, TRequest tRequest,
			String filterValue) {
		this.tFilterType = tFilterType;
		this.tRequest = tRequest;
		this.filterValue = filterValue;
	}

	@SequenceGenerator(name = "generator", sequenceName = "SEQ_T_REQUEST_FILTER", allocationSize = 1)
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
	@JoinColumn(name = "T_REQUEST_ID", nullable = false)
	public TRequest gettRequest() {
		return tRequest;
	}

	public void settRequest(TRequest tRequest) {
		this.tRequest = tRequest;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "T_FILTER_TYPE_ID", nullable = false)
	public TFilterType gettFilterType() {
		return tFilterType;
	}

	public void settFilterType(TFilterType tFilterType) {
		this.tFilterType = tFilterType;
	}

	@Column(name = "FILTER_VALUE", length = 4000)
	public String getFilterValue() {
		return this.filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
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
		TRequestFilter other = (TRequestFilter) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
