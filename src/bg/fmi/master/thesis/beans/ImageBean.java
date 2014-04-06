package bg.fmi.master.thesis.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;

import bg.fmi.master.thesis.model.TEventType;
import bg.fmi.master.thesis.model.TImage;
import bg.fmi.master.thesis.util.HibernateUtil;

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
		// File file = new File("C:\\Users\\Radi\\Desktop\\lekarstvo.jpg");
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

	public void showImage() {
		EntityManager em = HibernateUtil.getEntityManager();
		// read the existing entries and write to console
		Query q = em.createQuery("select u from TImage u where u.id=5");
		TImage image = (TImage) q.getSingleResult();
		System.out.println(image);
		tImage = image;
	}
}