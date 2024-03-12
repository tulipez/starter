package com.tulipez.starter.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectReader;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;

public class JacksonUtils {

	public static <T> T updatePojo(T pojo, JsonObject userSpecif) {
		ObjectReader objectReader = DatabindCodec.mapper().readerForUpdating(pojo);
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
