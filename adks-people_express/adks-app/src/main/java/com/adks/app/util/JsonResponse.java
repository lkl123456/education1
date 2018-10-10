package com.adks.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by luke on 1/20/14.
 */
public class JsonResponse {
	public static final String SUCCESS = "000";
	public static final String FAILED = "500";

	private Boolean result;
	private String code;
	private Object message;

	public static JsonResponse success(Object message) {
		return setJson(true, SUCCESS, message);
	}

	public static JsonResponse error(Object message) {
		return setJson(false, FAILED, message);
	}

	public static JsonResponse setJson(Boolean result, String code, Object message) {
		JsonResponse jsonResponse = new JsonResponse();
		jsonResponse.setResult(result);
		jsonResponse.setCode(code);
		jsonResponse.setMessage(message);
		return jsonResponse;
	}

	public static String build(Boolean result, String code, Object message) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(new JsonResponse(result, code, message));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "{error}";
		}
	}

	private JsonResponse(Boolean result, String code, Object message) {
		this.result = result;
		this.code = code;
		this.message = message;
	}

	public JsonResponse() {

	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
}
