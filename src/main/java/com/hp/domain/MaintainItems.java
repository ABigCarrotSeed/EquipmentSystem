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
@Table(name="maintainItems",catalog="equipmentsysment")
public class MaintainItems implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Datecycle datecycle;
	private Machine machine;
	private String project;
	private String norm;
	private Integer selection;
	private Set<MaintainRecord> maintainRecord = new HashSet<MaintainRecord>(0);

	// Constructors

	/** default constructor */
	public MaintainItems() {
	}

	/** full constructor */
	public MaintainItems(Datecycle datecycle, Machine machine, String project,
			String norm, Integer selection) {
		this.datecycle = datecycle;
		this.machine = machine;
		this.project = project;
		this.norm = norm;
		this.selection = selection;
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
	@JoinColumn(name="d_id")
	public Datecycle getDatecycle() {
		return this.datecycle;
	}

	public void setDatecycle(Datecycle datecycle) {
		this.datecycle = datecycle;
	}

	@ManyToOne
	@JoinColumn(name="m_id")
	public Machine getMachine() {
		return this.machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	@Column(name="project")
	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	@Column(name="norm")
	public String getNorm() {
		return this.norm;
	}

	public void setNorm(String norm) {
		this.norm = norm;
	}

	@Column(name="selection")
	public Integer getSelection() {
		return this.selection;
	}

	public void setSelection(Integer selection) {
		this.selection = selection;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="maintainItems")
	public Set<MaintainRecord> getMaintainRecord() {
		return this.maintainRecord;
	}

	public void setMaintainRecord(Set<MaintainRecord> maintainRecord) {
		this.maintainRecord = maintainRecord;
	}

}