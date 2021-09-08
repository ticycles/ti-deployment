package com.trackandtrail.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.BadgeRewardConfDto;
import com.trackandtrail.model.configuration.BadgeConfiguration;
import com.trackandtrail.model.configuration.BadgeRewardConf;
import com.trackandtrail.repository.BadgeConfigurationRepository;
import com.trackandtrail.repository.BadgeRewardConfRepository;
import com.trackandtrail.service.BadgeRewardService;
import com.trackandtrail.util.GenericUtils;

@Service
public class BadgeRewardServiceImpl implements BadgeRewardService {

	@Autowired
	BadgeConfigurationRepository badgeConfigurationRepository;

	@Autowired
	BadgeRewardConfRepository badgeRewardConf;

	@Override
	@Transactional
	public BaseResponseDTO save(BadgeRewardConfDto badgeRewardConfDto) throws Exception {
		BaseResponseDTO resp = new BaseResponseDTO();

		BadgeRewardConf badge = new BadgeRewardConf();

		badge.setActivityDistance(badgeRewardConfDto.getActivityDistance());
		badge.setActivityPoints(badgeRewardConfDto.getActivityPoints());
		badge.setModule(badgeRewardConfDto.getModule());
		badge.setNoOfBlogsPerDay(badgeRewardConfDto.getNoOfBlogsPerDay());
		badge.setBlogPoints(badgeRewardConfDto.getBlogPoints());
		badge.setShopItemPerDay(badgeRewardConfDto.getShopItemPerDay());
		badge.setShopPoints(badgeRewardConfDto.getShopPoints());
		badge.setEventPerDay(badgeRewardConfDto.getEventPerDay());
		badge.setEventPoints(badgeRewardConfDto.getEventPoints());
		badge.setEventEnrolledPerDay(badgeRewardConfDto.getEventEnrolledPerDay());
		badge.setEventEnrolledPoints(badgeRewardConfDto.getEventEnrolledPoints());
		badge.setTntEventEnrolledPerDay(badgeRewardConfDto.getTntEventEnrolledPerDay());
		badge.setTntEventEnrolledPoints(badgeRewardConfDto.getTntEventEnrolledPoints());
		badge.setNoOfChallengesPerDay(badgeRewardConfDto.getNoOfChallengesPerDay());
		badge.setChallengePoint(badgeRewardConfDto.getChallengePoint());
		badge.setChallengeEnrolledPerDay(badgeRewardConfDto.getChallengeEnrolledPerDay());
		badge.setChallengeEnrolledPoint(badgeRewardConfDto.getChallengeEnrolledPoint());

		List<BadgeConfiguration> config = new ArrayList<BadgeConfiguration>();

		badgeRewardConfDto.getBadges().forEach(reward -> {

			BadgeConfiguration badgeConfig = new BadgeConfiguration();
			badgeConfig.setBadgeRewardConf(badge);
			badgeConfig.setBatchName(reward.getBatchName());
			badgeConfig.setDescription(reward.getDescription());
			badgeConfig.setRangeFrom(reward.getRangeFrom());
			badgeConfig.setRangeTo(reward.getRangeTo());
			badgeConfig.setImage(reward.getImage());
			config.add(badgeConfig);

		});

		badge.setBadges(config);
		
		Optional<BadgeRewardConf> badgeRec = badgeRewardConf.findBadgeRewardConfModule(badgeRewardConfDto.getModule());
		if(badgeRec.isPresent()) {
			badgeRewardConf.deleteById(badgeRec.get().getId());
			badgeConfigurationRepository.saveAll(config);
			Long insertedId = badge.getBadges().size() > 0 ? badgeRewardConf.save(badge).getId() : 0;
			resp.setMsg("Badges updated successfully");
			resp.setErrorCode(200);
			return resp;

		}else {
			badgeConfigurationRepository.saveAll(config);
			Long insertedId = badge.getBadges().size() > 0 ? badgeRewardConf.save(badge).getId() : 0;
			resp.setMsg("Badges created successfully");
			resp.setErrorCode(200);
			return resp;
		}


	}

	@Override
	public BaseResponseDTO getByModule(String module ) throws Exception {
		return GenericUtils.wrapOrEmptyList(badgeConfigurationRepository.findByMModule(module));

	}

	@Override
	public BaseResponseDTO getEarnPointsConfig() throws Exception {
		return GenericUtils.wrapOrEmptyList(badgeRewardConf.findAll());
	}

}
