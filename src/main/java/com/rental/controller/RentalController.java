package com.rental.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.constants.RentalConstants;
import com.rental.dto.request.RentalRequest;
import com.rental.dto.response.RentalResponse;
import com.rental.dto.response.RentalsResponse;
import com.rental.service.RentalService;
import com.rental.utils.RentalUtils;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import javax.validation.Valid;


@RestController
@Validated
@RequestMapping(path="/")
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title = RentalConstants.APP_NAME, description = RentalConstants.APP_DESC, version = RentalConstants.APP_VERSION))
public class RentalController implements RentalConstants{
	
	private final String className = this.getClass().getName();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RentalUtils rentalUtils;
	
	@Autowired
	private RentalService rentalService;
	
	private ObjectMapper objectMapper;
	
	@GetMapping("/")
	@Operation(description = "startUp API", summary = "startUp", tags = "startUp")
	public ResponseEntity<String> startUp(){
		String methodName = "startUp";
		logger.info(METHOD_CLASS_ENTRY, methodName,className);

		return ResponseEntity.ok().body(START_UP_SUCCESS);
	}
	
	@GetMapping("/rentals")
	public ResponseEntity<RentalsResponse> getAllRentals(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@RequestHeader Map<String, String> requestHeaderMap) throws JsonProcessingException{
		String methodName = "getAllRentals";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		long startTime = System.currentTimeMillis();
		
		Map <String, String> logMap = rentalUtils.initiateLogMap(methodName, RENTAL_APP, startTime, requestHeaderMap);
		logMap.put(ACTION, GET);

		Map<String, Object> response = rentalService.getAllRentals(httpRequest, httpResponse,
							logMap, requestHeaderMap);

		HttpStatus httpStatus = HttpStatus.OK;
		
		if(null!=response && null != response.get(HTTP_STATUS)) {
			httpStatus = (HttpStatus) response.get(HTTP_STATUS);
			response.remove(HTTP_STATUS);
		}
		
		if(null == objectMapper) {
			objectMapper = new ObjectMapper();
		}
		
		String jsonResponse = objectMapper.writeValueAsString(response);
		RentalsResponse rentalsResponse = objectMapper.readValue(jsonResponse, RentalsResponse.class);
		
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		rentalUtils.logMap(logger, logMap);
		
		return new ResponseEntity<>(rentalsResponse, httpStatus);
	}
	
	@GetMapping("/rental")
	public ResponseEntity<RentalResponse> getRental(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@RequestParam(value="rentalId", defaultValue="") final String rentalId, 
			@RequestParam(value="userId", defaultValue="") final String userId,
			@RequestHeader Map<String, String> requestHeaderMap) throws JsonProcessingException{
		String methodName = "getRental";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		long startTime = System.currentTimeMillis();
		
		Map <String, String> logMap = rentalUtils.initiateLogMap(methodName, RENTAL_APP, startTime, requestHeaderMap);
		logMap.put(ACTION, GET);

		Map<String, Object> response = rentalService.getRental(httpRequest, httpResponse, rentalId, userId,
							logMap, requestHeaderMap);

		HttpStatus httpStatus = HttpStatus.OK;
		
		if(null!=response && null != response.get(HTTP_STATUS)) {
			httpStatus = (HttpStatus) response.get(HTTP_STATUS);
			response.remove(HTTP_STATUS);
		}
		
		if(null == objectMapper) {
			objectMapper = new ObjectMapper();
		}
		
		String jsonResponse = objectMapper.writeValueAsString(response);
		RentalResponse rentalResponse = objectMapper.readValue(jsonResponse, RentalResponse.class);
		
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		rentalUtils.logMap(logger, logMap);
		
		return new ResponseEntity<>(rentalResponse, httpStatus);
	}
	
	@PostMapping("/rental")
	public ResponseEntity<RentalResponse> createOrUpdateRental(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@Valid @RequestBody RentalRequest rentalRequest,
			@RequestHeader Map<String, String> requestHeaderMap) throws JsonProcessingException{
		String methodName = "createOrUpdateRental";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		long startTime = System.currentTimeMillis();
		
		Map <String, String> logMap = rentalUtils.initiateLogMap(methodName, RENTAL_APP, startTime, requestHeaderMap);
				
		Map<String, Object> response =  rentalService.createOrUpdateRental(httpRequest, httpResponse,
												rentalRequest, logMap, requestHeaderMap);

		HttpStatus httpStatus = HttpStatus.OK;
		
		if(null!=response && null != response.get(HTTP_STATUS)) {
			httpStatus = (HttpStatus) response.get(HTTP_STATUS);
			response.remove(HTTP_STATUS);
		}
		
		if(null == objectMapper) {
			objectMapper = new ObjectMapper();
		}
		
		String jsonResponse = objectMapper.writeValueAsString(response);
		RentalResponse rentalResponse = objectMapper.readValue(jsonResponse, RentalResponse.class);
		
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		rentalUtils.logMap(logger, logMap);
		
		return new ResponseEntity<>(rentalResponse, httpStatus);
	}
	
	@DeleteMapping("/rental")
	public ResponseEntity<RentalResponse> deleteRental(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@RequestParam(value="rentalId", defaultValue="") final String rentalId, 
			@RequestParam(value="userId", defaultValue="") final String userId,
			@RequestHeader Map<String, String> requestHeaderMap) throws JsonProcessingException{
		String methodName = "deleteRental";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		long startTime = System.currentTimeMillis();
		
		Map <String, String> logMap = rentalUtils.initiateLogMap(methodName, RENTAL_APP, startTime, requestHeaderMap);
		logMap.put(ACTION, DELETE);

		Map<String, Object> response = rentalService.deleteRental(httpRequest, httpResponse, rentalId, userId,
					logMap, requestHeaderMap);

		HttpStatus httpStatus = HttpStatus.OK;
		
		if(null!=response && null != response.get(HTTP_STATUS)) {
			httpStatus = (HttpStatus) response.get(HTTP_STATUS);
			response.remove(HTTP_STATUS);
		}
		
		if(null == objectMapper) {
			objectMapper = new ObjectMapper();
		}
		
		String jsonResponse = objectMapper.writeValueAsString(response);
		RentalResponse rentalResponse = objectMapper.readValue(jsonResponse, RentalResponse.class);
		
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		rentalUtils.logMap(logger, logMap);
		
		return new ResponseEntity<>(rentalResponse, httpStatus);
	}
}
