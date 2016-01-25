package org.nishkarma.petclinic.repository;

import java.util.Collection;

import org.nishkarma.petclinic.model.Vets;
import org.springframework.dao.DataAccessException;

public interface VetsMapper {

	Collection<Vets> findAll() throws DataAccessException;

}