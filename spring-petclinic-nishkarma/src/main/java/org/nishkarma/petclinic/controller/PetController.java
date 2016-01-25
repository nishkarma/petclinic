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

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.nishkarma.common.exception.NishkarmaException;
import org.nishkarma.common.util.BindingResultDebug;
import org.nishkarma.common.util.NishkarmaLocaleUtil;
import org.nishkarma.common.util.NishkarmaMessageSource;
import org.nishkarma.petclinic.controller.validator.PetValidator;
import org.nishkarma.petclinic.model.Owners;
import org.nishkarma.petclinic.model.Pets;
import org.nishkarma.petclinic.model.Types;
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

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@SessionAttributes("pet")
public class PetController {

	Logger logger = LoggerFactory.getLogger(PetController.class);

	@Autowired
	private ClinicService clinicService;

	@ModelAttribute("types")
	public Collection<Types> populatePetTypes() {
		return this.clinicService.findPetTypes();
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/{internationalPath:[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}}/owners/{ownerId}/pets/new", method = RequestMethod.GET)
	public String initCreationForm(@PathVariable("ownerId") int ownerId,
			Map<String, Object> model) {
		try {
			Owners owner = clinicService.findOwnerById(ownerId);
			Pets pet = new Pets();
			pet.setTypeId(0);

			owner.addPet(pet);
			model.put("pet", pet);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(
					NishkarmaMessageSource.getMessage("exception_message", NishkarmaLocaleUtil.resolveLocale()));

		}
		return "pets/createOrUpdatePetForm";
	}

	@RequestMapping(value = "/{internationalPath:[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}}/owners/{ownerId}/pets/new", method = RequestMethod.POST)
	public String processCreationForm(@ModelAttribute("pet") Pets pet,
			BindingResult result, SessionStatus status) {
		try {
			new PetValidator().validate(pet, result);
			BindingResultDebug.print(result);

			if (result.hasErrors()) {
				return "pets/createOrUpdatePetForm";
			}

			clinicService.savePet(pet);
			status.setComplete();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(
					NishkarmaMessageSource.getMessage("exception_message", NishkarmaLocaleUtil.resolveLocale()));

		}

		return "redirect:/{internationalPath}/owners/{ownerId}";
	}

	@RequestMapping(value = "/{internationalPath:[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}}/owners/*/pets/{petId}/edit", method = RequestMethod.GET)
	public String initUpdateForm(@PathVariable("petId") int petId,
			Map<String, Object> model) {
		try {
			Pets pet = this.clinicService.findPetById(petId);
			model.put("pet", pet);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(
					NishkarmaMessageSource.getMessage("exception_message", NishkarmaLocaleUtil.resolveLocale()));

		}
		return "pets/createOrUpdatePetForm";
	}

	@RequestMapping(value = "/{internationalPath:[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}}/owners/{ownerId}/pets/{petId}/edit", method = {
			RequestMethod.PUT, RequestMethod.POST })
	public String processUpdateForm(@ModelAttribute("pet") Pets pet,
			BindingResult result, SessionStatus status) {
		try {
			// we're not using @Valid annotation here because it is easier to
			// define
			// such validation rule in Java
			new PetValidator().validate(pet, result);
			if (result.hasErrors()) {
				return "pets/createOrUpdatePetForm";
			}
			this.clinicService.savePet(pet);
			status.setComplete();

			return "redirect:/{internationalPath}/owners/{ownerId}";
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(
					NishkarmaMessageSource.getMessage("exception_message", NishkarmaLocaleUtil.resolveLocale()));

		}
	}

}
