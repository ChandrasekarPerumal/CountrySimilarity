package com.country.app.entity;

import java.util.List;

public class Country {


	private String requestedCountryName;

	private List<BorderCountry> borderCountries;

	public List<BorderCountry> getBorderCountries() {
		return borderCountries;
	}

	public void setBorderCountries(List<BorderCountry> borderCountries) {
		this.borderCountries = borderCountries;
	}

	public String getRequestedCountryName() {
		return requestedCountryName;
	}

	public void setRequestedCountryName(String requestedCountryName) {
		this.requestedCountryName = requestedCountryName;
	}

}
