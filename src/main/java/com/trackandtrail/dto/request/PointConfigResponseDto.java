package com.trackandtrail.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointConfigResponseDto {
	
	
	private String nickName;
	
	private String title;
	
	private Float rangeFrom;
	
	private Float rangeTo;
	
	private Integer totalEarnedPoints;

}
