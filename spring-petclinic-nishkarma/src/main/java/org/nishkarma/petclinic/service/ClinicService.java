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
package org.nishkarma.petclinic.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nishkarma.petclinic.model.FindOwnerParam;
import org.nishkarma.petclinic.model.Owners;
import org.nishkarma.petclinic.model.Pets;
import org.nishkarma.petclinic.model.Types;
import org.nishkarma.petclinic.model.Vets;
import org.nishkarma.petclinic.model.Visits;
import org.nishkarma.petclinic.repository.OwnersMapper;
import org.nishkarma.petclinic.repository.PetsMapper;
import org.nishkarma.petclinic.repository.SpecialtiesMapper;
import org.nishkarma.petclinic.repository.TypesMapper;
import org.nishkarma.petclinic.repository.VetSpecialtiesMapper;
import org.nishkarma.petclinic.repository.VetsMapper;
import org.nishkarma.petclinic.repository.VisitsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder for @Transactional
 * and @Cacheable annotations
 * 
 * @author Michael Isvy
 */
@Service
public class ClinicService {

	Logger logger = LoggerFactory.getLogger(ClinicService.class);

	@Autowired
	private OwnersMapper ownersMapper;
	@Autowired
	private PetsMapper petsMapper;
	@Autowired
	private SpecialtiesMapper specialtiesMapper;
	@Autowired
	private TypesMapper typesMapper;
	@Autowired
	private VetsMapper vetsMapper;
	@Autowired
	private VetSpecialtiesMapper vetSpecialtiesMapper;
	@Autowired
	private VisitsMapper visitsMapper;

	@Transactional(readOnly = true)
	public Collection<Types> findPetTypes() throws DataAccessException {
		return typesMapper.findPetTypes();
	}

	@Transactional(readOnly = true)
	public Owners findOwnerById(int id) throws DataAccessException {
		Owners owner = ownersMapper.findById(id);
		logger.debug("[Nishkarma]-" + owner.getId() + ":" + owner.getFirstName()
				+ " " + owner.getLastName());
		loadPetsAndVisits(owner);

		return owner;
	}

	@Transactional(readOnly = true)
	public Collection<Owners> findOwnerByLastName(FindOwnerParam findOwnerParam)
			throws DataAccessException {
		Collection<Owners> owners = ownersMapper.findByLastName(findOwnerParam);

		loadOwnersPetsAndVisits(owners);
		return owners;
	}

	@Transactional(readOnly = true)
	public long findOwnerByLastNameCount(FindOwnerParam findOwnerParam)
			throws DataAccessException {
		return ownersMapper.findByLastNameCount(findOwnerParam);
	}
	
	private void loadOwnersPetsAndVisits(Collection<Owners> owners) {
		for (Owners owner : owners) {
			loadPetsAndVisits(owner);
		}
	}

	public void loadPetsAndVisits(final Owners owner) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", owner.getId().intValue());
		Collection<Pets> pets = petsMapper.findByOwnerId(owner.getId()
				.intValue());

		for (Pets pet : pets) {
			owner.addPet(pet);
			pet.setType(typesMapper.findById(pet.getTypeId()));
			List<Visits> visits = visitsMapper.findByPetId(pet.getId());
			for (Visits visit : visits) {
				pet.addVisit(visit);
			}
		}
	}

	@Transactional
	public void saveOwner(Owners owner) throws DataAccessException {
		if (owner.isNew()) {
			ownersMapper.insert(owner);
		} else {
			ownersMapper.updateByPrimaryKey(owner);
		}
	}

	@Transactional
	public void saveVisit(Visits visit) throws DataAccessException {
		if (visit.isNew()) {
			visitsMapper.insert(visit);
		} else {
			throw new UnsupportedOperationException(
					"Visit update not supported");
		}
	}

	@Transactional(readOnly = true)
	public Pets findPetById(int id) {

		Pets pet = petsMapper.findById(id);

		Owners owner = this.ownersMapper.findById(pet.getOwnerId());
		owner.addPet(pet);

		pet.setType(typesMapper.findById(pet.getTypeId()));

		List<Visits> visits = this.visitsMapper.findByPetId(pet.getId());
		for (Visits visit : visits) {
			pet.addVisit(visit);
		}

		return pet;
	}

	public void savePet(Pets pet) throws DataAccessException {
		if (pet.isNew()) {
			petsMapper.insert(pet);
		} else {
			petsMapper.updateByPrimaryKey(pet);
		}
	}

	@Transactional(readOnly = true)
	// @Cacheable(value = "vets")
	public Collection<Vets> findVets() throws DataAccessException {
		return vetsMapper.findAll();
	}

}
