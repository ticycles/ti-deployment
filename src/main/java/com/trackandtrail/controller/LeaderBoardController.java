package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.service.LeaderBoardService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/leaderBoard")
public class LeaderBoardController {
	
	@Autowired
	LeaderBoardService leaderBoardService;
	
	
//	@PreAuthorize("hasAnyRole('ROLE_MOBILE','ROLE_ADMIN')")
	@ApiOperation(value = "getAllLeaderBoard", response = BaseResponseDTO.class)
	@GetMapping("/getall")
	public ResponseEntity<BaseResponseDTO> getAll() throws Exception {
		return new ResponseEntity<BaseResponseDTO>(leaderBoardService.getAll(), HttpStatus.OK);
		
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/getByFriends")
	public ResponseEntity<BaseResponseDTO> getByRideActivityUserId(@RequestParam Long followingUserId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(leaderBoardService.getByUserId(followingUserId), HttpStatus.OK);
	}


}
