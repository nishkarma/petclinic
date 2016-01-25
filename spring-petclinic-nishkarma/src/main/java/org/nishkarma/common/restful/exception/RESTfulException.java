package org.nishkarma.common.restful.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

public class RESTfulException extends WebApplicationException {

	private static final long serialVersionUID = -9167052682441685446L;

	public RESTfulException(Response.Status status) {
		super(status);
	}

	public RESTfulException(String message, Response.Status status) {
		super(Response.status(status).entity(message).build());
	}

	public RESTfulException(Response response) {
		super(response);
	}

	// -- Exception Mappers
	@Provider
	public static class RESTfulExceptionMapper implements
			ExceptionMapper<RESTfulException> {

		@Override
		public Response toResponse(RESTfulException exception) {
			Response r = exception.getResponse();
			return Response.status(r.getStatus())
					.entity(exception.getMessage()).build();
		}
	}
}
