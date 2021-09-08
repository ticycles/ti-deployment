package com.trackandtrail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@EntityScan({"com.trackandtrail.model","com.trackandtrail.view"})
@ComponentScan("com.trackandtrail")
@SpringBootApplication
public class TrackAndTrailApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackAndTrailApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(dateFormat);
		return objectMapper;
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	

}
