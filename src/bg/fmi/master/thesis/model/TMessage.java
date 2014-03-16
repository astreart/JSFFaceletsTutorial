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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "T_MESSAGE")
public class TMessage {

	/*
	 * �� �� �����������
	 */
	private long id;
	
	/*
	 * ������� �� �����������
	 */
	private TUser tUser;
	
	/*
	 * �������(�)
	 */
	private Set<TMessageUser> tMessageUsers = new HashSet<TMessageUser>(0);
	
	/*
	 * ������, ��� ����� �� ������ �����������
	 */
	private TRequest tRequest;
	
	/*
	 * ��������
	 */
	private String title;
	
	/*
	 * ����������
	 */
	private String messageBody;
	
	/*
	 * ���� �� ���������
	 */
	private Date dateSent;

	/*
	 * ����, �������� ���� ����������� � ���������
	 */
	private Boolean isRead;
	
	/*
	 * ������ ���� ��� T_MESSAGE. ������� � ��� ��������� � �������� ��������
	 */
	private TMessage tMessage;
	
	public TMessage() {
	}
	
	public TMessage(TUser tUser, Set<TMessageUser> tMessageUsers, TRequest tRequest, String title, String messageBody,
			Date dateSent, Boolean isRead){
		this.tUser = tUser;
		this.tMessageUsers = tMessageUsers;
		this.tRequest = tRequest;
		this.title = title;
		this.messageBody = messageBody;
		this.dateSent = dateSent;
		this.isRead = isRead;
	}
	
	public TMessage(TUser tUser, Set<TMessageUser> tMessageUsers, TRequest tRequest, String title, String messageBody,
			Date dateSent, Boolean isRead, TMessage tMessage){
		this.tUser = tUser;
		this.tMessageUsers = tMessageUsers;
		this.tRequest = tRequest;
		this.title = title;
		this.messageBody = messageBody;
		this.dateSent = dateSent;
		this.isRead = isRead;
		this.tMessage = tMessage;
	}
	
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_T_MESSAGE", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "T_USER_ID")
	public TUser getTUser() {
		return tUser;
	}

	public void setTUser(TUser tUser) {
		this.tUser = tUser;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "receiver")
	public Set<TMessageUser> getTMessageUsers() {
		return tMessageUsers;
	}

	public void setTMessageUsers(Set<TMessageUser> tMessageUsers) {
		this.tMessageUsers = tMessageUsers;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "T_REQUEST_ID")
	public TRequest getTRequest() {
		return tRequest;
	}

	public void setTRequest(TRequest tRequest) {
		this.tRequest = tRequest;
	}

	@Column(name = "TITLE", length = 60, nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "MESSAGE_BODY", length = 4000, nullable = false)
	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_SENT", nullable = false, length = 7)
	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	@Column(name = "IS_READ", length = 1)
	@Type(type = "yes_no")
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	public TMessage getTMessage() {
		return tMessage;
	}

	public void setTMessage(TMessage tMessage) {
		this.tMessage = tMessage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		TMessage other = (TMessage) obj;
		if (id != other.id)
			return false;
		return true;
	}	
}