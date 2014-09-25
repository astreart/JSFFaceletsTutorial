package bg.fmi.master.thesis.models;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.ByteArrayInputStream;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.primefaces.model.DefaultStreamedContent;

@Entity
@Table(name = "T_IMAGE")
public class TImage implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String imageName;

	private byte[] data;

	public TImage() {
	}

	public TImage(String imageName, byte[] data) {
		super();
		this.imageName = imageName;
		this.data = data;
	}

	public TImage(TImage tImage) {
		this.imageName = tImage.imageName;
		this.data = tImage.data;
	}

	@SequenceGenerator(name = "generator", sequenceName = "SEQ_T_IMAGE", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "IMAGE_NAME", unique = false, nullable = false, length = 100)
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Lob
	@Column(name = "DATA", unique = false, nullable = false, length = 100000)
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
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
		TImage other = (TImage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}