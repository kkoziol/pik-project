package com.project.pik.EbayApi.mail;

import static com.project.pik.EbayApi.consts.ApiConsts.MAIL_PROPERTIES_FILE_NAME;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.project.pik.EbayApi.model.Order;

public class MailSender {
	/** LOGGER */
	private static final Logger logger = Logger.getLogger(MailSender.class);

	private Session session;

	public MailSender() {
		Properties keys = new Properties();
		try {
			InputStream in = MailSender.class.getResourceAsStream(MAIL_PROPERTIES_FILE_NAME);
			keys.load(in);
		} catch (IOException e) {
			logger.error("Could not load email properties file");
			logger.error(e.getMessage());
		}
		session = Session.getDefaultInstance(keys, new javax.mail.Authenticator() {
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication("autoreplyebaysearch@gmail.com", "tajne123");
			}
		});
	}

	public void sendSimpleMail(String recipientToMail, String subject, String content) {
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("autoreplyebaysearch@gmail.com"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientToMail));
			msg.setSubject(subject);

			MimeBodyPart msgBodyPart = new MimeBodyPart();
			msgBodyPart.setContent(content, "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(msgBodyPart);
			msg.setContent(multipart);

			Transport transport = session.getTransport("smtp");
			transport.connect();
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

		} catch (AddressException e) {
			logger.error("Wrong mail address");
			logger.error(e.getMessage());
		} catch (MessagingException e) {
			logger.error("Wrong message");
			logger.error(e.getMessage());
		} 
	}

	public void sendFoundOffer(String email, Order order, List<String> urls) {
		if(email == null) {
			logger.error("EMAIL EMPTY!");
			return;
		}
		sendSimpleMail(email, "Ebay Search Engine - We found something interesting", 
		          String.format("<html><h1>Hello %s</h1><p>Look at this:<br></br>%s</p></html>", 
		              order.getUser().getName(), urls.stream().collect(Collectors.joining("<br></br>")))); 
	}
}
