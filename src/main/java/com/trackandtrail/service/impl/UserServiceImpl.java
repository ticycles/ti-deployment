package com.trackandtrail.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.AuthenticationFailedException;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.LoginDto;
import com.trackandtrail.dto.UserDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.UserModelMapper;
import com.trackandtrail.model.role.Role;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.notification.NotificationDTO;
import com.trackandtrail.notification.NotificationService;
import com.trackandtrail.notification.OtpService;
import com.trackandtrail.repository.RoleRepository;
import com.trackandtrail.repository.UserRepository;
import com.trackandtrail.service.UserService;
import com.trackandtrail.util.GenericSearchandPaginationUtil;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;
import com.trackandtrail.util.StaticValues;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${reset.password.url}")
	private String passwordResetURL;

	@Autowired
	private OtpService otpService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	RoleRepository roleRepository;

//	@Autowired
//	UserMapper userMapper;

	@Override
	public BaseResponseDTO save(UserDto userDTO) throws Exception {
		userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		if (roleRepository.findById(userDTO.getRole().getRoleId()).isPresent()) {
			if (roleRepository.findById(userDTO.getRole().getRoleId()).get().getName().contains("MOBILE"))
				userDTO.setUsername(userDTO.getMobile());
			else
				userDTO.setUsername(userDTO.getEmail());
		}

//		userRepository.save(UserMapper.toUserEntity(userDTO,"C"));
		userRepository.save(objectMapper.convertValue(userDTO, UserModel.class));
		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO update(UserDto userDTO) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<UserModel> user = userRepository.findById(userDTO.getId());
		if (user.isPresent()) {
			UserModelMapper.toUserModel(userDTO, user.get());
//			userDTO.setPassword(user.get().getPassword());
			userRepository.save(user.get());
//			UserModel userM = user.get();
//			UserMapper.toUserEntityUpdate(userDTO, userM);
//			userRepository.save(userM);
			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
					RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {
			throw new ResourceNotFoundException("No user found for given Id.");
		}
		return baseResponseDTO;
	}

	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(userRepository.findAll());
	}

	@Override
	public BaseResponseDTO getById(Long id) throws Exception {
		return GenericUtils.wrapOrNotFound(userRepository.findById(id));
	}

	@Override
	public BaseResponseDTO deleteById(Long id, boolean isSoftDelete) throws Exception {
		Optional<UserModel> user = userRepository.findById(id);
		if (user.isPresent()) {
			if (isSoftDelete) {
				user.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				userRepository.save(user.get());
			} else {
				userRepository.deleteById(id);
			}
		} else {
			throw new ResourceNotFoundException("No user found for given Id.");
		}
		return new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
	}

	@Override
	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception {
		Optional<UserModel> user = userRepository.findById(id);
		BaseResponseDTO responseDTO = null;
		if (user.isPresent()) {
			switch (statusId) {
			case 2:
				user.get().setStatus(RecordStatusUtil.RECORD_DELETED);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.DELETED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.DELETED_RESPONSE.getErrorCode());
				break;
			case 0:
				user.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			case 1:
				user.get().setStatus(RecordStatusUtil.RECORD_ACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.ACTIVE.getErrorDescription(),
						RequestStatusUtil.ACTIVE.getErrorCode());
				break;
			default:
				user.get().setStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			}
			userRepository.save(user.get());
		} else {
			throw new ResourceNotFoundException("No user found for given Id.");
		}
		return responseDTO;
	}

	@Override
	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception {
		GenericSearchandPaginationUtil<UserModel> util = new GenericSearchandPaginationUtil<>();
		Map<String, String> columnNames = new HashMap<>();
		columnNames.put("role", "name");
		return GenericUtils.wrapOrEmptyPagination(
				util.searchByColumns(paginationDTO, columnNames, UserModel.class, entityManager));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (!username.isEmpty()) {
			UserModel user = userRepository.findByUsername(username);
			if (user != null) {
				if (user.getStatus() == RecordStatusUtil.RECORD_ACTIVE) {
					return new User(user.getUsername(), user.getPassword(), getAuthorities(user.getRole()));
				} else if (user.getStatus() == RecordStatusUtil.RECORD_INACTIVE)
					throw new DisabledException("User with username: " + username + " is disabled or inactive!");
				else if (user.getStatus() == RecordStatusUtil.RECORD_DELETED)
					throw new DisabledException("User with username: " + username + " is deleted!");
			} else {
				throw new UsernameNotFoundException("User not found with username: " + username);
			}
		}
		return null;
	}

	@Override
	public UserDetails validateOTP(LoginDto loginDto) {
		if (otpService.validateOTP(loginDto.getUsername(), loginDto.getOtp())) {
			UserModel user = userRepository.findByUsername(loginDto.getUsername());
			user.setLastLogin(new Date());
			userRepository.save(user);
			return new User(user.getUsername(), user.getPassword(), getAuthorities(user.getRole()));
		}
		return null;
	}

	@Override
	public BaseResponseDTO authenticateMobileUser(String username) {
		if (!username.isEmpty()) {
			UserModel user = userRepository.findByUsername(username);
			if (user != null) {
				if (user.getStatus() == RecordStatusUtil.RECORD_ACTIVE) {
					if (user.getRole().getName().contains("MOBILE")) {
						try {
							NotificationDTO dto = new NotificationDTO(Long.parseLong(username), "",
									StaticValues.SENDOTP.toString());
							Object otp = notificationService.notification(dto);
							user.setPassword(passwordEncoder.encode(otp.toString()));
							user.setLastModifiedBy(3L);
							userRepository.save(user);
							return new BaseResponseDTO(RequestStatusUtil.SUCCESS_RESPONSE.getErrorDescription(),
									RequestStatusUtil.SUCCESS_RESPONSE.getCode(), otp);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else
						return new BaseResponseDTO("WEB user cannot login with Mobile number",
								RequestStatusUtil.BAD_REQUEST.getCode());
				} else if (user.getStatus() == RecordStatusUtil.RECORD_INACTIVE)
					throw new DisabledException("User with username: " + username + " is disabled or inactive!");
				else if (user.getStatus() == RecordStatusUtil.RECORD_DELETED)
					throw new DisabledException("User with username: " + username + " is deleted!");
			} else {
				if (username.matches("^[6-9]{1}[0-9]{9}$")) {
					try {
						NotificationDTO dto = new NotificationDTO(Long.parseLong(username), "",
								StaticValues.SENDOTP.toString());
						Object otp = notificationService.notification(dto);
						if (otp != null) {
							UserModel newUser = new UserModel();
							newUser.setMobile(username);
							newUser.setUsername(username);
							newUser.setPassword(passwordEncoder.encode(otp.toString()));
							newUser.setStatus(RecordStatusUtil.RECORD_ACTIVE);
							newUser.setRole(new Role(2L));
							newUser.setCreatedBy(1L);
							newUser.setCreatedDate(new Date());
							userRepository.save(newUser);
						}
						return new BaseResponseDTO(RequestStatusUtil.SUCCESS_RESPONSE.getErrorDescription(),
								RequestStatusUtil.SUCCESS_RESPONSE.getCode(), otp.toString());
					} catch (Exception e) {
						return new BaseResponseDTO(RequestStatusUtil.CONFLICT.getErrorDescription(),
								RequestStatusUtil.CONFLICT.getCode());
					}

				}
			}
		}
		return null;

	}

	@Override
	public UserDetails socialLoginAuntenticate(UserModel model) throws Exception {
		Optional<UserModel> user = null;
		UserModel userModel = new UserModel();
		if (null != model.getMobile() && !model.getMobile().isEmpty()) {
			user = userRepository.findByMobile(model.getMobile());
			userModel.setUsername(model.getMobile());
		}
		if (null == user && null != model.getEmail() && !model.getEmail().isEmpty()) {
			user = userRepository.findByEmail(model.getEmail());
			userModel.setUsername(model.getEmail());
		}
		if (null != user && user.isPresent())
			return new User(user.get().getUsername(), user.get().getPassword(), getAuthorities(user.get().getRole()));
		else {

			if (StringUtils.hasLength(model.getFirstName())) {
				userModel.setFirstName(model.getFirstName());
			}

			if (StringUtils.hasLength(model.getLastName())) {
				userModel.setLastName(model.getLastName());
			}
			if (StringUtils.hasLength(model.getEmail())) {
				userModel.setEmail(model.getEmail());
			}
			if (StringUtils.hasLength(model.getGender())) {
				userModel.setGender(model.getGender());
			}
			if (StringUtils.hasLength(model.getPhotoUrl())) {
				userModel.setPhotoUrl(model.getPhotoUrl());
			}
			if (!ObjectUtils.isEmpty(model.getBirthDate())) {
				userModel.setBirthDate(model.getBirthDate());
			}
			if (!ObjectUtils.isEmpty(model.getMobile())) {
				userModel.setMobile(model.getMobile());
			}
			if (null == userModel.getUsername()) {
				userModel.setUsername(userModel.getUsername() == null
						? model.getMobile() == null ? model.getEmail() : model.getMobile()
						: userModel.getUsername());
			}
			userModel.setStatus(RecordStatusUtil.RECORD_ACTIVE);
			userModel.setRole(new Role(2L, "MOBILE"));
			userModel.setCreatedBy(1L);
			userModel.setCreatedDate(new Date());
			userModel.setPassword(passwordEncoder.encode(
					userModel.getUsername() == null ? model.getMobile() == null ? model.getEmail() : model.getMobile()
							: userModel.getUsername()));
			userRepository.save(userModel);
			UserModel usermodel = userRepository.findByUsername(userModel.getUsername());
			return new User(usermodel.getUsername(), usermodel.getPassword(), getAuthorities(usermodel.getRole()));
		}

	}

	private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.getName()));
		// roles.stream().map(p -> new
		// SimpleGrantedAuthority(p.getName())).forEach(authorities::add);
		return authorities;
	}

	public BaseResponseDTO getNonMobileUserListWithPaginate(PaginationDTO paginationDTO) {
		GenericSearchandPaginationUtil<UserModel> util = new GenericSearchandPaginationUtil<>();
		Map<String, String> columnNames = new HashMap<>();
		columnNames.put("role", "name");
		paginationDTO.setSearchData("status#!2:role#!ROLE_MOBILE");
		return GenericUtils.wrapOrEmptyPagination(
				util.searchBySpecificColumns(paginationDTO, columnNames, UserModel.class, entityManager));
	}

	@Override
	public BaseResponseDTO forgetPassword(String emailId) throws AuthenticationFailedException,Exception {
		Optional<UserModel> user = userRepository.findByEmail(emailId);
		if (user.isPresent()) {
			Integer token = otpService.generateOtp(user.get().getEmail());
			NotificationDTO dto = new NotificationDTO(StaticValues.SENDEMAIL.toString(), user.get().getEmail(),
					StaticValues.RESET_PASSWORD.toString(),
					"Hi " + user.get().getFirstName()
							+ ",\n Greetings from Track&Trail!, \n Use below Link to reset password. \n"
							+ passwordResetURL + "?token=" + token + "?emailId=" + user.get().getEmail());
			notificationService.notification(dto);
			return new BaseResponseDTO(RequestStatusUtil.SUCCESS_RESPONSE.getErrorDescription(),
					RequestStatusUtil.SUCCESS_RESPONSE.getErrorCode(),token);
		} else {
			throw new UsernameNotFoundException("User not found with email: " + user.get().getEmail());
		}
	}

	@Override
	public BaseResponseDTO resetPassword(LoginDto loginDto) throws Exception {
		if (otpService.validateOTP(loginDto.getUsername(), loginDto.getOtp())) {
			Optional<UserModel> user = userRepository.findByEmail(loginDto.getUsername());
			user.get().setPassword(passwordEncoder.encode(loginDto.getPassword()));
			userRepository.save(user.get());
			return new BaseResponseDTO(RequestStatusUtil.SUCCESS_RESPONSE.getErrorDescription(),
					RequestStatusUtil.SUCCESS_RESPONSE.getErrorCode());
		} else {
			throw new Exception("Password Reset Failed.");
		}
	}

	@Override
	public UserModel save(UserModel user) throws Exception {
		user.setPassword(passwordEncoder.encode(user.getUsername()));
		user.setStatus(RecordStatusUtil.RECORD_ACTIVE);
		user.setRole(new Role(2L));
		return userRepository.save(objectMapper.convertValue(user, UserModel.class));

	}

	@Override
	public BaseResponseDTO getAllMobileUser() throws Exception {
		return GenericUtils.wrapOrEmptyList(userRepository.findByRoleNameContaining("MOBILE"));

	}

	@Override
	public BaseResponseDTO getAllAdminUser() throws Exception {
		return GenericUtils.wrapOrEmptyList(userRepository.findByRoleNameNotContaining("MOBILE"));

	}

	@Override
	public BaseResponseDTO getAllRole() throws Exception {
		return GenericUtils.wrapOrEmptyList(roleRepository.findByNameNotContaining("MOBILE"));

	}

}
