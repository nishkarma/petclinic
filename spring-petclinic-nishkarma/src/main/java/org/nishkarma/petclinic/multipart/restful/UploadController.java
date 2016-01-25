package org.nishkarma.petclinic.multipart.restful;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.nishkarma.common.restful.exception.RESTfulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.stereotype.Controller;

@Path("/fileupload")
@Controller
public class UploadController {

	Logger logger = LoggerFactory.getLogger(UploadController.class);

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("hello") String hello,
			@FormDataParam("hello2") String hello2,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		String output = "";

		try {
			logger.debug("[Nishkarma]-hello=" + hello);
			logger.debug("[Nishkarma]-hello2=" + hello2);

			String fileName = fileDetail.getFileName();

			if (StringUtils.isEmpty(fileName)) {
				return Response.status(200).build();
			}

			try {
				fileName = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				logger.error(ExceptionUtils.getStackTrace(e1));
			}

			String uploadBase = "/Users/nish/git/web-application/spring-petclinic-nishkarma/uploads/";
			
			logger.debug("[Nishkarma]-uploadBase=" + uploadBase);
			logger.debug("[Nishkarma]-fileName=" + fileName);
			
			String uploadedFileLocation = uploadBase + fileName;

			// save it
			saveToFile(uploadedInputStream, uploadedFileLocation);

			output = "File uploaded via Jersey based RESTFul Webservice to: "
					+ uploadedFileLocation;

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new RESTfulException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		return Response.status(200).entity(output).build();

	}

	// save uploaded file to new location
	private void saveToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			OutputStream out = null;
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
	}

}