package com.rental.constants;

public interface RentalConstants {

	String RENTAL_APP = "Rental_App";
	String START_UP_SUCCESS ="startUp Success.";

	String APP_NAME = "appName";
	String APP_DESC = "appDesc";
	String APP_VERSION = "1.0.0";
	
	String API_NAME = "apiName";
	String START_TIME = "startTime";
	String END_TIME = "endTime";
	String RESPONSE_TIME = "responseTime";
		
	String HTTP_STATUS = "httpStatus";

	String ACTION = "action";
	String LOG_TYPE = "logType";
	String EXIT = "EXIT";
	String METHOD_CLASS_ENTRY = "Entering method={} class={}";
	String METHOD_CLASS_EXIT = "Exiting method={} class={}";
	
	String CODE="code";
	String MSG="msg";
	String DESC="desc";
	
	String GET = "get";
	String CREATE = "create";
	String UPDATE = "update";
	String DELETE = "delete";
	
	String SUCCESS_CODE = "200";
	String SUCCESS_MSG = "SUCCESS";
	
	String FAILURE_CODE = "500";
	String FAILURE_MSG = "FAILURE";
	
	String WARNING_CODE = "400";
	String WARNING_MSG = "WARNING";
	
	String NOT_FOUND_CODE = "404";
	String RECORD_NOT_FOUND_MSG = "Record Not Found in the Database";
	
	String RENTAL_ID_USER_ID_MISSING_MSG = "Either rentalId or userId should be present";	
	String USER_ID_MISSING_MSG = "userId should be present for action=create";
	String VEHICLE_ID_MISSING_MSG = "vehicleId should be present for action=create";
	String RENTAL_START_TIME_MISSING_MSG = "rentalStartTime should be present for action=create";
	String RENTAL_END_TIME_MISSING_MSG = "rentalEndTime should be present for action=create";
	String USER_VEHICLE_RENTAL_START_END_TIME_MISSING_MSG = "Atleast one out of userId, vehicleId, rentalStartTime or rentalEndTime should be present for action=update";

}
