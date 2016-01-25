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

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.nishkarma.common.exception.NishkarmaException;
import org.nishkarma.common.util.NishkarmaLocaleUtil;
import org.nishkarma.common.util.NishkarmaMessageSource;
import org.nishkarma.petclinic.model.VetsList;
import org.nishkarma.petclinic.service.ClinicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class VetController {
	Logger logger = LoggerFactory.getLogger(VetController.class);

	@Autowired
	private ClinicService clinicService;

	@RequestMapping("/{internationalPath:[a-zA-Z]{2,10}\\-[a-zA-Z]{2,10}}/vets")
	public String showVetList(Map<String, Object> model) {
		try {
			// Here we are returning an object of type 'Vets' rather than a
			// collection of Vet objects
			// so it is simpler for Object-Xml mapping
			VetsList vetslist = new VetsList();
			vetslist.getVetList().addAll(this.clinicService.findVets());
			model.put("vets", vetslist);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new NishkarmaException(
					NishkarmaMessageSource.getMessage("exception_message", NishkarmaLocaleUtil.resolveLocale()));

		}
		return "vets/vetList";
	}

}
