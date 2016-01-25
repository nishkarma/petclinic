package org.nishkarma.common.restful.exception;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

public class JsonErrorUtil {

	public static Response getErrorResponse(Response.Status status,
			Map<String, String> errorMap) {
		JsonErrorResponse jsonErrorResponse = new JsonErrorResponse();
		jsonErrorResponse.setStatus(status);
		jsonErrorResponse.setErrorMap(errorMap);

		Response response = Response.status(status).entity(jsonErrorResponse)
				.build();
		return response;
	}

	public static Response getErrorResponse(Response.Status status,
			String errorKey, String errorMessage) {

		Map<String, String> errorMap = new HashMap<String, String>();
		errorMap.put(errorKey, errorMessage);

		JsonErrorResponse jsonErrorResponse = new JsonErrorResponse();
		jsonErrorResponse.setStatus(status);
		jsonErrorResponse.setErrorMap(errorMap);

		Response response = Response.status(status).entity(jsonErrorResponse)
				.build();
		return response;
	}

}
