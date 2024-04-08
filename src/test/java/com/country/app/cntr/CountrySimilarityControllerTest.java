package com.country.app.cntr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.country.app.service.CountrySimilarity;
import com.country.app.service.CountrySimilarityImpl;

public class CountrySimilarityControllerTest {
	
	private CountrySimilarityController controller;
	private CountrySimilarity countrySimilarityService;
	
	@BeforeEach
	void setup() {
		countrySimilarityService = mock(CountrySimilarityImpl.class);		
		controller = new CountrySimilarityController();
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
	
	
	/*
	
	@Test
    void testGetBorderCountriesSimilarity_ValidInput6() {
		Country mockedCountry = new Country();
        mockedCountry.setRequestedCountryName("India");
        when(countrySimilarityService.getSimilarities("India")).thenReturn(mockedCountry);

        // Testing
        ResponseEntity<?> response = controller.getBorderCountriesSimilarity("India");

        // Verification
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedCountry, response.getBody());
    }
    
    */
	
}
