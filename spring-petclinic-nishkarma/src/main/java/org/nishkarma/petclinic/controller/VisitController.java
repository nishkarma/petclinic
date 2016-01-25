/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nishkarma.petclinic.controller;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.nishkarma.common.exception.NishkarmaException;
import org.nishkarma.common.util.NishkarmaLocaleUtil;
import org.nishkarma.common.util.NishkarmaMessageSource;
import org.nishkarma.petclinic.model.Pets;
import org.nishkarma.petclinic.model.Visits;
import org.nishkarma.petclinic.service.ClinicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@SessionAttributes("visit")
public class VisitController {
	Logger logger = LoggerFactory.getLogger(VisitController.class);

	@Autowired
	private ClinicService clinicService;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/{internationalPath:[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}}/owners/*/pets/{petId}/visits/new", method = RequestMethod.GET)
	public String initNewVisitForm(@PathVariable("petId") int petId,
			Map<String, Object> model) {
		try {
			Pets pet = this.clinicService.findPetById(petId);
			Visits visit = new Visits();
			pet.addVisit(visit);
			model.put("visit", visit);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(
					NishkarmaMessageSource.getMessage("exception_message", NishkarmaLocaleUtil.resolveLocale()));

		}
		return "pets/createOrUpdateVisitForm";
	}

	@RequestMapping(value = "/{internationalPath:[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}}/owners/{ownerId}/pets/{petId}/visits/new", method = RequestMethod.POST)
	public String processNewVisitForm(
			@Valid @ModelAttribute("visit") Visits visit, BindingResult result,
			SessionStatus status) {
		try {
			if (result.hasErrors()) {
				return "pets/createOrUpdateVisitForm";
			}
			this.clinicService.saveVisit(visit);
			status.setComplete();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(
					NishkarmaMessageSource.getMessage("exception_message", NishkarmaLocaleUtil.resolveLocale()));

		}
		return "redirect:/{internationalPath}/owners/{ownerId}";

	}

	@RequestMapping(value = "/{internationalPath:[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}}/owners/*/pets/{petId}/visits", method = RequestMethod.GET)
	public ModelAndView showVisits(@PathVariable int petId) {
		ModelAndView mav = null;
		try {
			mav = new ModelAndView("visitList");
			mav.addObject("visits", this.clinicService.findPetById(petId)
					.getVisits());
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(
					NishkarmaMessageSource.getMessage("exception_message", NishkarmaLocaleUtil.resolveLocale()));
		}
		return mav;
	}

}
