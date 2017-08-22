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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="equipment",catalog="equipmentsysment")
public class Equipment implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Line line;
	private Machine machine;
	private String eid;
	private Integer sit;
	private Set<MaintainRecord> maintainRecord = new HashSet<MaintainRecord>(0);

	// Constructors

	/** default constructor */
	public Equipment() {
	}

	/** full constructor */
	public Equipment(Line line, Machine machine, String eid, Integer sit) {
		this.line = line;
		this.machine = machine;
		this.eid = eid;
		this.sit = sit;
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

	@ManyToOne
	@JoinColumn(name="l_id")
	public Line getLine() {
		return this.line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	@ManyToOne
	@JoinColumn(name="m_id")
	public Machine getMachine() {
		return this.machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	@Column(name="eId")
	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	@Column(name="sit")
	public Integer getSit() {
		return this.sit;
	}

	public void setSit(Integer sit) {
		this.sit = sit;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="equipment")
	public Set<MaintainRecord> getMaintainRecord() {
		return this.maintainRecord;
	}

	public void setMaintainRecord(Set<MaintainRecord> maintainRecord) {
		this.maintainRecord = maintainRecord;
	}

}