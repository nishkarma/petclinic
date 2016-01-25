package org.nishkarma.common.restful.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

public class RESTfulValidateException extends WebApplicationException {

	private static final long serialVersionUID = -9167052682441685446L;

	public RESTfulValidateException(Response.Status status) {
		super(status);
	}

	public RESTfulValidateException(String message, Response.Status status) {
		super(Response.status(status).entity(message).build());
	}

	public RESTfulValidateException(Response response) {
		super(response);
	}

	// -- Exception Mappers
	@Provider
	public static class RESTfulValidateExceptionMapper implements
			ExceptionMapper<RESTfulValidateException> {

		@Override
		public Response toResponse(RESTfulValidateException exception) {
			Response r = exception.getResponse();
			return Response.status(r.getStatus())
					.entity(exception.getMessage()).build();
		}
	}
}
