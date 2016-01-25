package org.nishkarma.petclinic.repository;

import java.util.Collection;

import org.nishkarma.petclinic.model.FindOwnerParam;
import org.nishkarma.petclinic.model.Owners;
import org.springframework.dao.DataAccessException;

public interface OwnersMapper {

	/**
	 * Retrieve <code>Owner</code>s from the data store by last name, returning
	 * all owners whose last name <i>starts</i> with the given name.
	 * 
	 * @param findOwnerParam
	 *            Value to search for
	 * @return a <code>Collection</code> of matching <code>Owner</code>s (or an
	 *         empty <code>Collection</code> if none found)
	 */
	Collection<Owners> findByLastName(FindOwnerParam findOwnerParam)
			throws DataAccessException;

	long findByLastNameCount(FindOwnerParam findOwnerParam)
			throws DataAccessException;
	
	/**
	 * Retrieve an <code>Owner</code> from the data store by id.
	 * 
	 * @param id
	 *            the id to search for
	 * @return the <code>Owner</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException
	 *             if not found
	 */
	Owners findById(int id) throws DataAccessException;

	/**
	 * Save an <code>Owner</code> to the data store, either inserting or
	 * updating it.
	 * 
	 * @param owner
	 *            the <code>Owner</code> to save
	 * @see BaseEntity#isNew
	 */
	void updateByPrimaryKey(Owners owner) throws DataAccessException;

	void insert(Owners owner) throws DataAccessException;

}