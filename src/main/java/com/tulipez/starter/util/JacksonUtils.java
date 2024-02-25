package com.tulipez.starter.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import io.vertx.core.json.JsonObject;

public class JacksonUtils {

	public static ObjectMapper objectMapper = new ObjectMapper();
	
	public static <T> T updatePojo(T pojo, JsonObject userSpecif) {
		ObjectReader objectReader = objectMapper.readerForUpdating(pojo);
		try {
			return objectReader.readValue(userSpecif.encode());
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return pojo;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return pojo;
		}
	}
	
}
