package com.trackandtrail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackandtrail.model.event.EventType;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {

}
