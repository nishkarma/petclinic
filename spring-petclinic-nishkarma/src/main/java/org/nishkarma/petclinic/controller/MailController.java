/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2012, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.nishkarma.petclinic.controller;

import java.io.IOException;
import java.util.Locale;

import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.nishkarma.common.exception.NishkarmaException;
import org.nishkarma.common.util.NishkarmaLocaleUtil;
import org.nishkarma.common.util.NishkarmaMessageSource;
import org.nishkarma.petclinic.mail.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MailController {

	Logger logger = LoggerFactory.getLogger(MailController.class);

	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "/mail/new", method = RequestMethod.GET)
	public String index() {
		return "mail/mail";
	}

	/* Sending confirmation page. */
	@RequestMapping(value = "/mail/sent", method = RequestMethod.GET)
	public String sent() {
		return "mail/sent";
	}

	/*
	 * Send HTML mail (simple)
	 */
	@RequestMapping(value = "/mail/sendMailSimple", method = RequestMethod.POST)
	public String sendSimpleMail(
			@RequestParam("recipientName") final String recipientName,
			@RequestParam("recipientEmail") final String recipientEmail,
			final Locale locale) throws MessagingException {
		try {

			this.emailService.sendSimpleMail(recipientName, recipientEmail,
					locale);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(NishkarmaMessageSource.getMessage(
					"exception_message", NishkarmaLocaleUtil.resolveLocale()));

		}

		return "redirect:sent";

	}

	/*
	 * Send HTML mail with attachment.
	 */
	@RequestMapping(value = "/mail/sendMailWithAttachment", method = RequestMethod.POST)
	public String sendMailWithAttachment(
			@RequestParam("recipientName") final String recipientName,
			@RequestParam("recipientEmail") final String recipientEmail,
			@RequestParam("attachment") final MultipartFile attachment,
			final Locale locale) throws MessagingException, IOException {

		logger.debug("recipientName=" + recipientName);
		logger.debug("recipientEmail=" + recipientEmail);

		try {

			this.emailService.sendMailWithAttachment(recipientName,
					recipientEmail, attachment.getOriginalFilename(),
					attachment.getBytes(), attachment.getContentType(), locale);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(NishkarmaMessageSource.getMessage(
					"exception_message", NishkarmaLocaleUtil.resolveLocale()));

		}

		return "redirect:sent";

	}

	/*
	 * Send HTML mail with inline image
	 */
	@RequestMapping(value = "/mail/sendMailWithInlineImage", method = RequestMethod.POST)
	public String sendMailWithInline(
			@RequestParam("recipientName") final String recipientName,
			@RequestParam("recipientEmail") final String recipientEmail,
			@RequestParam("image") final MultipartFile image,
			final Locale locale) throws MessagingException, IOException {

		logger.debug("recipientName=" + recipientName);
		logger.debug("recipientEmail=" + recipientEmail);

		try {

			this.emailService.sendMailWithInline(recipientName, recipientEmail,
					image.getName(), image.getBytes(), image.getContentType(),
					locale);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(NishkarmaMessageSource.getMessage(
					"exception_message", NishkarmaLocaleUtil.resolveLocale()));

		}

		return "redirect:sent";

	}

}
