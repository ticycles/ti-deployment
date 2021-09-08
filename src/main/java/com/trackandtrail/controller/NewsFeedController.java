package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ChallengeDto;
import com.trackandtrail.dto.ChallengeNewsFeedCmntDto;
import com.trackandtrail.dto.ContentNewsFeedCmntDto;
import com.trackandtrail.exception.BadRequestException;
import com.trackandtrail.exception.CusDataIntegrityViolationException;
import com.trackandtrail.service.NewsFeedService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/newsFeed")
public class NewsFeedController {
	
	
	@Autowired
	NewsFeedService newsFeedService;
	
	
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "challengesByFriends", response = BaseResponseDTO.class)
	@GetMapping(value = "/challengesByFriends", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> challengesByFriends(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(newsFeedService.challengesByFriends(userId), HttpStatus.OK);
	}
	

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "contentByFriends", response = BaseResponseDTO.class)
	@GetMapping(value = "/contentByFriends", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> contentByFriends(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(newsFeedService.contentByFriends(userId), HttpStatus.OK);
	}

	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "suggestedForYou", response = BaseResponseDTO.class)
	@GetMapping(value = "/suggestedForYou", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> getSuggestedForYou(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(newsFeedService.getSuggestedForYou(userId), HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createContentCmnt", response = BaseResponseDTO.class)
	@PostMapping(value = "/createContentCmnt", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody ContentNewsFeedCmntDto cmntDto)
			throws CusDataIntegrityViolationException, Exception {
		if (cmntDto.getId() != null)
			throw new BadRequestException("Id must be null");
		return new ResponseEntity<BaseResponseDTO>(newsFeedService.save(cmntDto), HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@ApiOperation(value = "createChallengeCmnt", response = BaseResponseDTO.class)
	@PostMapping(value = "/createChallengeCmnt", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponseDTO> create(@RequestBody ChallengeNewsFeedCmntDto cmntDto)
			throws CusDataIntegrityViolationException, Exception {
		if (cmntDto.getId() != null)
			throw new BadRequestException("Id must be null");
		return new ResponseEntity<BaseResponseDTO>(newsFeedService.save(cmntDto), HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/challengeLikeComnt")
	public ResponseEntity<BaseResponseDTO> getChallengeLikeComnt(@RequestParam Long id) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(newsFeedService.getChallengeLikeComnt(id), HttpStatus.OK);
	}
	
	
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOBILE')")
	@GetMapping("/contentLikeComnt")
	public ResponseEntity<BaseResponseDTO> getContentLikeComnt(@RequestParam Long userId) throws Exception {
		return new ResponseEntity<BaseResponseDTO>(newsFeedService.getContentLikeComnt(userId), HttpStatus.OK);
	}
}
