package com.rental.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoreResponse {

	@Schema(description="code")
	@JsonProperty("code")
	private String code;

	@Schema(description="msg")
	@JsonProperty("msg")
	private String msg;
	
	@Schema(description="desc")
	@JsonProperty("desc")
	private String desc;
	
}
