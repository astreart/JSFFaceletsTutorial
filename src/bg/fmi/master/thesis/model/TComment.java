package bg.fmi.master.thesis.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * Положителни и отрицателни коментари, свързани с изпълнението на дадена заявка
 */
@Entity
@Table(name = "T_COMMENT")
public class TComment implements java.io.Serializable{

	/**
	 * ИД на коментар
	 */
	private Long id;

	/**
	 * Заявка за организиране на събитие
	 */
	private TRequest tRequest;

	/*
	 * Коментар
	 */
	private String commentBody;

	/*
	 * Дата на коментара
	 */
	private Date commentDate;

	/*
	 * Флаг, показващ дали коментара е положителен или отрицателен
	 */
	private Boolean isNegative;

	TComment() {
	}
	
	public TComment(TRequest tRequest, String commentBody, Date commentDate,
			Boolean isNegative) {
		super();
		this.tRequest = tRequest;
		this.commentBody = commentBody;
		this.commentDate = commentDate;
		this.isNegative = isNegative;
	}
	
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_T_COMMENT", allocationSize = 1)
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
	@JoinColumn(name = "T_REQUEST_ID", nullable = false)
	public TRequest gettRequest() {
		return tRequest;
	}

	public void settRequest(TRequest tRequest) {
		this.tRequest = tRequest;
	}

	@Column(name = "COMMENT_BODY", nullable = false, length = 4000)
	public String getCommentBody() {
		return commentBody;
	}

	public void setCommentBody(String commentBody) {
		this.commentBody = commentBody;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "COMMENT_DATE", nullable = false, length = 7)
	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	@Column(name = "IS_NEGATIVE", length = 1, nullable = false)
	@Type(type = "yes_no")
	public Boolean getIsNegative() {
		return isNegative;
	}

	public void setIsNegative(Boolean isNegative) {
		this.isNegative = isNegative;
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
		TComment other = (TComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
