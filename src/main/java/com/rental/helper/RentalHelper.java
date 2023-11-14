package com.rental.helper;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.rental.constants.RentalConstants;
import com.rental.dto.request.RentalRequest;
import com.rental.entity.Rental;
import com.rental.repository.helper.RentalRepositoryHelper;
import com.rental.utils.RentalUtils;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RentalHelper implements RentalConstants {

	private final String className = this.getClass().getName();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	//private RentalConfiguration rentalConfiguration;

	@Autowired
	private RentalRepositoryHelper rentalRepositoryHelper;

	@Autowired
	private RentalUtils rentalUtils;
	
	public void validateGetOrDeleteRental(String rentalId, String userId,
			Map<String, Object> response, Map<String, String> logMap) {
		String methodName = "validateGetOrDeleteRental";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);

		if (StringUtils.isBlank(rentalId) && StringUtils.isBlank(userId)) {
			rentalUtils.prepareResponse(WARNING_CODE, RENTAL_ID_USER_ID_MISSING_MSG,
					WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);
	}
	
	public void validateCreateOrUpdateRental(RentalRequest rentalRequest,
			Map<String, Object> response, Map<String, String> logMap) {
		String methodName = "validateCreateOrUpdateRental";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		
		if(null != rentalRequest) {
			if (StringUtils.isBlank(rentalRequest.getRentalId())){
				rentalRequest.setAction(CREATE);
				logMap.put(ACTION, CREATE);
			}else {
				logMap.put("rentalId", rentalRequest.getRentalId());
				logMap.put(ACTION, UPDATE);
				rentalRequest.setAction(UPDATE);
			}
			
			if(rentalRequest.getAction().equalsIgnoreCase(CREATE)) {
				if(StringUtils.isBlank(rentalRequest.getUserId())) {
					rentalUtils.prepareResponse(WARNING_CODE, USER_ID_MISSING_MSG,
							WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
				}
				
				if(StringUtils.isBlank(rentalRequest.getVehicleId())) {
					rentalUtils.prepareResponse(WARNING_CODE, VEHICLE_ID_MISSING_MSG,
							WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
				}
				
				if(null == rentalRequest.getRentalStartTime()) {
					rentalUtils.prepareResponse(WARNING_CODE, RENTAL_START_TIME_MISSING_MSG,
							WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
				}
				
				if(null == rentalRequest.getRentalEndTime()) {
					rentalUtils.prepareResponse(WARNING_CODE, RENTAL_END_TIME_MISSING_MSG,
							WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
				}
			}
			
			if(rentalRequest.getAction().equalsIgnoreCase(UPDATE)) {
				if(StringUtils.isBlank(rentalRequest.getUserId()) &&
						StringUtils.isBlank(rentalRequest.getVehicleId()) &&
						null == rentalRequest.getRentalStartTime() &&
								null == rentalRequest.getRentalEndTime()) {
					rentalUtils.prepareResponse(WARNING_CODE, USER_VEHICLE_RENTAL_START_END_TIME_MISSING_MSG,
							WARNING_MSG, HttpStatus.BAD_REQUEST, response, logMap);
				}				
			}
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);
	}
	
	public Map<String, Object> getAllRentals(Map<String, String> logMap) {
		String methodName = "getAllRentals";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		List<Rental> rentalsList = null;
		Map<String, Object> response = new HashMap<>();
		
		String recordNotFoundMsg = "No Rentals found";
		
		rentalsList = rentalRepositoryHelper.findAllRentals();
		
		logger.info("rentalsList={}", rentalsList);
		
		if (null != rentalsList && rentalsList.size() > 0) {
			populateGetRentalsResponse(rentalsList, response, logMap);
		} else {
			rentalUtils.prepareResponse(NOT_FOUND_CODE, recordNotFoundMsg,
					WARNING_MSG, HttpStatus.NOT_FOUND, response, logMap);
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;

	}
	
	public Map<String, Object> getRental(String rentalId, String userId, Map<String, String> logMap) {
		String methodName = "getRental";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		Optional<Rental> optRental;
		Rental rental = null;
		Map<String, Object> response = new HashMap<>();
		
		String recordNotFoundMsg = null;
		
		if (StringUtils.isNotBlank(rentalId)) {
			logMap.put("rentalId", rentalId);
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for rentalId: "+rentalId;
			optRental = rentalRepositoryHelper.findByRentalId(rentalId);

		} else if (StringUtils.isNotBlank(userId)) {
			logMap.put("userId", userId);
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for userId: "+userId;
			optRental = rentalRepositoryHelper.findByUserId(userId);
		}else {
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG;
			optRental = null;
		}

		if (null!= optRental && optRental.isPresent()) {
			rental = optRental.get();
		}
		logger.info("rental={}", rental);

		if (null != rental) {
			populateGetRentalResponse(rental, response);
		} else {
			rentalUtils.prepareResponse(NOT_FOUND_CODE, recordNotFoundMsg,
					WARNING_MSG, HttpStatus.NOT_FOUND, response, logMap);
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;

	}
	
	private void populateGetRentalsResponse(List<Rental> rentalsList, Map<String, Object> response,
			Map<String, String> logMap) {
		String methodName = "populateGetRentalsResponse";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		

		response.put("rentals", rentalsList);
		/*
		for(Rental rental: rentalsList) {
			Map<String, Object> rentalMap = new HashMap<String, Object>();

			rentalMap.put("rentalId", rental.getRentalId());
			rentalMap.put("model", rental.getModel());
			rentalMap.put("type", rental.getType());
			rentalMap.put("userId", rental.getUserId());
			rentalMap.put("availability", rental.getAvailability());
			rentalMap.put("createdTimestamp", rental.getCreatedTimestamp());
			rentalMap.put("createdBy", rental.getCreatedBy());
			rentalMap.put("modifiedTimestamp", rental.getModifiedTimestamp());
			rentalMap.put("modifiedBy", rental.getModifiedBy());	
		}
		*/

		response.put(HTTP_STATUS, HttpStatus.OK);
		logger.info("populateGetRentalsResponse response={}", response);

		logger.debug(METHOD_CLASS_EXIT, methodName, className);
	}

	private void populateGetRentalResponse(Rental rental, Map<String, Object> response) {
		String methodName = "populateGetRentalResponse";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);

		response.put("rentalId", rental.getRentalId());
		response.put("userId", rental.getUserId());
		response.put("vehicleId", rental.getVehicleId());
		response.put("rentalStartTime", rental.getRentalStartTime());
		response.put("rentalEndTime", rental.getRentalEndTime());
		response.put("createdTimestamp", rental.getCreatedTimestamp());
		response.put("createdBy", rental.getCreatedBy());
		response.put("modifiedTimestamp", rental.getModifiedTimestamp());
		response.put("modifiedBy", rental.getModifiedBy());
		
		response.put(HTTP_STATUS, HttpStatus.OK);
		logger.debug(METHOD_CLASS_EXIT, methodName, className);
	}
	
	public Map<String, Object> createOrUpdateRental(RentalRequest rentalRequest, Map<String, String> logMap) {
		String methodName = "createOrUpdateRental";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		Optional<Rental> optRental;
		Rental rental = null;
		Map<String, Object> response = new HashMap<>();
		
		String recordNotFoundMsg = null;
		String successMsg = null;
		
		if(rentalRequest.getAction().equalsIgnoreCase(UPDATE)) {
			optRental = rentalRepositoryHelper.findByRentalId(rentalRequest.getRentalId());
			
			if (null!= optRental && optRental.isPresent()) {
				rental = optRental.get();
				
				if (null != rental) {
					if(StringUtils.isNotBlank(rentalRequest.getUserId())) {
						rental.setUserId(rentalRequest.getUserId());
					}
					
					if(StringUtils.isNotBlank(rentalRequest.getVehicleId())) {
						rental.setVehicleId(rentalRequest.getVehicleId());
					}
					
					if(null != rentalRequest.getRentalStartTime()) {
						rental.setRentalStartTime(rentalRequest.getRentalStartTime());
					}
					
					if(null != rentalRequest.getRentalEndTime())  {
						rental.setRentalEndTime(rentalRequest.getRentalEndTime());
					}
					
					rental.setModifiedBy(RENTAL_APP);
					rental.setModifiedTimestamp(new Timestamp(System.currentTimeMillis()));
					
					rentalRepositoryHelper.saveRental(rental);
					successMsg = UPDATE + " completed for rentalId: "+rentalRequest.getRentalId();
					rentalUtils.prepareResponse(SUCCESS_CODE, successMsg,
							SUCCESS_MSG, HttpStatus.OK, response, logMap);
				}	
			}else {
				recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for rentalId: "+rentalRequest.getRentalId();
				rentalUtils.prepareResponse(NOT_FOUND_CODE, recordNotFoundMsg,
						WARNING_MSG, HttpStatus.NOT_FOUND, response, logMap);
			}
		}
		
		if(rentalRequest.getAction().equalsIgnoreCase(CREATE)) {
			rental = new Rental();
			String rentalId = "rental-"+UUID.randomUUID().toString();
			rental.setRentalId(rentalId);
			rental.setUserId(rentalRequest.getUserId());
			rental.setVehicleId(rentalRequest.getVehicleId());
			rental.setRentalStartTime(rentalRequest.getRentalStartTime());
			rental.setRentalEndTime(rentalRequest.getRentalEndTime());
			
			rental.setCreatedBy(RENTAL_APP);
			rental.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));			
			rental.setModifiedBy(RENTAL_APP);
			rental.setModifiedTimestamp(new Timestamp(System.currentTimeMillis()));
			
			rentalRepositoryHelper.saveRental(rental);
			successMsg = CREATE + " completed. rentalId: "+rentalId;
			logMap.put("rentalId", rentalId);
			response.put("rentalId", rentalId);
			
			rentalUtils.prepareResponse(SUCCESS_CODE, successMsg,
					SUCCESS_MSG, HttpStatus.OK, response, logMap);
		}
	
		logger.debug(METHOD_CLASS_EXIT, methodName, className);
		
		return response;
	}
	
	public Map<String, Object> deleteRental(String rentalId, String userId, Map<String, String> logMap) {
		String methodName = "deleteRental";
		logger.debug(METHOD_CLASS_ENTRY, methodName, className);
		Optional<Rental> optRental;
		Rental rental = null;
		Map<String, Object> response = new HashMap<>();
		String successMsg = null;
		String recordNotFoundMsg = null;
		
		if (StringUtils.isNotBlank(rentalId)) {
			logMap.put("rentalId", rentalId);
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for rentalId: "+rentalId;
			successMsg = "Rental with rentalId: "+rentalId+" deleted successfully.";
			
			optRental = rentalRepositoryHelper.findByRentalId(rentalId);

		} else if (StringUtils.isNotBlank(userId)) {
			logMap.put("userId", userId);
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG + " for userId: "+userId;
			successMsg = "Rental with userId: "+userId+" deleted successfully.";

			optRental = rentalRepositoryHelper.findByUserId(userId);
		}else {
			recordNotFoundMsg = RECORD_NOT_FOUND_MSG;
			optRental = null;
		}

		if (null!= optRental && optRental.isPresent()) {
			rental = optRental.get();
		}
		logger.debug("rental={}", rental);

		if (null != rental) {
			rentalRepositoryHelper.deleteRental(rental);
			rentalUtils.prepareResponse(SUCCESS_CODE, successMsg,
					SUCCESS_MSG, HttpStatus.OK, response, logMap);
			
		} else {
			rentalUtils.prepareResponse(NOT_FOUND_CODE, recordNotFoundMsg,
					WARNING_MSG, HttpStatus.NOT_FOUND, response, logMap);
		}

		logger.debug(METHOD_CLASS_EXIT, methodName, className);

		return response;
	}

}
