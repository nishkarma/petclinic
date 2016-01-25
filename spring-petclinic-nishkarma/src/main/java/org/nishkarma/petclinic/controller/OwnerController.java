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

import java.net.URLEncoder;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.nishkarma.common.exception.NishkarmaException;
import org.nishkarma.common.paging.model.PagingUtil;
import org.nishkarma.common.util.BindingResultDebug;
import org.nishkarma.common.util.NishkarmaLocaleUtil;
import org.nishkarma.common.util.NishkarmaMessageSource;
import org.nishkarma.petclinic.model.FindOwnerParam;
import org.nishkarma.petclinic.model.Owners;
import org.nishkarma.petclinic.service.ClinicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@SessionAttributes(types = Owners.class)
public class OwnerController {

	Logger logger = LoggerFactory.getLogger(OwnerController.class);
	/*
	 * @Autowired private MessageSource messageSource;
	 */

	@Autowired
	private ClinicService clinicService;

	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/{internationalPath:[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}}/owners/new", method = RequestMethod.GET)
	public String initCreationForm(Map<String, Object> model) {
		Owners owner = new Owners();
		model.put("owner", owner);
		return "owners/createOrUpdateOwnerForm";
	}

	@RequestMapping(value = "/{internationalPath:[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}}/owners/new", method = RequestMethod.POST)
	public String processCreationForm(
			@Valid @ModelAttribute("owner") Owners owner, BindingResult result,
			SessionStatus status) {
		int ownerId = 0;

		try {
			if (result.hasErrors()) {
				BindingResultDebug.print(result);
				return "owners/createOrUpdateOwnerForm";
			}

			clinicService.saveOwner(owner);
			status.setComplete();

			ownerId = owner.getId();

		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(NishkarmaMessageSource.getMessage(
					"exception_message", NishkarmaLocaleUtil.resolveLocale()));

		}
		return "redirect:/{internationalPath}/owners/" + ownerId;

	}

	@RequestMapping(value = "/{internationalPath:[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}}/owners/find", method = RequestMethod.GET)
	public String initFindForm(Map<String, Object> model) {
		/*
		Locale locale = NishkarmaLocaleUtil.resolveLocale();

		if (locale != null) {
			logger.debug("locale.getCountry()=" + locale.getCountry());
			logger.debug("locale.getDisplayCountry()="
					+ locale.getDisplayCountry());
			logger.debug("locale.getDisplayLanguage()="
					+ locale.getDisplayLanguage());
			logger.debug("locale.getDisplayName()=" + locale.getDisplayName());
			logger.debug("locale.getLanguage()=" + locale.getLanguage());
			logger.debug("locale.toLanguageTag()=" + locale.toLanguageTag());
			logger.debug("locale.toString()=" + locale.toString());
		}
		*/
		FindOwnerParam findOwnerParam = new FindOwnerParam();

		model.put("findOwnerParam", findOwnerParam);
		
		return "owners/findOwners";
	}

	@RequestMapping(value = "/{internationalPath:[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}}/owners", method = RequestMethod.GET)
	public String processFindForm(FindOwnerParam findOwnerParam,
			BindingResult result, Map<String, Object> model) {
		try {
			// allow parameterless GET request for /owners to return all records
			if (findOwnerParam.getLastName() == null) {
				findOwnerParam.setLastName(""); // empty string signifies
												// broadest possible search
			}
			
			logger.debug("findOwnerParam.getPage_page()="
					+ findOwnerParam.getPage_page());
			logger.debug("findOwnerParam.getPage_size()="
					+ findOwnerParam.getPage_size());

			findOwnerParam.makePageQuery();

			// find owners by last name
			Collection<Owners> results = this.clinicService
					.findOwnerByLastName(findOwnerParam);

			long findCount = this.clinicService
					.findOwnerByLastNameCount(findOwnerParam);

			findOwnerParam.setTotalPages(PagingUtil.getTotalPages(
					findOwnerParam.getPage_size(), findCount));

			if (results.size() < 1) {
				// no owners found
				result.rejectValue("lastName", "notFound", "not found");

				BindingResultDebug.print(result);

				return "owners/findOwners";
			}
			if (results.size() > 1) {
				// multiple owners found
				model.put("selections", results);
				model.put("findOwnerParam", findOwnerParam);

				String pageUrl = "owners.html?lastName="
						+ (URLEncoder.encode(findOwnerParam.getLastName(),
								"UTF-8")) + "&page_page=";
				model.put("pageUrl", pageUrl);

				return "owners/ownersList";
			} else {
				// 1 owner found
				Owners owner = results.iterator().next();
				return "redirect:/{internationalPath}/owners/" + owner.getId();
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));

			throw new NishkarmaException(NishkarmaMessageSource.getMessage(
					"exception_message", NishkarmaLocaleUtil.resolveLocale()));
		}
	}

	@RequestMapping(value = "/{internationalPath:[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}}/owners/{ownerId}/edit", method = RequestMethod.GET)
	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId,
			Model model) {
		try {
			Owners owner = this.clinicService.findOwnerById(ownerId);
			model.addAttribute(owner);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(NishkarmaMessageSource.getMessage(
					"exception_message", NishkarmaLocaleUtil.resolveLocale()));

		}
		return "owners/createOrUpdateOwnerForm";
	}

	@RequestMapping(value = "/{internationalPath:[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}}/owners/{ownerId}/edit", method = RequestMethod.POST)
	public String processUpdateOwnerForm(@Valid Owners owner,
			BindingResult result, SessionStatus status) {
		try {
						
			if (result.hasErrors()) {
				return "owners/createOrUpdateOwnerForm";
			}
			this.clinicService.saveOwner(owner);
			status.setComplete();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(NishkarmaMessageSource.getMessage(
					"exception_message", NishkarmaLocaleUtil.resolveLocale()));

		}
		return "redirect:/{internationalPath}/owners/{ownerId}";
	}

	/**
	 * Custom handler for displaying an owner.
	 * 
	 * @param ownerId
	 *            the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@RequestMapping("/{internationalPath:[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}}/owners/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {

		ModelAndView mav = null;
		try {
			mav = new ModelAndView("owners/ownerDetails");
			Owners owner = this.clinicService.findOwnerById(ownerId);
			mav.addObject("owner", owner);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(NishkarmaMessageSource.getMessage(
					"exception_message", NishkarmaLocaleUtil.resolveLocale()));

		}
		return mav;
	}

}
