package org.nishkarma.petclinic.model;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class Visits {
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column visits.id
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	private Integer id;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column visits.pet_id
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	private Integer petId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column visits.visit_date
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private DateTime visitDate;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column visits.description
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	@NotEmpty
	private String description;

	private Pets pet;

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column visits.id
	 * 
	 * @return the value of visits.id
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column visits.id
	 * 
	 * @param id
	 *            the value for visits.id
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column visits.pet_id
	 * 
	 * @return the value of visits.pet_id
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	public Integer getPetId() {
		return petId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column visits.pet_id
	 * 
	 * @param petId
	 *            the value for visits.pet_id
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column visits.visit_date
	 * 
	 * @return the value of visits.visit_date
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	public DateTime getVisitDate() {
		return visitDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column visits.visit_date
	 * 
	 * @param visitDate
	 *            the value for visits.visit_date
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	public void setVisitDate(DateTime visitDate) {
		this.visitDate = visitDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column visits.description
	 * 
	 * @return the value of visits.description
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column visits.description
	 * 
	 * @param description
	 *            the value for visits.description
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isNew() {
		return (this.id == null);
	}

	public Pets getPet() {
		return pet;
	}

	public void setPet(Pets pet) {
		this.pet = pet;
	}
}