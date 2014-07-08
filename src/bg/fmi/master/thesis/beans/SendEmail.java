package bg.fmi.master.thesis.beans;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;


public class SendEmail
{
	private Properties props;
	private String msgFrom;
	private String msgTo;
	private String msgSubject;
	private String msgBody;
	
	public String getMsgFrom() {
		return msgFrom;
	}

	public void setMsgFrom(String msgFrom) {
		this.msgFrom = msgFrom;
	}

	public String getMsgTo() {
		return msgTo;
	}

	public void setMsgTo(String msgTo) {
		this.msgTo = msgTo;
	}

	public String getMsgSubject() {
		return msgSubject;
	}

	public void setMsgSubject(String msgSubject) {
		this.msgSubject = msgSubject;
	}
	
	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public SendEmail() {
		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	}
	
	public void sendEmailToUser(String msgFrom, String msgTo, String msgTitle, String msgBody){
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("masterthesisfmi@gmail.com","masterThesis");
				}
			});
	
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(msgTo));//("masterthesisfmi@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(msgTo));//("masterthesisfmi@gmail.com"));
			message.setSubject(msgTitle);
			message.setText("Здравейте," +
					"\n\n Имате ново съобщение от " + msgFrom + 
					"\n Във връзка с " + msgBody +
					"\n\n\n Хубав ден!");
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}