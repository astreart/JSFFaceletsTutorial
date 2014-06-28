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
import javax.persistence.OneToOne;
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
public class TComment implements java.io.Serializable {

	/**
	 * ИД на коментар
	 */
	private Long id;

	/**
	 * Заявка за организиране на събитие
	 */
	private TRequest tRequest;

	/*
	 * Позитивен коментар
	 */
	private String positiveComment;

	/*
	 * Негативен коментар
	 */
	private String negativeComment;

	/*
	 * Дата на коментара
	 */
	private Date commentDate;

	/**
	 * Оценка за това как е било организирано събитието от наетата агенция
	 */
	private int assessment;

	public TComment() {
	}

	public TComment(TRequest tRequest, String positiveComment,
			Date commentDate, String negativeComment, int assessment) {
		super();
		this.tRequest = tRequest;
		this.positiveComment = positiveComment;
		this.commentDate = commentDate;
		this.positiveComment = positiveComment;
		this.assessment = assessment;
	}

	public TComment(TComment agencyComment) {
		this.tRequest = agencyComment.tRequest;
		this.positiveComment = agencyComment.positiveComment;
		this.commentDate = agencyComment.commentDate;
		this.positiveComment = agencyComment.positiveComment;
		this.assessment = agencyComment.assessment;
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

	@Column(name = "POSITIVE_COMMENT", length = 4000)
	public String getPositiveComment() {
		return positiveComment;
	}

	public void setPositiveComment(String positiveComment) {
		this.positiveComment = positiveComment;
	}

	@Column(name = "NEGATIVE_COMMENT", length = 4000)
	public String getNegativeComment() {
		return negativeComment;
	}

	public void setNegativeComment(String negativeComment) {
		this.negativeComment = negativeComment;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "COMMENT_DATE", nullable = false, length = 7)
	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	@Column(name = "ASSESSMENT", nullable = false)
	public int getAssessment() {
		return this.assessment;
	}

	public void setAssessment(int assessment) {
		this.assessment = assessment;
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
