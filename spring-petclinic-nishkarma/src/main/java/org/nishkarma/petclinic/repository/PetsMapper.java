package org.nishkarma.petclinic.repository;

import java.util.Collection;

import org.nishkarma.petclinic.model.Pets;
import org.springframework.dao.DataAccessException;

public interface PetsMapper {

	/**
	 * Retrieve a <code>Pet</code> from the data store by id.
	 * 
	 * @param id
	 *            the id to search for
	 * @return the <code>Pet</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException
	 *             if not found
	 */
	Pets findById(int id) throws DataAccessException;

	/**
	 * Save a <code>Pet</code> to the data store, either inserting or updating
	 * it.
	 * 
	 * @param pet
	 *            the <code>Pet</code> to save
	 * @see BaseEntity#isNew
	 */
	void updateByPrimaryKey(Pets pet) throws DataAccessException;

	void insert(Pets pet) throws DataAccessException;

	void deleteByPrimaryKey(int id) throws DataAccessException;

	Collection<Pets> findByOwnerId(int id) throws DataAccessException;

}