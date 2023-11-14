package com.rental.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
abstract class Maintenance {
	
	@Column(name="CREATED_TIMESTAMP")
	private Timestamp createdTimestamp;

	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="MODIFIED_TIMESTAMP")
	private Timestamp modifiedTimestamp;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;
	
}
