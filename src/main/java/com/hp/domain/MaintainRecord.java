package com.hp.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="maintainRecord",catalog="equipmentsysment")
public class MaintainRecord implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer id;
	private Users usersByEnId;
	private Equipment equipment;
	private Users usersByUId;
	private MaintainItems maintainItems;
	private Timestamp maintaintime;
	private String firstResult;
	private String secResult;
	private String tip;

	// Constructors

	/** default constructor */
	public MaintainRecord() {
	}

	/** minimal constructor */
	public MaintainRecord(Timestamp maintaintime) {
		this.maintaintime = maintaintime;
	}

	/** full constructor */
	public MaintainRecord(Users usersByEnId, Equipment equipment,
			Users usersByUId, MaintainItems maintainItems,
			Timestamp maintaintime, String firstResult, String secResult,
			String tip) {
		this.usersByEnId = usersByEnId;
		this.equipment = equipment;
		this.usersByUId = usersByUId;
		this.maintainItems = maintainItems;
		this.maintaintime = maintaintime;
		this.firstResult = firstResult;
		this.secResult = secResult;
		this.tip = tip;
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
	@JoinColumn(name="en_id")
	public Users getUsersByEnId() {
		return this.usersByEnId;
	}

	public void setUsersByEnId(Users usersByEnId) {
		this.usersByEnId = usersByEnId;
	}

	@ManyToOne
	@JoinColumn(name="e_id")
	public Equipment getEquipment() {
		return this.equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	
	@ManyToOne
	@JoinColumn(name="u_id")
	public Users getUsersByUId() {
		return this.usersByUId;
	}

	public void setUsersByUId(Users usersByUId) {
		this.usersByUId = usersByUId;
	}

	@ManyToOne
	@JoinColumn(name="m_Id")
	public MaintainItems getMaintainItems() {
		return this.maintainItems;
	}

	public void setMaintainItems(MaintainItems maintainItems) {
		this.maintainItems = maintainItems;
	}
	
	@Column(name="maintaintime")
	public Timestamp getMaintaintime() {
		return this.maintaintime;
	}

	public void setMaintaintime(Timestamp maintaintime) {
		this.maintaintime = maintaintime;
	}

	@Column(name="firstResult")
	public String getFirstResult() {
		return this.firstResult;
	}

	public void setFirstResult(String firstResult) {
		this.firstResult = firstResult;
	}

	@Column(name="secResult")
	public String getSecResult() {
		return this.secResult;
	}

	public void setSecResult(String secResult) {
		this.secResult = secResult;
	}

	@Column(name="tip")
	public String getTip() {
		return this.tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

}