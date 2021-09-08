	package com.trackandtrail.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.trackandtrail.model.mail.Mail;




@Service
public class EmailSenderService {
	
	@Value("${spring.mail.username}")
private String fromEmailAdress;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;


    public void sendEmail(Mail mail) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

//       mail.getAttachments().forEach(o -> {
//    	   helper.addAttachment( o.getFileName(), new ClassPathResource("javabydeveloper-email.PNG"));   
//       });
//        
           
              
        
        Context context = new Context();
        context.setVariables(mail.getProps());
    
        String html = templateEngine.process(mail.getTemplateName(), context);
//        String html = templateEngine.process("user-follow-template", context);


        String fromMail = mail.getFrom() == null ? fromEmailAdress : mail.getFrom();
        
        helper.setTo(mail.getMailTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(new InternetAddress(fromMail, "Test"));      
        helper.setSentDate(new Date());

        emailSender.send(message);
    }
    
    
    

}

