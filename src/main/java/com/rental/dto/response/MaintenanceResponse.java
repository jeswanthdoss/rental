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
public class MaintenanceResponse extends CoreResponse{

	@Schema(description="createdTimestamp")
	@JsonProperty("createdTimestamp")
	private Timestamp createdTimestamp;

	@Schema(description="createdBy")
	@JsonProperty("createdBy")
	private String createdBy;
	
	@Schema(description="modifiedTimestamp")
	@JsonProperty("modifiedTimestamp")
	private Timestamp modifiedTimestamp;

	@Schema(description="modifiedBy")
	@JsonProperty("modifiedBy")
	private String modifiedBy;
	
}
