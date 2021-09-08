package com.trackandtrail.dto;

import java.util.List;

import com.trackandtrail.model.configuration.PointsConfiguration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointsConfigurationDto {

	private Long id;

	private List<PointsConfiguration> pointReward;

}
