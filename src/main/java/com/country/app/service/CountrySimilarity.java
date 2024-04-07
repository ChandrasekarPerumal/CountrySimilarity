package com.country.app.service;

import java.util.List;

import org.json.simple.JSONArray;

import com.country.app.entity.BorderCountry;
import com.country.app.entity.Country;

public interface CountrySimilarity {

	String urlForName = "https://restcountries.com/v3.1/name/";
	String urlForCode = "https://restcountries.com/v3.1/alpha/";

	Country getSimilarities(String name);

	String getCountryByName(String countryName);

	String getCountryByCode(String countryCode);

	List<BorderCountry> getBorderCountriesDetails(JSONArray borderCountryCode, List<String> langCode,
			BorderCountry borderCountry);

	BorderCountry getSimilarInfo(String jsonData, List<String> langCode, BorderCountry borderCountry2);

}
