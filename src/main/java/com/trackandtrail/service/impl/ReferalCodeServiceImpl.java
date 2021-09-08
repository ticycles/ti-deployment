package com.trackandtrail.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.ReferAndEarnDto;
import com.trackandtrail.dto.ReferalCodeDto;
import com.trackandtrail.dto.UserPreferenceDto;
import com.trackandtrail.mapper.ReferalCodeMapper;
import com.trackandtrail.mapper.UserPreferenceMapper;
import com.trackandtrail.model.ReferalCode.ReferAndEarn;
import com.trackandtrail.model.ReferalCode.ReferalCode;
import com.trackandtrail.model.userpreference.UserPreference;
import com.trackandtrail.repository.ReferAndEarnRepository;
import com.trackandtrail.repository.ReferalCodeRepository;
import com.trackandtrail.service.ReferalCodeService;
import com.trackandtrail.util.RequestStatusUtil;
import com.trackandtrail.util.StringUtils;

@Service
public class ReferalCodeServiceImpl implements ReferalCodeService {

	@Autowired
	ReferalCodeRepository referalCodeRepository;

	@Autowired
	ReferAndEarnRepository referAndEarnRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public BaseResponseDTO save(ReferalCodeDto referalCodeDto) throws Exception {
		BaseResponseDTO baseResponseDTO = new BaseResponseDTO();
		HttpStatus httpStatus;

		if (referalCodeDto.getUser().getId() != null) {
			Optional<ReferalCode> user = referalCodeRepository.findByUserId(referalCodeDto.getUser().getId());
			if (user.isPresent()) {
				baseResponseDTO.setResponseContent(user.get().getCode());
				baseResponseDTO.setMsg("Code Alredy Exist for this user");
				return baseResponseDTO;
			}

			else {
				String uuid = StringUtils.getAlphaNumericString();
				referalCodeDto.setCode(uuid);
				referalCodeDto.setPoints(0);
				referalCodeRepository.save(objectMapper.convertValue(referalCodeDto, ReferalCode.class));
				baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
						RequestStatusUtil.CREATED_RESPONSE.getErrorCode(), uuid);

			}
		}

		return baseResponseDTO;
	}

	@Override
	public BaseResponseDTO create(ReferAndEarnDto referAndEarnDto) throws Exception {
		BaseResponseDTO baseResponseDTO = new BaseResponseDTO();

		if (referAndEarnDto.getUser().getId() != null) {
			Optional<ReferAndEarn> user = referAndEarnRepository.findByUserId(referAndEarnDto.getUser().getId());

			if (!user.isPresent()) {
				Optional<ReferalCode> code = referalCodeRepository.findByCode(referAndEarnDto.getCode());
				if (code.isPresent()) {
					if (code.get().getUser().getId() != referAndEarnDto.getUser().getId()) {
						code.get().setPoints(code.get().getPoints() + 50);
						referalCodeRepository.save(code.get());
						Optional<ReferalCode> codeByUser = referalCodeRepository
								.findByUserId(referAndEarnDto.getUser().getId());
						if (codeByUser.isPresent()) {
							codeByUser.get().setPoints(codeByUser.get().getPoints() + 50);
							referalCodeRepository.save(codeByUser.get());
						}
						referAndEarnRepository.save(objectMapper.convertValue(referAndEarnDto, ReferAndEarn.class));
						baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
								RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
						baseResponseDTO.setResponseContent("You have earned 50 points");
					}else {
						baseResponseDTO.setMsg("Invalid code");
						return baseResponseDTO;
					}
				} else {
					baseResponseDTO.setMsg("Code does not exist");
					return baseResponseDTO;
				}
			} else {
				baseResponseDTO.setMsg("User already used the referal code");
				return baseResponseDTO;
			}

		}

		return baseResponseDTO;

	}

}