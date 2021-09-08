package com.trackandtrail.mapper;

import com.trackandtrail.dto.BadgeConfigurationDto;
import com.trackandtrail.model.configuration.BadgeConfiguration;

public class BadgeConfigurationMapper {

	public static void toBadgeConfiguration(BadgeConfigurationDto s, BadgeConfiguration d) {
		if (s == null) {
			return;
		}

		if (s.getBadgeRewardConf() != null)
			d.setBadgeRewardConf(s.getBadgeRewardConf());

		if (s.getBatchName() != null)
			d.setBatchName(s.getBatchName());

		if (s.getDescription() != null)
			d.setDescription(s.getDescription());

		if (s.getImage() != null)
			d.setImage(s.getImage());

		if (s.getRangeFrom() != null)
			d.setRangeFrom(s.getRangeFrom());

		if (s.getRangeTo() != null)
			d.setRangeTo(s.getRangeTo());
	}

}
