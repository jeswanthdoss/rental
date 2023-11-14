package com.rental.service;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;

import com.rental.dto.request.RentalRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RentalService {

	Map<String, Object> getAllRentals(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			Map<String, String> logMap, Map<String, String> requestHeaderMap);

	Map<String, Object> getRental(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String rentalId,
			String userId, Map<String, String> logMap, Map<String, String> requestHeaderMap);

	Map<String, Object> createOrUpdateRental(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			@Valid @RequestBody RentalRequest rentalRequest, Map<String, String> logMap,
			Map<String, String> requestHeaderMap);

	Map<String, Object> deleteRental(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
			String rentalId, String userId, Map<String, String> logMap, Map<String, String> requestHeaderMap);

}