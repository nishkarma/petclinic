package org.nishkarma.petclinic.repository;

import java.util.List;

import org.nishkarma.petclinic.model.Visits;
import org.springframework.dao.DataAccessException;

public interface VisitsMapper {

	List<Visits> findByPetId(Integer petId) throws DataAccessException;

	void updateByPrimaryKey(Visits visit) throws DataAccessException;

	void insert(Visits visit) throws DataAccessException;
}