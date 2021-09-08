package com.trackandtrail.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.common.dto.PaginationDTO;
import com.trackandtrail.dto.LoginDto;
import com.trackandtrail.dto.UserDto;
import com.trackandtrail.model.user.UserModel;

public interface UserService extends UserDetailsService {

	public BaseResponseDTO save(UserDto userDTO) throws Exception;

	public UserModel save(UserModel user) throws Exception;

	public BaseResponseDTO update(UserDto userDTO) throws Exception;

	public BaseResponseDTO getAll() throws Exception;
	
	public BaseResponseDTO getAllRole() throws Exception;


	public BaseResponseDTO getAllMobileUser() throws Exception;

	public BaseResponseDTO getAllAdminUser() throws Exception;

	public BaseResponseDTO getById(Long id) throws Exception;

	public BaseResponseDTO deleteById(Long id, boolean flag) throws Exception;

	public BaseResponseDTO searchAndPaginate(PaginationDTO paginationDTO) throws Exception;

	public BaseResponseDTO getNonMobileUserListWithPaginate(PaginationDTO paginationDTO);

	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception;

	public BaseResponseDTO forgetPassword(String emailId) throws Exception;

	public BaseResponseDTO resetPassword(LoginDto loginDto) throws Exception;
	
	public BaseResponseDTO authenticateMobileUser(String username);

	public UserDetails validateOTP(LoginDto loginDto);

	public UserDetails socialLoginAuntenticate(UserModel model) throws Exception;

}
