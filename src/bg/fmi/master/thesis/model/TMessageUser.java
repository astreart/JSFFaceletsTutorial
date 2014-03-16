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
 * �������, ������ ���������� �� ���� �� ��������� ���������
 */

@Entity
@Table(name = "T_MESSAGE_USER")
public class TMessageUser {
	
	/**
	 * �� �� ������
	 */
	private Long id;

	/**
	 * ����������, �� ������ � ��������� �����������
	 */
	private TUser tUser;

	/**
	 * �����������, ����� � ���������
	 */
	private TMessage tMessage;
	
	public TMessageUser() {
	}

	public TMessageUser(TUser tUser, TMessage tMessage) {
		this.tUser = tUser;
		this.tMessage = tMessage;
	}

	@SequenceGenerator(name = "generator", sequenceName = "SEQ_T_MESSAGE_USER", allocationSize = 1)
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
	@JoinColumn(name = "T_MESSAGE_ID", nullable = false)
	public TMessage getTMessage() {
		return tMessage;
	}

	public void setTMessage(TMessage tMessage) {
		this.tMessage = tMessage;
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
		TMessageUser other = (TMessageUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}