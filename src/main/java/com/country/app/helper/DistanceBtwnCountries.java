package com.country.app.helper;

public class DistanceBtwnCountries {
	
	// Radius of the Earth in miles
	static double radius = 3958.8;

	/* Used to calculate the distance between two co-ordinates */
	public static double calculateDistance(double[] coordinate1, double[] coordinate2) {


		// Convert latitude and longitude coordinates from degrees to radians
		double lat1Radians = Math.toRadians(coordinate1[0]);
		double lon1Radians = Math.toRadians(coordinate1[1]);
		double lat2Radians = Math.toRadians(coordinate2[0]);
		double lon2Radians = Math.toRadians(coordinate2[1]);

		// Calculate differences in latitude and longitude
		double latDiff = lat2Radians - lat1Radians;
		double lonDiff = lon2Radians - lon1Radians;

		// Calculate the distance using Haversine formula
		double a = Math.pow(Math.sin(latDiff / 2), 2)
				+ Math.cos(lat1Radians) * Math.cos(lat2Radians) * Math.pow(Math.sin(lonDiff / 2), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = radius * c;

		//Round the distance value and return
		return Math.round(distance * 100.0) / 100.0;

	}

}
