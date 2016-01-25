package org.nishkarma.book.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BookController {

	@RequestMapping(value = "/book/books", method = RequestMethod.GET)
	public String initCreationForm(Map<String, Object> model) {
		return "book/books";
	}
}
