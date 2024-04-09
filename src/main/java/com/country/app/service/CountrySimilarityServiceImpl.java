package com.country.app.service;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.country.app.entity.BorderCountry;
import com.country.app.entity.Country;
import com.country.app.helper.DistanceBtwnCountries;

@Service
public class CountrySimilarityServiceImpl implements CountrySimilarityService {

	private JSONParser jsonParser = new JSONParser();

	private final RestTemplate restTemplate;

	@Autowired
	public CountrySimilarityServiceImpl(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}

	private double[] requestCountryLatLong = new double[2];

	public Country getSimilarities(String name) {

		System.out.println("Get - Sim:" + name);
		Country country = new Country(); // Response object
		BorderCountry borderCountry = new BorderCountry();// Object for border-country
		JSONArray bordersCountryCode = null;
		JSONObject jsonCountry = null;
		JSONArray latLong = null;

		List<String> languageCode = new ArrayList<>(); // List of language code
		List<BorderCountry> borderCountryInformation; // To store border countries information

		// Get the information of requested country
		String jsonData = getCountryByName(name);

		try {
			// Parse the json data from string
			JSONArray countries = (JSONArray) jsonParser.parse(jsonData);

			for (Object countryObj : countries) {
				jsonCountry = (JSONObject) countryObj;
				borderCountry = new BorderCountry();
				String independent = jsonCountry.get("independent").toString();
				String landLocked = jsonCountry.get("landlocked").toString();
				String unionMember = jsonCountry.get("unMember").toString();
				if (independent != null)
					borderCountry.setIndependent(independent);
				else
					borderCountry.setIndependent("Not-Available");
				if (landLocked != null)
					borderCountry.setCarDriving(landLocked);
				else
					borderCountry.setCarDriving("Not-Available");

				if (unionMember != null)
					borderCountry.setUnionMember(unionMember);
				else
					borderCountry.setUnionMember("Not-Available");

				// Requested country lat-long
				latLong = (JSONArray) jsonCountry.get("latlng");

				if (latLong.size() >= 2) {
					requestCountryLatLong[0] = (double) latLong.get(0);
					requestCountryLatLong[1] = (double) latLong.get(1);
				}

				country.setRequestedCountryName(name);
				JSONObject lang = (JSONObject) jsonCountry.get("languages");

				for (Object o : lang.keySet()) {
					languageCode.add(o.toString());
				}
				// List of border countries code
				bordersCountryCode = (JSONArray) jsonCountry.get("borders");

				if (bordersCountryCode != null) {
					// get border countries information
					borderCountryInformation = getBorderCountriesDetails(bordersCountryCode, languageCode,
							borderCountry);
					if (borderCountryInformation.size() > 0)
						country.setBorderCountries(borderCountryInformation);
					else
						country.setBorderCountries(null);
				}
			}
			return country;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<BorderCountry> getBorderCountriesDetails(JSONArray borderCountryCode, List<String> langCode,
			BorderCountry borderCountry) {
		List<BorderCountry> bc = new ArrayList<>();
		try {
			// Iterate by country code
			for (Object countryCode : borderCountryCode) {
				// Fetch information of border country by their code
				String jsonData = getCountryByCode(countryCode.toString());
				// Send the Json-data of border country and get similar information and add to
				// list
				bc.add(getSimilarInfo(jsonData, langCode, borderCountry));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bc;
	}

	/* Fetch details of country using country name */
	@Override
	public String getCountryByName(String countryName) {
		return restTemplate.getForObject(urlForName + countryName, String.class);
	}

	/* Fetch details of country using country code */
	@Override
	public String getCountryByCode(String name) {
		return restTemplate.getForObject(urlForCode + name, String.class);
	}

	/*
	 * Extract common information of requested country with it's neighboring
	 * countries
	 */
	@Override
	public BorderCountry getSimilarInfo(String jsonData, List<String> langCode, BorderCountry borderCountry2) {

		BorderCountry borderCountry = new BorderCountry();

		try {
			JSONArray countries = (JSONArray) jsonParser.parse(jsonData);
			for (Object countryObj : countries) {
				JSONObject country = (JSONObject) countryObj;
				JSONObject nameObject = (JSONObject) country.get("name");
				JSONObject lang = (JSONObject) country.get("languages");

				String commonName = nameObject.get("common").toString();
				borderCountry.setCountryName(commonName);

				// Has Freedom or Not
				String independent = country.get("independent").toString();
				if (independent.equalsIgnoreCase(borderCountry2.getIndependent())) {
					borderCountry.setIndependent("Yes");
				} else {
					borderCountry.setIndependent("No");
				}

				// Landlocked is true then can't drive.
				String landLocked = country.get("landlocked").toString();
				if (landLocked.equalsIgnoreCase("false") && borderCountry2.getCarDriving().equalsIgnoreCase("false")) {
					borderCountry.setCarDriving("Yes");
				} else {
					borderCountry.setCarDriving("No");
				}

				// Whether part of union member !
				String unionMember = country.get("unMember").toString();

				if (unionMember.equalsIgnoreCase(borderCountry2.getUnionMember())) {
					borderCountry.setUnionMember("Yes");
				} else {
					borderCountry.setUnionMember("No");
				}
				double[] borderCountryLatLong = new double[2];

				JSONArray latLong = (JSONArray) country.get("latlng");
				if (latLong.size() >= 2) {

					borderCountryLatLong[0] = (double) latLong.get(0);
					borderCountryLatLong[1] = (double) latLong.get(1);
					borderCountry.setDrivingDistance(
							(int) DistanceBtwnCountries.calculateDistance(requestCountryLatLong, borderCountryLatLong));
				}

				for (Object o : lang.keySet()) {
					if (langCode.contains(o.toString())) {
						borderCountry.setCommonLanguage("Yes");
					}
				}
				if (borderCountry.getCommonLanguage() == null) {
					borderCountry.setCommonLanguage("No");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return borderCountry;
	}

}
