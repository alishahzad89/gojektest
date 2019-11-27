package gojektest;

import java.util.Map;

import org.testng.annotations.Test;

import com.gojeck.api.DataReader;
import com.gojeck.api.ResponseValidator;

public class testgojek {
	private Map<String, String> testData;
	@Test(description = "Compare response of api listed on two files")
	public void compareResponse() throws Exception {
		//Read Test data from "testdata.json"
		testData = DataReader.readData("testdata.json", "CompareResponse");
		new ResponseValidator().validateResponse(testData.get("FileOneName"), testData.get("FileTwoName"));
	}

}
