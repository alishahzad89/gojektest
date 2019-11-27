package com.gojeck.api;

import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.response.Response;

public class ResponseComparator {
	JsonParser parser = new JsonParser();

	public boolean checkValidURL(String expectedURL, String actualURL) {
		if (expectedURL.substring(0, 8).equalsIgnoreCase("https://")
				&& actualURL.substring(0, 8).equalsIgnoreCase("https://")) {
			return true;
		}
		return false;
	}

	public boolean checkValidStatusCode(Response expected, Response actual) {
		return (expected.statusCode() == actual.getStatusCode());
	}
	
	public boolean checkGetResponse(String urlExpected, String urlActual) {
		try {
			RestUtil.get(urlExpected);
			RestUtil.get(urlActual);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean compareJson(JsonElement expectedJsonElement, JsonElement actualJsonElement) {
		boolean isEqual = true;
		// Check whether both jsonElement are not null
		if (expectedJsonElement != null && actualJsonElement != null) {
			// Check whether both jsonElement are objects
			if (expectedJsonElement.isJsonObject() && actualJsonElement.isJsonObject()) {
				Set<Entry<String, JsonElement>> expectedEntrySet = ((JsonObject) expectedJsonElement).entrySet();
				Set<Entry<String, JsonElement>> actualEntrySet = ((JsonObject) actualJsonElement).entrySet();
				JsonObject json2obj = (JsonObject) actualJsonElement;
				if (expectedEntrySet != null && actualEntrySet != null) {
					// Iterate JSON Elements with Key values
					for (Entry<String, JsonElement> entry : expectedEntrySet) {
						isEqual = isEqual && compareJson(entry.getValue(), json2obj.get(entry.getKey())); // Use recursion
					}
				} else {
					return false;
				}
			}

			// Check whether both jsonElement are arrays
			else if (expectedJsonElement.isJsonArray() && actualJsonElement.isJsonArray()) {
				JsonArray expectedJsonArray = expectedJsonElement.getAsJsonArray();
				JsonArray actualJsonArray = actualJsonElement.getAsJsonArray();
				if (expectedJsonArray.size() != actualJsonArray.size()) {
					return false;
				} else {
					int i = 0;
					// Iterate JSON Array to JSON Elements
					for (JsonElement jsonElement : expectedJsonArray) {
						isEqual = isEqual && compareJson(jsonElement, actualJsonArray.get(i)); // Use recursion
						i++;
					}
				}
			}

			// Check whether both jsonElement are null
			else if (expectedJsonElement.isJsonNull() && actualJsonElement.isJsonNull()) {
				return true;
			}

			// Check whether both jsonElement are primitives
			else if (expectedJsonElement.isJsonPrimitive() && actualJsonElement.isJsonPrimitive()) {
				if (expectedJsonElement.equals(actualJsonElement)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else if (expectedJsonElement == null && actualJsonElement == null) {
			return true;
		} else {
			return false;
		}
		return isEqual;
	}

}
