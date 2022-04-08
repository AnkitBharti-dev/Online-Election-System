package com.OnlineElectionSystem;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	public static void sendMail(String emailTo, String subject, String body) {
	    Properties p = new Properties();
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.port", "465");
		p.put("mail.smtp.ssl.enable", "true");
		p.put("mail.smtp.auth", "true");
		MailAuthenticator m = new MailAuthenticator("enter email id here", "enter your passowrd");
		
		Session session = Session.getInstance(p, m);
		session.setDebug(true);
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom("enter email id here");
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			msg.setSubject(subject);
			msg.setText(body);
			Transport.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	  }
}
