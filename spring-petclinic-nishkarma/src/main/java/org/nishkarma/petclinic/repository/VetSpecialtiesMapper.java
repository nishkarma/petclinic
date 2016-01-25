package org.nishkarma.petclinic.repository;

import java.util.List;

import org.nishkarma.petclinic.model.VetSpecialties;
import org.springframework.dao.DataAccessException;

public interface VetSpecialtiesMapper {

	List<VetSpecialties> findAll() throws DataAccessException;

}