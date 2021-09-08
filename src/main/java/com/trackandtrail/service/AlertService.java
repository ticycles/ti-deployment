package com.trackandtrail.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.dto.BikeRentalBookingDto;
import com.trackandtrail.dto.notification.PushNotificationRequest;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalBooking;
import com.trackandtrail.model.bikerentalmanagement.BikeRentalProduct;
import com.trackandtrail.model.bikerentalmanagement.StoreDetail;
import com.trackandtrail.model.bikeservicepackage.BikeService;
import com.trackandtrail.model.bikeservicepackage.BikeServicePackage;
import com.trackandtrail.model.challenge.Challenge;
import com.trackandtrail.model.content.Content;
import com.trackandtrail.model.event.Event;
import com.trackandtrail.model.event.EventType;
import com.trackandtrail.model.mail.Mail;
import com.trackandtrail.model.managedealer.ManageDealer;
import com.trackandtrail.model.notificationAlert.NotificationAlert;
import com.trackandtrail.model.registerbike.BikeBrand;
import com.trackandtrail.model.rideActivityComment.RideActivityComment;
import com.trackandtrail.model.rideactivity.RideActivity;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.notification.NotificationDTO;
import com.trackandtrail.notification.NotificationService;
import com.trackandtrail.notification.firebase.FCMService;
import com.trackandtrail.repository.notificationAlert.NotificationAlertRepository;
import com.trackandtrail.util.Constants;
import com.trackandtrail.util.StaticValues;
import com.trackandtrail.util.TemplateHelper;

@Service
public class AlertService {

	@Autowired
	NotificationService notificationService;
	

	@Autowired
	private EmailSenderService emailsender;
	
	@Autowired
    private FCMService fcmService;
    
    
    @Autowired
    private NotificationAlertRepository notificationAlertRepository;
	
	
	public void sendFollowAlert(UserModel follower, UserModel following) throws Exception {
		
		
		// username - fname, lasname
		
		// make template ready
		
		 // Prepare value map of variables to replace to template String
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("user_name", follower.getFirstName()+" " + follower.getLastName());
		String smsContent = TemplateHelper.mergeTemplateWithContent(Constants.USER_FOLLOW, valuesMap);
		
		// make message content for every alert
		
//		SMS
		NotificationDTO ndto = new NotificationDTO();
		ndto.setServiceName(StaticValues.SENDSMS.toString());
		ndto.setMobileNumber(Long.valueOf(following.getMobile()));
		ndto.setSmsContent(smsContent);	
		
		//sendsms
		notificationService.notification(ndto);
		System.out.println("------------send user follow sms here--------------------");

		
		

		

		
		//sendemail
//		EMAIL
		if(following.getEmail()!=null) {
			
			
			 Map<String, Object> model = new HashMap<String, Object>();
		        model.put("user_name", follower.getFirstName()+" " + follower.getLastName());
					 
			
	    Mail mail = new Mail();	
	    mail.setMailTo(following.getEmail());
	    mail.setTemplateName("user-follow-template");
	    mail.setSubject("Follow Email");
	    mail.setProps(model);
	    emailsender.sendEmail(mail);
		
		}
		

		// sendpush
		if(following.getFCMToken()!=null) {
		PushNotificationRequest request = new PushNotificationRequest();	
		 Map<String, String> data = new HashMap<String, String>();
		 data.put("user_id", following.getId().toString());
		 data.put("page", "profile_view");
		 data.put("module", "User");
		request.setData(data);
		request.setTitle("Follows");
		request.setToken(following.getFCMToken());
		request.setMessage(smsContent);		
		fcmService.sendMessage(request);
		System.out.println(following.getId() + "----->" + following.getFCMToken());
		
		}
		
		//databse ntofication write
		
		
		 
		 String jsonStrData = "";
		  // Creating Object of ObjectMapper define in Jackson API  
         ObjectMapper Obj = new ObjectMapper();  
         try {  
             // Converting the Java object into a JSON string  
              jsonStrData = Obj.writeValueAsString(following);  
             // Displaying Java object into a JSON string  
//             System.out.println(jsonStrData);  
         }  
         catch (IOException e) {  
             e.printStackTrace();  
         }  
		 
		
		NotificationAlert notifi=new NotificationAlert();
		notifi.setTitle("User Follow");
		notifi.setBody(smsContent);
		notifi.setUserType(StaticValues.ALERT_MOBILE.toString());
		notifi.setSender(follower.getId());
		notifi.setReceiver(following.getId());
		notifi.setModuleName("User");
		notifi.setPage("profile_view");
		notifi.setUser(follower);
		notifi.setData(jsonStrData);
		notificationAlertRepository.save(notifi);

	}
	
	
	
	public void sendBikeRentalBookingAlert(UserModel userModel,BikeRentalProduct bikeRentalProduct,StoreDetail storeDetail,String uuid) throws Exception
	{
		 Map<String, String> valuesMap = new HashMap<>();
	        valuesMap.put("dealer_name", storeDetail.getName());
			String smsContent = TemplateHelper.mergeTemplateWithContent(Constants.BIKE_RENTAL_ORDER_PLACED_DEALER, valuesMap);
			
			// make message content for every alert
			
//			SMS
			NotificationDTO ndto = new NotificationDTO();
			ndto.setServiceName(StaticValues.SENDSMS.toString());
			ndto.setMobileNumber(Long.valueOf(storeDetail.getContact()));
			ndto.setSmsContent(smsContent);	
		
			
			//sendsms
			notificationService.notification(ndto);
			System.out.println("------------send dealer sms here--------------------");
			
			 Map<String, String> valuesMapCustomer = new HashMap<>();
		        valuesMap.put("customer_name", userModel.getFirstName()+" " + userModel.getLastName());
				String smsContentCustomer = TemplateHelper.mergeTemplateWithContent(Constants.BIKE_RENTAL_ORDER_PLACED_CUSTOMER, valuesMapCustomer);
				
				// make message content for every alert
				
//				SMS
				NotificationDTO notifiCustomer = new NotificationDTO();
				notifiCustomer.setServiceName(StaticValues.SENDSMS.toString());
				notifiCustomer.setMobileNumber(Long.valueOf(userModel.getMobile()));
				notifiCustomer.setSmsContent(smsContentCustomer);	
			
				
				//sendsms
				notificationService.notification(notifiCustomer);
				System.out.println("------------send customer sms here--------------------");
				
			
			

			
			//sendemail
//			EMAIL
			if(userModel.getEmail()!=null) {
				
				
				 Map<String, Object> model = new HashMap<String, Object>();
			        model.put("dealer_name", userModel.getFirstName()+" " +userModel.getLastName());
						 
				
		    Mail mail = new Mail();	
		    mail.setMailTo(userModel.getEmail());  
		    mail.setTemplateName("bike-rental-product-template");
		    mail.setSubject("Bike Rental Product Email");
		    mail.setProps(model);
		    emailsender.sendEmail(mail);
			
			}
			
			// sendpush
			if(userModel.getFCMToken()!=null) {
			PushNotificationRequest request = new PushNotificationRequest();	
			 Map<String, String> data = new HashMap<String, String>();
			 data.put("bike_rental_product_id", bikeRentalProduct.getId().toString());
			 data.put("page", "Bike_Rental_View");
			 data.put("module", "User");
			 request.setData(data);
			request.setTitle("Bike Rental Service");
			request.setToken(userModel.getFCMToken());
			request.setMessage(smsContent);	
			fcmService.sendMessage(request);
			System.out.println(userModel.getId() + "----->" + userModel.getFCMToken());
			
			
			}
			
			//databse ntofication write
			
			
			 
			 String jsonStrData = "";
			  // Creating Object of ObjectMapper define in Jackson API  
	         ObjectMapper Obj = new ObjectMapper();  
	         try {  
	             // Converting the Java object into a JSON string  
	              jsonStrData = Obj.writeValueAsString(storeDetail);  
	             // Displaying Java object into a JSON string  
//	             System.out.println(jsonStrData);  
	         }  
	         catch (IOException e) {  
	             e.printStackTrace();  
	         }  
			 
			
			NotificationAlert notifi=new NotificationAlert();
			notifi.setTitle("Bike Rental Product");
			notifi.setBody(smsContent);
			notifi.setUserType(StaticValues.ALERT_DEALER.toString());
			notifi.setSender(userModel.getId());
			notifi.setReceiver(storeDetail.getId());//store id
			notifi.setModuleName("User");
			notifi.setPage("Bike_Rental_View");
			notifi.setUser(userModel);
			notifi.setData(jsonStrData);	;
			notificationAlertRepository.save(notifi);
			
			
	}
	
	public void sendBikeServiceAlert(UserModel userModel,BikeServicePackage bikeServicePackage,BikeBrand bikeBrand,StoreDetail storeDetails, String uuid)throws Exception
	{
		 Map<String, String> valuesMap = new HashMap<>();
//	        valuesMap.put("dealer_name", userModel.getFirstName()+" " + userModel.getLastName());
	        valuesMap.put("dealer_name",storeDetails.getName());
			String smsContent = TemplateHelper.mergeTemplateWithContent(Constants.BIKE_SERVICE_ORDER_PLACED_DEALER, valuesMap);
			
			// make message content for every alert
			
//			SMS
			NotificationDTO ndto = new NotificationDTO();
			ndto.setServiceName(StaticValues.SENDSMS.toString());
			ndto.setMobileNumber(Long.valueOf(storeDetails.getContact()));
			ndto.setSmsContent(smsContent);	
		
			
			//sendsms
			notificationService.notification(ndto);
			System.out.println("------------send dealer sms here--------------------");
			
			
			 Map<String, String> valuesMapCustomer = new HashMap<>();
		        valuesMap.put("customer_name",userModel.getFirstName()+" " + userModel.getLastName());
				String smsContentCustomer = TemplateHelper.mergeTemplateWithContent(Constants.BIKE_SERVICE_ORDER_PLACED_CUSTOMER, valuesMapCustomer);
				
				// make message content for every alert
				
//				SMS
				NotificationDTO notifiCustomer = new NotificationDTO();
				notifiCustomer.setServiceName(StaticValues.SENDSMS.toString());
				notifiCustomer.setMobileNumber(Long.valueOf(userModel.getMobile()));
				notifiCustomer.setSmsContent(smsContentCustomer);	
			
				
				//sendsms
				notificationService.notification(notifiCustomer);
				System.out.println("------------send customer sms here--------------------");
			
			
			
		
			//sendemail
//			EMAIL
			if(userModel.getEmail()!=null) {
				
				
				 Map<String, Object> model = new HashMap<String, Object>();
			        model.put("dealer_name", userModel.getFirstName()+" " +userModel.getLastName());
						 
				
		    Mail mail = new Mail();	
		    mail.setMailTo(userModel.getEmail());   
		    mail.setTemplateName("bike-service-template");
		    mail.setSubject("Bike Service Email");
		    mail.setProps(model);
		    emailsender.sendEmail(mail);
			
			}
//			
			// sendpush
			if(userModel.getFCMToken()!=null) {
			PushNotificationRequest request = new PushNotificationRequest();	
			 Map<String, String> data = new HashMap<String, String>();
			 data.put("store_id", storeDetails.getId().toString());
			 data.put("page", "Bike_Service_View");
			 data.put("module", "User");
			 request.setData(data);
			request.setData(valuesMap);
			request.setTitle("Bike Service");
			request.setToken(userModel.getFCMToken());
			request.setMessage(smsContent);	
			fcmService.sendMessage(request);
			System.out.println(userModel.getId() + "----->" + userModel.getFCMToken());
			
			
			}
			
			//databse ntofication write
			
			
			 
			 String jsonStrData = "";
			  // Creating Object of ObjectMapper define in Jackson API  
	         ObjectMapper Obj = new ObjectMapper();  
	         try {  
	             // Converting the Java object into a JSON string  
	              jsonStrData = Obj.writeValueAsString(storeDetails);  
	             // Displaying Java object into a JSON string  
//	             System.out.println(jsonStrData);  
	         }  
	         catch (IOException e) {  
	             e.printStackTrace();  
	         }  
			 
			
			NotificationAlert notifi=new NotificationAlert();
			notifi.setTitle("Bike Service");
			notifi.setBody(smsContent);
			notifi.setUserType(StaticValues.ALERT_DEALER.toString());
			notifi.setSender(userModel.getId());
			notifi.setReceiver(storeDetails.getId());//store id
			notifi.setModuleName("User");
			notifi.setPage("Bike_Service_View");
			notifi.setUser(userModel);
			notifi.setData(jsonStrData);		
			notificationAlertRepository.save(notifi);
			
			
	}
	
//	public void sendEventAlert(EventType eventType,Event events,UserModel user)throws Exception
	public void sendEventAlert(EventType eventType,UserModel user)throws Exception

	
	{
		 Map<String, String> valuesMap = new HashMap<>();
//	        valuesMap.put("user_name", user.getFirstName()+" " + user.getLastName()+" " + events.getEventName());
	        valuesMap.put("user_name", user.getFirstName()+" " + user.getLastName());
			String smsContent = TemplateHelper.mergeTemplateWithContent(Constants.JOIN_EVENT, valuesMap);
			
//			SMS
			NotificationDTO ndto = new NotificationDTO();
			ndto.setServiceName(StaticValues.SENDSMS.toString());
			ndto.setMobileNumber(Long.valueOf(user.getMobile()));
			ndto.setSmsContent(smsContent);	
		
			
			//sendsms
			notificationService.notification(ndto);
		
			//sendemail
//			EMAIL
			if(user.getEmail()!=null) {
				
				
				 Map<String, Object> model = new HashMap<String, Object>();
//			        model.put("user_name", user.getFirstName()+" " +user.getLastName()+" " + events.getEventName());
			        model.put("user_name", user.getFirstName()+" " +user.getLastName());

						 
				
		    Mail mail = new Mail();	
		    mail.setMailTo(user.getEmail());
		    mail.setTemplateName("event-template");
		    mail.setSubject("Event Email");
		    mail.setProps(model);
		    emailsender.sendEmail(mail);
}
			
			// sendpush
			if(user.getFCMToken()!=null) {
			PushNotificationRequest request = new PushNotificationRequest();
			Map<String, String> data = new HashMap<String, String>();
//			 data.put("id", events.getId().toString());
			 data.put("page", "Event_view");
			 data.put("module", "Event");
			 request.setData(data);
			request.setData(valuesMap);
			request.setTitle("Event");
			request.setToken(user.getFCMToken());
			request.setMessage(smsContent);		
			fcmService.sendMessage(request);
			System.out.println(user.getId() + "----->" + user.getFCMToken());
			
			
			}
			
			//databse ntofication write
			
			
			 
			 String jsonStrData = "";
			  // Creating Object of ObjectMapper define in Jackson API  
	         ObjectMapper Obj = new ObjectMapper();  
	         try {  
	             // Converting the Java object into a JSON string  
	              jsonStrData = Obj.writeValueAsString(user);  
	             // Displaying Java object into a JSON string  
//	             System.out.println(jsonStrData);  
	         }  
	         catch (IOException e) {  
	             e.printStackTrace();  
	         }  
			 
			
			NotificationAlert notifi=new NotificationAlert();
			notifi.setTitle("Join Event");
			notifi.setBody(smsContent);
			notifi.setUserType(StaticValues.ALERT_MOBILE.toString());
			notifi.setSender(user.getId());
//			notifi.setReceiver(events.getId());
			notifi.setModuleName("Event");
			notifi.setPage("Event_view");
			notifi.setUser(user);
			notifi.setData(jsonStrData);		
			notificationAlertRepository.save(notifi);
			
			
	}	

	
//	public void sendChallengeAlert(Challenge challenge,UserModel user) throws Exception
	public void sendChallengeAlert(UserModel user) throws Exception
	{

		 Map<String, String> valuesMap = new HashMap<>();
//	        valuesMap.put("user_name", user.getFirstName()+" " + user.getLastName()+" " + challenge.getName());
	        valuesMap.put("user_name", user.getFirstName()+" " + user.getLastName());

			String smsContent = TemplateHelper.mergeTemplateWithContent(Constants.JOIN_CHALLENGE, valuesMap);
			
//			SMS
			NotificationDTO ndto = new NotificationDTO();
			ndto.setServiceName(StaticValues.SENDSMS.toString());
			ndto.setMobileNumber(Long.valueOf(user.getMobile()));
			ndto.setSmsContent(smsContent);	
		
			
			//sendsms
			notificationService.notification(ndto);
		
			//sendemail
//			EMAIL
			if(user.getEmail()!=null) {
				
				
				 Map<String, Object> model = new HashMap<String, Object>();
//			        model.put("user_name", user.getFirstName()+" " +user.getLastName()+" " + challenge.getName());
				 model.put("user_name", user.getFirstName()+" " +user.getLastName());
						 
				
		    Mail mail = new Mail();	
		    mail.setMailTo(user.getEmail());
		    mail.setTemplateName("challenge-template");
		    mail.setSubject("Challenges Email");
		    mail.setProps(model);
		    emailsender.sendEmail(mail);
}
			
			// sendpush
			if(user.getFCMToken()!=null) {
			PushNotificationRequest request = new PushNotificationRequest();
			Map<String, String> data = new HashMap<String, String>();
//			 data.put("challenge_id", challenge.getId().toString());
			 data.put("page", "Challenge_view");
			 data.put("module", "Challenge");
			 request.setData(data);
			request.setData(valuesMap);
			request.setTitle("Challenges");
			request.setToken(user.getFCMToken());
			request.setMessage(smsContent);	
			fcmService.sendMessage(request);
			System.out.println(user.getId() + "----->" + user.getFCMToken());
			
			
			}
			
			//databse ntofication write
			
			
			 
			 String jsonStrData = "";
			  // Creating Object of ObjectMapper define in Jackson API  
	         ObjectMapper Obj = new ObjectMapper();  
	         try {  
	             // Converting the Java object into a JSON string  
	              jsonStrData = Obj.writeValueAsString(user);  
	             // Displaying Java object into a JSON string  
//	             System.out.println(jsonStrData);  
	         }  
	         catch (IOException e) {  
	             e.printStackTrace();  
	         }  
			 
			
			NotificationAlert notifi=new NotificationAlert();
			notifi.setTitle("Join Challenge");
			notifi.setBody(smsContent);
			notifi.setUserType(StaticValues.ALERT_MOBILE.toString());
			notifi.setSender(user.getId());
//			notifi.setReceiver(challenge.getId());
			notifi.setModuleName("Challenge");
			notifi.setPage("Challenge_view");
			notifi.setUser(user);
			notifi.setData(jsonStrData);		
			notificationAlertRepository.save(notifi);
			
			
	}
	
	public void sendContentAlert(Content contents,UserModel user) throws Exception
	{

		 Map<String, String> valuesMap = new HashMap<>();
	        valuesMap.put("user_name", user.getFirstName()+" " + user.getLastName());
			String smsContent = TemplateHelper.mergeTemplateWithContent(Constants.PUBLISH_CONTENT, valuesMap);
			
//			SMS
			NotificationDTO ndto = new NotificationDTO();
			ndto.setServiceName(StaticValues.SENDSMS.toString());
			ndto.setMobileNumber(Long.valueOf(user.getMobile()));
			ndto.setSmsContent(smsContent);	
			System.out.println("------------send user sms here--------------------");
			
			
			//sendsms
			notificationService.notification(ndto);
		
			//sendemail
//			EMAIL
			if(user.getEmail()!=null) {
				
				
				 Map<String, Object> model = new HashMap<String, Object>();
			        model.put("user_name", user.getFirstName()+" " +user.getLastName());
						 
				
		    Mail mail = new Mail();	
		    mail.setMailTo(user.getEmail());
		    mail.setTemplateName("content-template");
		    mail.setSubject("Content Email");
		    mail.setProps(model);
		    emailsender.sendEmail(mail);
}
			
			// sendpush
			if(user.getFCMToken()!=null) {
			PushNotificationRequest request = new PushNotificationRequest();	
			Map<String, String> data = new HashMap<String, String>();
			 data.put("content_id", contents.getId().toString());
			 data.put("page", "Content_View");
			 data.put("module", "Content");
			 request.setData(data);
			request.setData(valuesMap);
			request.setTitle("Content");
			request.setToken(user.getFCMToken());
			request.setMessage(smsContent);	
			fcmService.sendMessage(request);
			System.out.println(user.getId() + "----->" + user.getFCMToken());
			
			}
			
			//databse ntofication write
			
			
			 
			 String jsonStrData = "";
			  // Creating Object of ObjectMapper define in Jackson API  
	         ObjectMapper Obj = new ObjectMapper();  
	         try {  
	             // Converting the Java object into a JSON string  
	              jsonStrData = Obj.writeValueAsString(user);  
	             // Displaying Java object into a JSON string  
//	             System.out.println(jsonStrData);  
	         }  
	         catch (IOException e) {  
	             e.printStackTrace();  
	         }  
			 
			
			NotificationAlert notifi=new NotificationAlert();
			notifi.setTitle("Content");
			notifi.setBody(smsContent);
			notifi.setUserType(StaticValues.ALERT_MOBILE.toString());
			notifi.setSender(user.getId());
			notifi.setReceiver(contents.getId());
			notifi.setModuleName("Content");
			notifi.setPage("Content_View");
			notifi.setUser(user);
			notifi.setData(jsonStrData);		
			notificationAlertRepository.save(notifi);
			
			
	}
	
	public void sendRideActivityAlert(RideActivity rideActivity,UserModel user) throws Exception
	{

		 Map<String, String> valuesMap = new HashMap<>();
	        valuesMap.put("user_name", user.getFirstName()+" " + user.getLastName());
			String smsContent = TemplateHelper.mergeTemplateWithContent(Constants.RIDE_ACTIVITY, valuesMap);
			
//			SMS
			NotificationDTO ndto = new NotificationDTO();
			ndto.setServiceName(StaticValues.SENDSMS.toString());
			ndto.setMobileNumber(Long.valueOf(user.getMobile()));
			ndto.setSmsContent(smsContent);	
		
			
			//sendsms
			notificationService.notification(ndto);
		
			//sendemail
//			EMAIL
			if(user.getEmail()!=null) {
				
				
				 Map<String, Object> model = new HashMap<String, Object>();
			        model.put("user_name", user.getFirstName()+" " +user.getLastName());
						 
				
		    Mail mail = new Mail();	
		    mail.setMailTo(user.getEmail());
		    mail.setTemplateName("ride-activity-template");
		    mail.setSubject("RideActivity Email");
		    mail.setProps(model);
		    emailsender.sendEmail(mail);
}
			
			// sendpush
			if(user.getFCMToken()!=null) {
			PushNotificationRequest request = new PushNotificationRequest();	
			Map<String, String> data = new HashMap<String, String>();
			 data.put("ride_id", rideActivity.getRideId().toString());
			 data.put("page", "RideActivity_View");
			 data.put("module", "RideActivity");
			 request.setData(data);
			request.setData(valuesMap);
			request.setTitle("Content");
			request.setToken(user.getFCMToken());
			request.setMessage(smsContent);	
			fcmService.sendMessage(request);
			System.out.println(user.getId() + "----->" + user.getFCMToken());
			
			}
			
			//databse ntofication write
			
			
			 
			 String jsonStrData = "";
			  // Creating Object of ObjectMapper define in Jackson API  
	         ObjectMapper Obj = new ObjectMapper();  
	         try {  
	             // Converting the Java object into a JSON string  
	              jsonStrData = Obj.writeValueAsString(user);  
	             // Displaying Java object into a JSON string  
//	             System.out.println(jsonStrData);  
	         }  
	         catch (IOException e) {  
	             e.printStackTrace();  
	         }  
			 
			
			NotificationAlert notifi=new NotificationAlert();
			notifi.setTitle("RideActivity");
			notifi.setBody(smsContent);
			notifi.setUserType(StaticValues.ALERT_MOBILE.toString());
			notifi.setSender(user.getId());
			notifi.setReceiver(user.getId());
			notifi.setModuleName("RideActivity");
			notifi.setPage("RideActivity_View");
			notifi.setUser(user);
			notifi.setData(jsonStrData);		
			notificationAlertRepository.save(notifi);
			
			
	}
	}
