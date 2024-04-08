package com.country.app.cntr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.country.app.entity.Country;
import com.country.app.service.CountrySimilarity;

@RestController
@RequestMapping("/api/v1/country")
public class CountrySimilarityController {

	@Autowired
	private CountrySimilarity countrySimilarity;

	@GetMapping("/get/")
	public ResponseEntity<?> getBorderCountriesSimilarity(@RequestParam String name) {

		if (name == null) {
			return new ResponseEntity<>("Enter valid country name", HttpStatus.BAD_REQUEST);
		}

		if (name.length() < 3) {
			return new ResponseEntity<>("Enter valid country name", HttpStatus.BAD_REQUEST);
		}

		try {
			Country country = countrySimilarity.getSimilarities(name);
			if (country != null) {
				return new ResponseEntity<>(country, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(country, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			return new ResponseEntity<>("Enter valid country name", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
