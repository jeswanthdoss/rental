package com.rental.dto.response;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RentalResponse extends MaintenanceResponse{
	
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
}
