package com.trackandtrail.common;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableBase {

	
	@CreatedBy
	@Column(name = "created_by", updatable = false, nullable = true)
	protected Long createdBy;

	@CreatedDate
	@Temporal(TIMESTAMP)
	@Column(name = "created_on", updatable = false, nullable = true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date createdDate;

	@LastModifiedBy
	@Column(name = "updated_by")
	protected Long lastModifiedBy;

	@LastModifiedDate
	@Temporal(TIMESTAMP)
	@Column(name = "updated_on")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date lastModifiedDate;

	@Column(columnDefinition = "integer default 1",nullable = false)
	private Integer status=1;

}
