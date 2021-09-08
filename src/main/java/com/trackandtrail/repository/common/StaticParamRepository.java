package com.trackandtrail.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackandtrail.model.common.StaticParamModel;

public interface StaticParamRepository extends JpaRepository<StaticParamModel, Long> {

}
