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
 * Таблица, пазеща информация за видовете филтри, свързани с дадено запитване за организиране на събитие
 */

@Entity
@Table(name = "REQUEST_FILTER")
public class ReqFilter implements java.io.Serializable {

	/**
	 * ИД на филтър
	 */
	private Long id;

}
