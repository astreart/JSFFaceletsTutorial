﻿package bg.fmi.master.thesis.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import bg.fmi.master.thesis.models.TAgency;
import bg.fmi.master.thesis.models.TMessage;
import bg.fmi.master.thesis.models.TMessageUser;
import bg.fmi.master.thesis.models.TRequest;
import bg.fmi.master.thesis.models.TUser;
import bg.fmi.master.thesis.utils.HibernateUtil;
import bg.fmi.master.thesis.utils.SendEmail;

@ManagedBean(name = "messageBean")
@RequestScoped
public class MessageBean implements Serializable {

	private TMessage tMessage = new TMessage();
	private TMessageUser tMessageUser = new TMessageUser();
	private List<TAgency> agencies;
	private SendEmail sendEmail = new SendEmail();
	
	public TMessage gettMessage() {
		return tMessage;
	}

	public void settMessage(TMessage tMessage) {
		this.tMessage = tMessage;
	}

	public void createMessageToAll(TRequest tRequest) {
		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();

		// Добавяме си съобщението
		TMessage newMessage = new TMessage(tMessage);
		newMessage.setDateSent(tRequest.getRequestDate());
		newMessage.setMessageBody(tRequest.getDescription());
		newMessage.settUser(tRequest.getAuthor());
		newMessage.settRequest(tRequest);
		newMessage.setTitle(tRequest.getTitle());

		Query q = em.createQuery("select max(m.messageGroup) from TMessage m");
		Long messageGroup = (Long) q.getSingleResult();
		newMessage.setMessageGroup(messageGroup + 1);

		em.persist(newMessage);

		// Намираме всички агенции, които организират събития от типа на
		// събитието в запитването
		Query queryAgencies = em
				.createQuery("select agency "
						+ "from TAgency agency join agency.tAgencyEventTypes et where et.tEventType = :eventType");
		queryAgencies.setParameter("eventType", tRequest.gettEventType());
		agencies = queryAgencies.getResultList();

		TMessageUser newMessageUser = null;
		for (TAgency agency : agencies) {
			//Изпращаме съобщението			
			sendEmail.sendEmailToUser("masterthesisfmi@gmail.com", "masterthesisfmi@gmail.com", tRequest.getTitle(), tRequest.getDescription());
			
			// Записваме на кои агенции е добавено съобщението
			newMessageUser = new TMessageUser(tMessageUser);
			newMessageUser.settUser(agency.gettUser());
			newMessageUser.settMessage(newMessage);
			em.persist(newMessageUser);
		}

		em.getTransaction().commit();
	}

	public void createMessageToAgency(TRequest tRequest, TUser tUserRequestSent) {

		EntityManager em = HibernateUtil.getEntityManager();
		em.getTransaction().begin();

		// Добавяме си съобщението
		TMessage newMessage = new TMessage(tMessage);
		newMessage.setDateSent(tRequest.getRequestDate());
		newMessage.setMessageBody(tRequest.getDescription());
		newMessage.settUser(tRequest.getAuthor());
		newMessage.settRequest(tRequest);
		newMessage.setTitle(tRequest.getTitle());

		Query q = em.createQuery("select max(m.messageGroup) from TMessage m");
		Long messageGroup = (Long) q.getSingleResult();
		newMessage.setMessageGroup(messageGroup + 1);

		em.persist(newMessage);
		
		//Изпращаме съобщението			
		sendEmail.sendEmailToUser("masterthesisfmi@gmail.com", "masterthesisfmi@gmail.com", tRequest.getTitle(), tRequest.getDescription());
		
		// Записваме съобщението при изпратените съобщения до определена компания
		TMessageUser newMessageUser = new TMessageUser(tMessageUser);
		newMessageUser.settUser(tUserRequestSent);
		newMessageUser.settMessage(newMessage);
		em.persist(newMessageUser);

		em.getTransaction().commit();

	}
}
