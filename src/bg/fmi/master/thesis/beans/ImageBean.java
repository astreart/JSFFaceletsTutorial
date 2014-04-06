package bg.fmi.master.thesis.beans;

import java.io.File;
import java.io.FileInputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

import org.primefaces.event.FileUploadEvent;

import bg.fmi.master.thesis.model.TImage;
import bg.fmi.master.thesis.util.HibernateUtil;

@ManagedBean(name = "imageBean")
@SessionScoped
public class ImageBean {

	private TImage tImage = new TImage();

	public void addImage(FileUploadEvent event) {
		 
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();

		File file = new File("C:\\Users\\Radi\\Desktop\\lekarstvo.jpg");
		byte[] imageData = new byte[(int) file.length()];

		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(imageData);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		TImage image = new TImage(tImage);
		image.setImageName("lekarstvo.jpg");
		image.setData(imageData);

		try {
			em.persist(image);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}

		em.getTransaction().commit();
		HibernateUtil.shutdown();
	}
}