package com.rental.entity;


import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="rental")
public class Rental extends Maintenance {

	@Id
	@Column(name="RENTAL_ID")
	private String rentalId;
	
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="VEHICLE_ID")
	private String vehicleId;

	@Column(name="RENTAL_START_TIME")
	private Timestamp rentalStartTime;
	
	@Column(name="RENTAL_END_TIME")
	private Timestamp rentalEndTime;

}
