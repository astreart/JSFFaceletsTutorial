package bg.fmi.master.thesis.beans;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import bg.fmi.master.thesis.models.TEventType;
import bg.fmi.master.thesis.models.TImage;
import bg.fmi.master.thesis.utils.HibernateUtil;

@ManagedBean(name = "imageBean")
@SessionScoped
public class ImageBean {

	private TImage tImage = new TImage();

	public TImage gettImage() {
		return tImage;
	}

	public void settImage(TImage tImage) {
		this.tImage = tImage;
	}

	String fileName;

	public void addImage(FileUploadEvent event) {

		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();

		fileName = event.getFile().getFileName();
		File file = new File("F:\\PICTURE\\peizaji\\", fileName);
		byte[] imageData = new byte[(int) file.length()];

		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(imageData);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		TImage image = new TImage(tImage);
		image.setImageName(fileName);
		image.setData(imageData);

		try {
			em.persist(image);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}

		em.getTransaction().commit();
		HibernateUtil.shutdown();
	}

	public StreamedContent getStreamedImageById() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate rig
		} else {
			EntityManager em = HibernateUtil.getEntityManager();
			// read the existing entries and write to console
			Query q = em.createQuery("select u from TImage u where u.id=5");
			TImage image = (TImage) q.getSingleResult();
			if (image.getData() == null) {
				return new DefaultStreamedContent();
			} else {
				return new DefaultStreamedContent(new ByteArrayInputStream(
						image.getData()));
			}
		}
	}
}