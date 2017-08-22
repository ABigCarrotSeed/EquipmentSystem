package com.hp.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="machine",catalog="equipmentsysment")
public class Machine implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String type;
	private String version;
	private Set<MaintainItems> maintainItemses = new HashSet<MaintainItems>(0);
	private Set<Equipment> equipments = new HashSet<Equipment>(0);

	// Constructors

	/** default constructor */
	public Machine() {
	}

	/** full constructor */
	public Machine(String name, String type, String version) {
		this.name = name;
		this.type = type;
		this.version = version;
	}

	// Property accessors

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="type")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="version")
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="machine")
	public Set<MaintainItems> getMaintainItemses() {
		return this.maintainItemses;
	}

	public void setMaintainItemses(Set<MaintainItems> maintainItemses) {
		this.maintainItemses = maintainItemses;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="machine")
	public Set<Equipment> getEquipments() {
		return this.equipments;
	}

	public void setEquipments(Set<Equipment> equipments) {
		this.equipments = equipments;
	}

}