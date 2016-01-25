package org.nishkarma.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

public class Pets {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column pets.id
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column pets.name
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	private String name;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column pets.birth_date
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private DateTime birthDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column pets.type_id
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	private Types type;
	private Integer typeId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column pets.owner_id
	 * 
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	private Integer ownerId;

	private Owners owner;
	private Set<Visits> visits;

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pets.id
	 * 
	 * @return the value of pets.id
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pets.id
	 * 
	 * @param id
	 *            the value for pets.id
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pets.name
	 * 
	 * @return the value of pets.name
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pets.name
	 * 
	 * @param name
	 *            the value for pets.name
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pets.birth_date
	 * 
	 * @return the value of pets.birth_date
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	public DateTime getBirthDate() {
		return birthDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pets.birth_date
	 * 
	 * @param birthDate
	 *            the value for pets.birth_date
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	public void setBirthDate(DateTime birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pets.owner_id
	 * 
	 * @return the value of pets.owner_id
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	public Integer getOwnerId() {
		return ownerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pets.owner_id
	 * 
	 * @param ownerId
	 *            the value for pets.owner_id
	 * @mbggenerated Thu Aug 01 15:43:11 KST 2013
	 */
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public boolean isNew() {
		return (this.id == null);
	}

	protected void setOwner(Owners owner) {
		this.owner = owner;
	}

	public Owners getOwner() {
		return this.owner;
	}

	protected void setVisitsInternal(Set<Visits> visits) {
		this.visits = visits;
	}

	protected Set<Visits> getVisitsInternal() {
		if (this.visits == null) {
			this.visits = new HashSet<Visits>();
		}
		return this.visits;
	}

	public List<Visits> getVisits() {
		List<Visits> sortedVisits = new ArrayList<Visits>(getVisitsInternal());
		PropertyComparator.sort(sortedVisits, new MutableSortDefinition(
				"visitDate", false, false));
		return Collections.unmodifiableList(sortedVisits);
	}

	public void addVisit(Visits visit) {
		getVisitsInternal().add(visit);
		visit.setPet(this);
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
}