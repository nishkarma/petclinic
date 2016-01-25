package org.nishkarma.common.restful.exception;

import java.util.Map;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonErrorResponse {

	private Response.Status status = null;
	private Map<String, String> errorMap = null;

	public JsonErrorResponse() {
	}

	public Response.Status getStatus() {
		return status;
	}

	public void setStatus(Response.Status status) {
		this.status = status;
	}

	public Map<String, String> getErrorMap() {
		return errorMap;
	}

	public void setErrorMap(Map<String, String> errorMap) {
		this.errorMap = errorMap;
	}

}
