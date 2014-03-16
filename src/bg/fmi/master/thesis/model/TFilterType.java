package bg.fmi.master.thesis.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * ��� ������ (�������� ���������: ����, ���� ����� � �����)
 */
@Entity
@Table(name = "T_FILTER_TYPE")
public class TFilterType implements java.io.Serializable {

	/**
	 * �� �� ������
	 */
	private Long id;

	/**
	 * ������������ �� ���� ������
	 */
	private String filterTypeName;

	/**
	 * �������� �� ���� ������
	 */
	private String filterTypeDesc;

	private Set<TRequestFilter> tRequestFilters = new HashSet<TRequestFilter>(0);

	public TFilterType() {
	}

	public TFilterType(String filterTypeName, String filterTypeDesc) {
		this.filterTypeName = filterTypeName;
		this.filterTypeDesc = filterTypeDesc;
	}

	public TFilterType(String filterTypeName, String filterTypeDesc,
			Set<TRequestFilter> tRequestFilters) {
		this.filterTypeName = filterTypeName;
		this.filterTypeDesc = filterTypeDesc;
		this.tRequestFilters = tRequestFilters;
	}

	@SequenceGenerator(name = "generator", sequenceName = "SEQ_T_FILTER_TYPE", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "FILTER_TYPE_NAME", nullable = false, length = 50)
	public String getFilterTypeName() {
		return this.filterTypeName;
	}

	public void setFilterTypeName(String filterTypeName) {
		this.filterTypeName = filterTypeName;
	}

	@Column(name = "FILTER_TYPE_DESC", nullable = false, length = 200)
	public String getFilterTypeDesc() {
		return this.filterTypeDesc;
	}

	public void setFilterTypeDesc(String filterTypeDesc) {
		this.filterTypeDesc = filterTypeDesc;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tFilterType")
	public Set<TRequestFilter> getReqFilters() {
		return this.tRequestFilters;
	}

	public void setReqFilters(Set<TRequestFilter> tRequestFilters) {
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
		TFilterType other = (TFilterType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
