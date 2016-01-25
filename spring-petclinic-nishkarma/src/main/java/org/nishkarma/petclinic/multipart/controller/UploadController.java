package org.nishkarma.petclinic.multipart.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UploadController {

	@RequestMapping(value = "/multipart/upload", method = RequestMethod.GET)
	public String initCreationForm(Map<String, Object> model) {
		return "multipart/upload";
	}
}
