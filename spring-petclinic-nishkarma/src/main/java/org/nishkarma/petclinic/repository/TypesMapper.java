package org.nishkarma.petclinic.repository;

import java.util.Collection;

import org.nishkarma.petclinic.model.Types;
import org.springframework.dao.DataAccessException;

public interface TypesMapper {
	/**
	 * Retrieve all <code>PetType</code>s from the data store.
	 * 
	 * @return a <code>Collection</code> of <code>PetType</code>s
	 */
	Collection<Types> findPetTypes() throws DataAccessException;

	Types findById(int id) throws DataAccessException;
}