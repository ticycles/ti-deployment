package com.trackandtrail.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import com.trackandtrail.repository.UserRepository;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JPAAuditConfig {

	@Autowired
	UserRepository userRepo;

	@Bean
	public AuditorAware<Long> auditorProvider() {
		if (null != SecurityContextHolder.getContext().getAuthentication())
			return () -> Optional.ofNullable(
					userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
		else
			return () -> Optional.ofNullable(0L);
	}

}
