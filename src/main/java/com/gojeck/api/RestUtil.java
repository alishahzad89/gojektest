package com.gojeck.api;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class RestUtil {

	public static Response get(String url) throws CustomException {
		try {
		return RestAssured.given().header("Content-Type", "application/json").get(url);
		}catch(Exception e) {
			throw new CustomException("Invalid URL");
		}
	}
}
