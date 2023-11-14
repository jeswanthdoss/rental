package com.rental.dto.request;


import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentalRequest {
		
	@Schema(description ="rentalId, the Rental Id")
	@JsonProperty("rentalId")
	private String rentalId;
	
	@Schema(description="userId")
	@JsonProperty("userId")
	private String userId;
	
	@Schema(description="vehicleId")
	@JsonProperty("vehicleId")
	private String vehicleId;

	@Schema(description="rentalStartTime")
	@JsonProperty("rentalStartTime")
	private Timestamp rentalStartTime;
	
	@Schema(description="rentalEndTime")
	@JsonProperty("rentalEndTime")
	private Timestamp rentalEndTime;
	
	private String action;
	
}
