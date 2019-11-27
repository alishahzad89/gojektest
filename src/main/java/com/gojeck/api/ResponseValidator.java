package com.gojeck.api;

import org.testng.asserts.SoftAssert;

import com.google.gson.JsonParser;
import com.jayway.restassured.response.Response;

public class ResponseValidator {

	public void validateResponse(String fileOneName, String fileTwoName) throws Exception {

		JsonParser parser = new JsonParser();
		SoftAssert softassert = new SoftAssert();
		ResponseComparator responseComparator = new ResponseComparator();

		FileReading fileOneData = new FileReading(fileOneName);
		FileReading fileTwoData = new FileReading(fileTwoName);

		String[] expectedData = fileOneData.readFile();
		String[] actualData = fileTwoData.readFile();
		String lineFileOne, lineFileTwo;

		Response expectedResponse;
		Response actualResponse;
		boolean checkReponse;
		boolean isEqual = true;

		for (int i = 0; i < expectedData.length; i++) {
			lineFileOne = expectedData[i].trim();
			lineFileTwo = actualData[i].trim();
			//Check if api is valid api
			checkReponse = responseComparator.checkGetResponse(lineFileOne, lineFileTwo);
			if (checkReponse) {
				expectedResponse = RestUtil.get(lineFileOne);
				actualResponse = RestUtil.get(lineFileTwo);
				// Check both response are same
				boolean checkStaus = responseComparator.checkValidStatusCode(expectedResponse, actualResponse);

				if (!checkStaus) {
					System.out.println(lineFileOne + " not equals " + lineFileTwo);
					softassert.assertEquals(checkStaus, true, "Validate URLs response code valid for line [" + i + "] "
							+ lineFileOne + " || " + lineFileTwo);
				} else {
					isEqual = responseComparator.compareJson(parser.parse(expectedResponse.getBody().asString()),
							parser.parse(actualResponse.getBody().asString()));
					if (isEqual) {
						System.out.println(lineFileOne + " equals " + lineFileTwo);
					} else {
						System.out.println(lineFileOne + " not equals " + lineFileTwo);
					}
				}
			} else {
				System.out.println(lineFileOne + " not equals " + lineFileTwo);
				softassert.assertEquals(checkReponse, true,
						"Validate Get Response are valid for line [" + i + "] " + lineFileOne + " || " + lineFileTwo);
			}

			softassert.assertEquals(isEqual, true,
					"Validate response are equals for line [" + i + "] " + lineFileOne + " || " + lineFileTwo);

		}
		softassert.assertAll();
	}

}
