/**
 * Nishkarma Project
 */
package org.nishkarma.petclinic.model;

import org.nishkarma.common.paging.model.PagingParam;

public class FindOwnerParam extends PagingParam {
	
	private String lastName;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
