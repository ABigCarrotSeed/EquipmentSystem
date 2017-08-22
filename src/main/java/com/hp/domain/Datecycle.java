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
@Table(name="datecycle",catalog="equipmentsysment")
public class Datecycle implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String type;
	private Set<MaintainItems> maintainItemses = new HashSet<MaintainItems>(0);

	// Constructors

	/** default constructor */
	public Datecycle() {
	}

	/** full constructor */
	public Datecycle(String type) {
		this.type = type;
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

	@Column(name="type")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="datecycle")
	public Set<MaintainItems> getMaintainItemses() {
		return this.maintainItemses;
	}

	public void setMaintainItemses(Set<MaintainItems> maintainItemses) {
		this.maintainItemses = maintainItemses;
	}

}