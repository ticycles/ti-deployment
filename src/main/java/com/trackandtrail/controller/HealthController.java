package com.trackandtrail.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthController {

	@GetMapping("/")
	public ResponseEntity<?> healthCheck() {
		return new ResponseEntity<String>("{}", HttpStatus.OK);
	}
  
	@GetMapping("/health/")
	public ResponseEntity<?> healthCheck2() {
		return new ResponseEntity<String>("{Health check success}", HttpStatus.OK);
	}

}
