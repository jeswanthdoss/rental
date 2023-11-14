package com.rental.service;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.rental.config.RentalConfiguration;
import com.rental.constants.RentalConstants;
import com.rental.dto.request.RentalRequest;
import com.rental.helper.RentalHelper;
import com.rental.utils.RentalUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RentalServiceImpl implements RentalService, RentalConstants
 {
	private final String className = this.getClass().getName();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RentalHelper rentalHelper;
	
	@Autowired
	private RentalUtils rentalUtils;
	
	@Autowired
	private RentalConfiguration rentalConfiguration;

	@Override
	public Map<String, Object>  getRental(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			String rentalId, String userId, Map<String, String> logMap, Map<String, String> requestHeaderMap)
	{
		// TODO Auto-generated method stub
		String methodName = "getRental";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		
		boolean isSuccess = false;
		Map<String, Object> response = new HashMap<>();
				
		try {
			logger.info("rentalConfiguration customValue={}",rentalConfiguration.getCustomValue());
			rentalHelper.validateGetOrDeleteRental(rentalId, userId, response, logMap);

			if(!response.isEmpty()) {
				return response;
			}
			
			response=rentalHelper.getRental(rentalId, userId, logMap);
			
			if(!response.isEmpty() && response.get(HTTP_STATUS) == HttpStatus.OK) {
				isSuccess=true;
			}
			
		}catch(Exception e) {
			logger.error("Exception in RentalServiceImpl getRental method {}",e.getMessage());
			rentalUtils.handleExceptions(e.getMessage(), response, logMap);
		}finally {
			rentalUtils.finallyDo(isSuccess, response, logMap);
		}
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		 return response;
	}


	@Override
	public Map<String, Object> createOrUpdateRental(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@Valid @RequestBody RentalRequest rentalRequest, Map<String, String> logMap,  Map<String, String> requestHeaderMap) {
		// TODO Auto-generated method stub
		String methodName = "createOrUpdateRental";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		
		boolean isSuccess = false;
		Map<String, Object> response = new HashMap<>();
				
		try {			
			rentalHelper.validateCreateOrUpdateRental(rentalRequest, response, logMap);

			if(!response.isEmpty()) {
				return response;
			}
			
			response=rentalHelper.createOrUpdateRental(rentalRequest, logMap);
			
			if(!response.isEmpty() && response.get(HTTP_STATUS) == HttpStatus.OK) {
				isSuccess=true;
			}
			
		}catch(Exception e) {
			logger.error("Exception in RentalServiceImpl getRental method {}",e.getMessage());
			rentalUtils.handleExceptions(e.getMessage(), response, logMap);
		}finally {
			rentalUtils.finallyDo(isSuccess, response, logMap);
		}
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		 return response;
	}

	@Override
	public Map<String, Object> deleteRental(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			String rentalId, String userId, Map<String, String> logMap, Map<String, String> requestHeaderMap) {
		// TODO Auto-generated method stub
		String methodName = "deleteRental";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		
		boolean isSuccess = false;
		Map<String, Object> response = new HashMap<>();
				
		try {			
			rentalHelper.validateGetOrDeleteRental(rentalId, userId, response, logMap);

			if(!response.isEmpty()) {
				return response;
			}
			
			response=rentalHelper.deleteRental(rentalId, userId, logMap);
			
			if(!response.isEmpty() && response.get(HTTP_STATUS) == HttpStatus.OK) {
				isSuccess=true;
			}
			
		}catch(Exception e) {
			logger.error("Exception in RentalServiceImpl getRental method {}",e.getMessage());
			rentalUtils.handleExceptions(e.getMessage(), response, logMap);
		}finally {
			rentalUtils.finallyDo(isSuccess, response, logMap);
		}
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		 return response;
	}


	@Override
	public Map<String, Object> getAllRentals(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			Map<String, String> logMap, Map<String, String> requestHeaderMap) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String methodName = "getAllRentals";
		logger.debug(METHOD_CLASS_ENTRY, methodName,className);
		
		boolean isSuccess = false;
		Map<String, Object> response = new HashMap<>();
				
		try {			
			
			response=rentalHelper.getAllRentals(logMap);
			
			if(!response.isEmpty() && response.get(HTTP_STATUS) == HttpStatus.OK) {
				isSuccess=true;
			}
			
		}catch(Exception e) {
			logger.error("Exception in RentalServiceImpl getAllRentals method {}", e.getMessage());
			rentalUtils.handleExceptions(e.getMessage(), response, logMap);
		}finally {
			rentalUtils.finallyDo(isSuccess, response, logMap);
		}
		logger.debug(METHOD_CLASS_EXIT, methodName,className);

		 return response;
	}
	

}
