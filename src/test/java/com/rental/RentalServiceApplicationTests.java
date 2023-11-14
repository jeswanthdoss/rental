package com.rental;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.rental.config.RentalConfiguration;
import com.rental.constants.RentalConstants;
import com.rental.dto.request.RentalRequest;
import com.rental.entity.Rental;
import com.rental.repository.RentalRepository;
import com.rental.service.RentalService;
import com.rental.utils.RentalUtils;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
//@SpringBootTest(properties = { "spring.config.location=classpath:application-test.properties" })
//@TestPropertySource("classpath:application-test.properties")
class RentalServiceApplicationTests implements RentalConstants {

	@Autowired
	private RentalService rentalService;

	@Autowired
	private RentalUtils rentalUtils;

	// @Autowired
	// private RentalConfiguration rentalConfiguration;

	@MockBean
	private RentalRepository rentalRepository;

	private MockHttpServletRequest mockHttpRequest;
	private MockHttpServletResponse mockHttpResponse;
	

	long currentTimeMillis = System.currentTimeMillis();
	Timestamp startTimestamp = new Timestamp(currentTimeMillis + (1 * 24 * 60 * 60 * 1000));
	Timestamp endTimestamp = new Timestamp(currentTimeMillis + (7 * 24 * 60 * 60 * 1000));


	@Test
	void contextLoads() {
	}

	@Test
	public void testCreateRental() {
		String methodName = "testCreateRental";
		long startTime = System.currentTimeMillis();
		Map<String, String> requestHeaderMap = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		boolean assertFlag = false;

		Map<String, String> logMap = rentalUtils.initiateLogMap(methodName, RENTAL_APP, startTime, requestHeaderMap);
		RentalRequest rr = new RentalRequest();

		long currentTimeMillis = System.currentTimeMillis();

		Timestamp startTimestamp = new Timestamp(currentTimeMillis + (1 * 24 * 60 * 60 * 1000));

		Timestamp endTimestamp = new Timestamp(currentTimeMillis + (7 * 24 * 60 * 60 * 1000));

		rr.setUserId("user-1");
		rr.setVehicleId("vehicle-1");
		rr.setRentalStartTime(startTimestamp);
		rr.setRentalEndTime(endTimestamp);

		// System.out.println("testCreateRental
		// customValue="+testConfiguration.getCustomValue());

		response = rentalService.createOrUpdateRental(mockHttpRequest, mockHttpResponse, rr, logMap, requestHeaderMap);

		System.out.println("testCreateRental response=" + response);

		if (null != response && StringUtils.isNotBlank((String) response.get("code"))) {
			String code = (String) response.get("code");

			if (code.equalsIgnoreCase("200")) {
				assertFlag = true;
			}
		}

		System.out.println("testCreateRental assertFlag=" + assertFlag);

		assertTrue(assertFlag);
	}

	@Test
	public void testGetRental() {
		String methodName = "testGetRental";
		long startTime = System.currentTimeMillis();
		Map<String, String> requestHeaderMap = new HashMap<>();
		Map<String, Object> response = new HashMap<>();

		boolean findByRentalId = false;
		boolean findByUserId = false;

		boolean assertFlag = false;

		Map<String, String> logMap = rentalUtils.initiateLogMap(methodName, RENTAL_APP, startTime, requestHeaderMap);
	
		when(rentalRepository.findByRentalId("rental-1")).thenReturn(Optional.of(
				new Rental("rental-1", "user-1", "vehicle-1",startTimestamp, endTimestamp)));

		// findByRentalId
		response = rentalService.getRental(mockHttpRequest, mockHttpResponse, "rental-1", null, logMap, requestHeaderMap);

		System.out.println("testGetRental findByRentalId response=" + response);

		if (null != response && StringUtils.isNotBlank((String) response.get("code"))) {
			String code = (String) response.get("code");

			if (code.equalsIgnoreCase("200")) {
				findByRentalId = true;
			}
		}

		when(rentalRepository.findByUserId("user-1")).thenReturn(Optional.of(
				new Rental("rental-1", "user-1", "vehicle-1",startTimestamp, endTimestamp)));

		// findByUserId
		response = rentalService.getRental(mockHttpRequest, mockHttpResponse, null, "user-1", logMap,
				requestHeaderMap);

		System.out.println("testGetRental findByUserId response=" + response);

		if (null != response && StringUtils.isNotBlank((String) response.get("code"))) {
			String code = (String) response.get("code");

			if (code.equalsIgnoreCase("200")) {
				findByUserId = true;
			}
		}

		if (findByRentalId && findByUserId) {
			assertFlag = true;
		}

		System.out.println("testGetRental assertFlag=" + assertFlag);

		assertTrue(assertFlag);

	}

	@Test
	public void testGetAllRentals() {
		String methodName = "testGetAllRentals";
		long startTime = System.currentTimeMillis();
		Map<String, String> requestHeaderMap = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		boolean assertFlag = false;

		Map<String, String> logMap = rentalUtils.initiateLogMap(methodName, RENTAL_APP, startTime, requestHeaderMap);

		when(rentalRepository.findAll())
				.thenReturn(Stream.of(
						new Rental("rental-1", "user-1", "vehicle-1",startTimestamp, endTimestamp),
						new Rental("rental-2", "user-2", "vehicle-2",startTimestamp, endTimestamp)	
						).collect(Collectors.toList()));

		response = rentalService.getAllRentals(mockHttpRequest, mockHttpResponse, logMap, requestHeaderMap);

		System.out.println("testGetAllRentals response=" + response);

		// assertEquals(2, response.size());

		if (null != response && StringUtils.isNotBlank((String) response.get("code"))) {
			String code = (String) response.get("code");

			if (code.equalsIgnoreCase("200")) {
				assertFlag = true;
			}
		}

		System.out.println("testGetAllRentals assertFlag=" + assertFlag);

		assertTrue(assertFlag);
	}

	@Test
	public void testDeleteRental() {
		String methodName = "testDeleteRental";
		long startTime = System.currentTimeMillis();
		Map<String, String> requestHeaderMap = new HashMap<>();
		Map<String, Object> response = new HashMap<>();

		boolean assertFlag = false;

		Map<String, String> logMap = rentalUtils.initiateLogMap(methodName, RENTAL_APP, startTime, requestHeaderMap);

		Rental rental = new Rental("rental-1", "user-1", "vehicle-1",startTimestamp, endTimestamp);

		when(rentalRepository.findByRentalId("rental-1")).thenReturn(Optional.of(rental));

		doNothing().when(rentalRepository).delete(rental);

		// deleteRental
		response = rentalService.deleteRental(mockHttpRequest, mockHttpResponse, rental.getRentalId(), null, logMap,
				requestHeaderMap);

		System.out.println("testDeleteRental deleteRental response=" + response);

		if (null != response && StringUtils.isNotBlank((String) response.get("code"))) {
			String code = (String) response.get("code");

			if (code.equalsIgnoreCase("200")) {
				assertFlag = true;
			}
		}

		System.out.println("testDeleteRental assertFlag=" + assertFlag);

		assertTrue(assertFlag);

	}

}
