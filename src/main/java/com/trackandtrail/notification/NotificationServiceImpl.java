package com.trackandtrail.notification;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sun.mail.smtp.SMTPTransport;
import com.trackandtrail.util.StaticValues;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class NotificationServiceImpl implements NotificationService {

	@Value("${karix.sms.url}")
	private String karixSmsURL;

	@Value("${karix.sms.version}")
	private String karixSmsVersion;

	@Value("${karix.sms.key}")
	private String karixSmskey;

	@Value("${karix.sms.send}")
	private String karixSmsSend;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private OtpService otpService;

	@Override
	public Object notification(NotificationDTO notificationDTO) throws Exception {

		if (notificationDTO.getServiceName().equalsIgnoreCase(StaticValues.SENDSMS.toString()))
			return sendSMS(notificationDTO);
		else if (notificationDTO.getServiceName().equalsIgnoreCase(StaticValues.SENDEMAIL.toString()))
			return sendSimpleEmail(notificationDTO);
		else if (notificationDTO.getServiceName().equalsIgnoreCase(StaticValues.SENDOTP.toString()))
			return sendOTP(notificationDTO);
		else if (notificationDTO.getServiceName().equalsIgnoreCase(StaticValues.SENDHTML.toString()))
			return sendHtmlTemplateEmail(notificationDTO);
		return false;
	}

//	private boolean sendSMS(NotificationDTO notificationDTO) throws Exception {
//
//		Map<String, String> uriVar = new HashMap<String, String>();
//		uriVar.put("version", karixSmsVersion);
//		uriVar.put("key", karixSmskey);
//		uriVar.put("send", karixSmsSend);
//		uriVar.put("mobilenumber", notificationDTO.getMobileNumber().toString());
//		uriVar.put("smstext", notificationDTO.getSmsContent());
//
//		String smsResponse = restTemplate.getForObject(karixSmsURL, String.class, uriVar);
//
//		if (smsResponse.contains("SUCCESS"))
//			return true;
//		else {
//			log.info("<---- NotificationServiceImpl.sendSMS() Message not sent----> {}", smsResponse);
//			return false;
//		}
//
//	}
	
	@Override
	public String sendSMS(NotificationDTO notificationDTO) throws Exception {

		Map<String, String> uriVar = new HashMap<String, String>();
		uriVar.put("version", karixSmsVersion);
		uriVar.put("key", karixSmskey);
		uriVar.put("send", karixSmsSend);
		uriVar.put("mobilenumber", notificationDTO.getMobileNumber().toString());
		uriVar.put("smstext", notificationDTO.getSmsContent());

		return  restTemplate.getForObject(karixSmsURL, String.class, uriVar);

//		if (smsResponse.contains("SUCCESS"))
//			return true;
//		else {
//			log.info("<---- NotificationServiceImpl.sendSMS() Message not sent----> {}", smsResponse);
//			return false;
//		}
	}



	private boolean sendSimpleEmail(NotificationDTO notificationDTO) throws Exception {

		try {
			Properties props = System.getProperties();
			props.put("mail.smtps.host", "smtp.gmail.com");
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory
			Session session = Session.getInstance(props, null);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("tgtest2019@gmail.com"));
			;
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(notificationDTO.getTo(), false));
			msg.setSubject(notificationDTO.getSub());
			msg.setText(notificationDTO.getText());
			msg.setSentDate(new Date());
			SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
			t.connect("smtp.gmail.com", "tgtest2019@gmail.com", "TGDev2020");
			t.sendMessage(msg, msg.getAllRecipients());
			System.out.println("Response: " + t.getLastServerResponse() + t.getLastReturnCode());
			if (t.getLastReturnCode() == 250) {
				return true;
			}
			t.close();
		} catch (Exception e) {
			log.info("<---- NotificationServiceImpl.sendSimpleEmail() Email not sent----> {}", e);
			return true;
		}
		return false;
	}

	private Integer sendOTP(NotificationDTO notificationDTO) throws Exception {
		Integer otp = otpService.generateOtp(notificationDTO.getMobileNumber().toString());
		String smsContent = "Greetings From Track&Trail! Your OTP :" + otp;
		notificationDTO.setSmsContent(smsContent);
		// if(sendSMS(notificationDTO))
		return otp;
		// return 0;
	}

	public Integer sendHtmlTemplateEmail(NotificationDTO notificationDTO) throws IOException {
		try {

			final String fromEmail = "";
			final String password = "";

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Authenticator auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, password);
				}
			};

			Session session = Session.getDefaultInstance(props, auth);
			MimeMessage msg = new MimeMessage(session);
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress("email id"));// email id here

			msg.setReplyTo(InternetAddress.parse("email id", false));// email id

			msg.setSubject(notificationDTO.getSub(), "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(notificationDTO.getTo(), false));

			msg.setContent(notificationDTO.getText(), "text/html");

			// Send message
			Transport.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return 1;
	}

	

}
