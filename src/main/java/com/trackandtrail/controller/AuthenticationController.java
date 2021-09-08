package com.trackandtrail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.LoginDto;
import com.trackandtrail.dto.request.LoginResponseDTO;
import com.trackandtrail.mapper.LoginResponseMapper;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.repository.UserRepository;
import com.trackandtrail.service.UserService;
import com.trackandtrail.util.JwtTokenUtil;
import com.trackandtrail.util.RequestStatusUtil;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userDetailsService;

	@Autowired
	private UserRepository userRepository;

	//@Autowired
	//private UserSummaryCountsRespository userSummaryCountsRespository;

	@PostMapping(value = "/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDto authenticationRequest) throws Exception {
		String token = null;
		LoginResponseDTO dto = new LoginResponseDTO();
		if (authenticationRequest.getChannel().equalsIgnoreCase("WEB")) {
			if (!authenticationRequest.getUsername().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"))
				throw new Exception("INVALID_CREDENTIALS");
		} else if (authenticationRequest.getChannel().equalsIgnoreCase("MOBILE")) {
			if (!authenticationRequest.getUsername().matches("^[6-9]{1}[0-9]{9}$"))
				throw new Exception("INVALID_CREDENTIALS");
			if (null == authenticationRequest.getOtp())
				return ResponseEntity
						.ok(userDetailsService.authenticateMobileUser(authenticationRequest.getUsername()));
			else {
				final UserDetails userDetails = userDetailsService.validateOTP(authenticationRequest);
				if (null != userDetails) {
					token = jwtTokenUtil.generateToken(userDetails);
					if (token != null) {
						LoginResponseMapper.toUserModel(userRepository.findByUsername(userDetails.getUsername()), dto);
						dto.setToken(token);
					}
					return ResponseEntity
							.ok(new BaseResponseDTO(RequestStatusUtil.SUCCESS_RESPONSE.getErrorDescription(),
									RequestStatusUtil.SUCCESS_RESPONSE.getErrorCode(), dto));
				} else
					throw new Exception("INVALID_CREDENTIALS");
			}
		}
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		token = jwtTokenUtil.generateToken(userDetails);

		if (token != null) {
			LoginResponseMapper.toUserModel(userRepository.findByUsername(userDetails.getUsername()), dto);
			dto.setToken(token);
		}
		return ResponseEntity.ok(new BaseResponseDTO(RequestStatusUtil.SUCCESS_RESPONSE.getErrorDescription(),
				RequestStatusUtil.SUCCESS_RESPONSE.getErrorCode(), dto));

	}

	@PostMapping(value = "/social/login")
	public ResponseEntity<?> socialLogin(@RequestBody UserModel model) throws Exception {
		String token = null;
		LoginResponseDTO dto = new LoginResponseDTO();

		if (model.getId() != null)
			throw new Exception("user id must be null");

		final UserDetails userDetails = userDetailsService.socialLoginAuntenticate(model);
		if (null != userDetails) {
			token = jwtTokenUtil.generateToken(userDetails);
			if (token != null) {
				LoginResponseMapper.toUserModel(userRepository.findByUsername(userDetails.getUsername()), dto);
				dto.setToken(token);
			}

				
		}
//		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new BaseResponseDTO(RequestStatusUtil.SUCCESS_RESPONSE.getErrorDescription(),
				RequestStatusUtil.SUCCESS_RESPONSE.getErrorCode(), dto));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}