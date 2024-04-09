package com.country.app.cntr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.country.app.entity.BorderCountry;
import com.country.app.entity.Country;
import com.country.app.service.CountrySimilarityService;
import com.country.app.service.CountrySimilarityServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CountrySimilarityControllerTest {

	@InjectMocks
	private CountrySimilarityController controller;
	private CountrySimilarityService countrySimilarityService;

	@Mock
	private CountrySimilarityService countrySimilarity;

	private JSONParser jsonParser;

	@BeforeEach
	void setup() {
		countrySimilarityService = mock(CountrySimilarityServiceImpl.class);
		controller = new CountrySimilarityController();
		jsonParser = new JSONParser();
	}

	@Test
	void testGetBorderCountriesSimilarity_InvalidInput1() {
		// Testing with invalid input
		ResponseEntity<?> response = controller.getBorderCountriesSimilarity("Fr");

		// Verification
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Enter valid country name", response.getBody());
	}

	@Test
	void testGetBorderCountriesSimilarity_InvalidInput2() {
		// Testing with invalid input
		ResponseEntity<?> response = controller.getBorderCountriesSimilarity(" ");

		// Verification
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Enter valid country name", response.getBody());
	}

	@Test
	void testGetBorderCountriesSimilarity_InvalidInput3() {
		// Testing with invalid input
		ResponseEntity<?> response = controller.getBorderCountriesSimilarity("Count");

		// Verification
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Enter valid country name", response.getBody());
	}

	@Test
	void testGetBorderCountriesSimilarity_InvalidInput4() {
		// Testing with invalid input
		ResponseEntity<?> response = controller.getBorderCountriesSimilarity("USA");

		// Verification
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Enter valid country name", response.getBody());
	}

	@Test
	void testGetBorderCountriesSimilarity_InvalidInput5() {
		// Testing with invalid input
		ResponseEntity<?> response = controller.getBorderCountriesSimilarity("!@#35$");

		// Verification
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Enter valid country name", response.getBody());
	}

	@Test
	void testGetBorderCountriesSimilarity_InvalidInput6() {
		// Testing with invalid input
		ResponseEntity<?> response = controller.getBorderCountriesSimilarity(null);

		// Verification
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Enter valid country name", response.getBody());
	}



}
