package com.trackandtrail.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.notification.PushNotificationRequest;
import com.trackandtrail.dto.notification.PushNotificationResponse;
import com.trackandtrail.model.mail.Mail;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.notification.NotificationDTO;
import com.trackandtrail.notification.NotificationService;
import com.trackandtrail.repository.UserFollowRepository;
import com.trackandtrail.repository.UserRepository;
import com.trackandtrail.service.AlertService;
import com.trackandtrail.service.EmailSenderService;

import com.trackandtrail.util.StaticValues;

@RestController
public class TestController {

	@Autowired
	private NotificationService notificationService;


	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	UserFollowRepository userFollowRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AlertService alertService;
	

	
	
	
	@GetMapping("/sendSamplePush")
	public ResponseEntity sendSamplePush() {
		Optional<UserModel> following=userRepository.findById(25L);
        Optional<UserModel> follower=userRepository.findById(80L);
        
        if(following.isPresent() && follower.isPresent()) {
	        try {
				alertService.sendFollowAlert(following.get(),follower.get());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }
		return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."),
				HttpStatus.OK);
	}
	
	
	
	
	

	@GetMapping("/sendSms")
	public ResponseEntity sendSms(@RequestParam Long mobile) {
		BaseResponseDTO resp = new BaseResponseDTO();
		NotificationDTO dto = new NotificationDTO(mobile, "", StaticValues.SENDSMS.toString());
		String otp;
		try {
			otp = notificationService.sendSMS(dto);
			resp.setResponseContent(otp);

		} catch (Exception e) {
			resp.setResponseContent(e);

		}

		resp.setMsg("");
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}
	
	
	
	
	@GetMapping("/sendEmail")
	public ResponseEntity sendEmail(@RequestParam String email) {
		BaseResponseDTO resp = new BaseResponseDTO();		
		try {
			
			 Map<String, Object> model = new HashMap<String, Object>();
		        model.put("user_name", "rajapandi");					 			
	    Mail mail = new Mail();	
	    mail.setMailTo(email);
	    mail.setTemplateName("user-follow-template");
	    mail.setSubject("Follow Email");
	    mail.setProps(model);
   
	  emailSenderService.sendEmail(mail);			

			resp.setResponseContent("email sending");

		} catch (Exception e) {
			resp.setResponseContent(e);

		}

		resp.setMsg("");
		return new ResponseEntity<>(resp, HttpStatus.OK);

	}
	
	
	
}