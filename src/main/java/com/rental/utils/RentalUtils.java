package com.rental.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.constants.RentalConstants;

@Component
public class RentalUtils implements RentalConstants{

	private ObjectMapper objectMapper = new ObjectMapper();

	public Map<String, String> initiateLogMap(String apiName, String appName, long startTime, Map<String, String> requestHeaderMap ){
		
		Map<String, String> logMap = new LinkedHashMap<>();
		
		logMap.put(API_NAME, apiName);
		logMap.put(APP_NAME, appName);
		logMap.put(START_TIME, String.valueOf(startTime));
		logMap.put(CODE, SUCCESS_CODE);
		logMap.put(MSG, SUCCESS_MSG);
		
		return logMap;
	}
	
	public void logMap(Logger logger, Map<String, String> params) {
		
		if(params == null) {
			return;
		}
			
			Map<String, String> logMessage = new LinkedHashMap<>();
			
			logMessage.put(LOG_TYPE, EXIT);
			logMessage.putAll(params);
			
			logger.info(objectToJSONString(logMessage, false));
	}
	
	public String objectToJSONString(Object obj, boolean prettyPrint) {
		String jsonString = "";
		try {
			jsonString = prettyPrint ? objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj) : 
				objectMapper.writer().writeValueAsString(obj);
		}catch(JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
	
	public void prepareResponse(String code, String desc, String msg, 
			HttpStatus httpStatus, Map<String, Object> response, Map<String, String> logMap) {
		response.put(HTTP_STATUS, httpStatus);
		response.put(CODE, code);
		response.put(MSG, msg);
		response.put(DESC, desc);
		
		logMap.put(HTTP_STATUS, httpStatus.toString());
		logMap.put(CODE, code);
		logMap.put(MSG, msg);
		logMap.put(DESC, desc);
	}
	
	public void handleExceptions(String exceptionMsg, Map<String, Object> response, Map<String, String> logMap) {
		response.put(HTTP_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
		response.put(CODE, FAILURE_CODE);
		response.put(DESC, exceptionMsg);
		response.put(MSG, FAILURE_MSG);
		
		logMap.put(HTTP_STATUS, HttpStatus.INTERNAL_SERVER_ERROR.toString());
		logMap.put(CODE, FAILURE_CODE);
		logMap.put(DESC, exceptionMsg);
		logMap.put(MSG, FAILURE_MSG);
	}
	
	public void finallyDo(boolean isSuccess, Map<String, Object> response, Map<String, String> logMap) {
		
		if(isSuccess) {
			response.put(CODE, SUCCESS_CODE);
			response.put(MSG, SUCCESS_MSG);
		}else if (null != response && response.isEmpty()) {
			response.put(CODE, FAILURE_CODE);
			response.put(MSG, FAILURE_MSG);			
		}
		
		if(null != logMap && null != logMap.get(START_TIME)) {
			long startTime = Long.valueOf(logMap.get(START_TIME));
			long endTime = System.currentTimeMillis();
			
			long responseTime = endTime - startTime;
			
			logMap.put(END_TIME, String.valueOf(endTime));
			logMap.put(RESPONSE_TIME, String.valueOf(responseTime));
		}
		
	}

}
