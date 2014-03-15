package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Номенклатура на типа филтър (възможни стойности: цена, брой гости и други)
 */
@Entity
@Table(name = "FILTER_TYPE")
public class FilterType implements java.io.Serializable {

	/**
	 * ИД на филтър
	 */
	private Long id;

	/**
	 * Наименование на вида филтър
	 */
	private String filterTypeName;

	/**
	 * Описание на вида филтър
	 */
	private String filterTypeDesc;

	private Set<ReqFilter> reqFilters = new HashSet<ReqFilter>(0);

	public FilterType() {
	}

	public FilterType(Long id, String filterTypeName, String filterTypeDesc) {
		this.id = id;
		this.filterTypeName = filterTypeName;
		this.filterTypeDesc = filterTypeDesc;
	}

	public FilterType(Long id, String filterTypeName, String filterTypeDesc,
			Set<ReqFilter> reqFilters) {
		this.id = id;
		this.filterTypeName = filterTypeName;
		this.filterTypeDesc = filterTypeDesc;
		this.reqFilters = reqFilters;
	}
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "filterType")
	public Set<ReqFilter> getReqFilters() {
		return this.reqFilters;
	}

	public void setReqFilters(Set<ReqFilter> reqFilters) {
		this.reqFilters = reqFilters;
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
		FilterType other = (FilterType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	


}
