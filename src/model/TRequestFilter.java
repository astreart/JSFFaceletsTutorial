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
 * �������, ������ ���������� �� �������� ������, �������� � ������ ��������� �� ������������ �� �������
 */

@Entity
@Table(name = "T_REQUEST_FILTER")
public class TRequestFilter implements java.io.Serializable {

	/**
	 * �� �� ������
	 */
	private Long id;

}
