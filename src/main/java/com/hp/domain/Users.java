package com.hp.domain;

import java.io.Serializable;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "users",catalog="equipmentsysment")
public class Users implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String jobId;
	private String name;
	private String password;
	private String post;
	private Department department;
	private Set<MaintainRecord> maintainRecordsForEnId = new HashSet<MaintainRecord>(0);
	private Set<MaintainRecord> maintainRecordsForUId = new HashSet<MaintainRecord>(0);
	private Set<Privilege> privileges = new HashSet<Privilege>(0); 
	
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Users(String jobId, String name, String password, String post) {
		super();
		this.jobId = jobId;
		this.name = name;
		this.password = password;
		this.post = post;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="jobId")
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="post")
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)//开启懒加载
	@JoinColumn(name="d_id")
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="usersByEnId")
	public Set<MaintainRecord> getMaintainRecordsForEnId() {
		return maintainRecordsForEnId;
	}
	public void setMaintainRecordsForEnId(Set<MaintainRecord> maintainRecordsForEnId) {
		this.maintainRecordsForEnId = maintainRecordsForEnId;
	}
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="usersByUId")
	public Set<MaintainRecord> getMaintainRecordsForUId() {
		return maintainRecordsForUId;
	}
	public void setMaintainRecordsForUId(Set<MaintainRecord> maintainRecordsForUId) {
		this.maintainRecordsForUId = maintainRecordsForUId;
	}
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinTable(name="users_privilege",
		joinColumns={@JoinColumn(name="usersId",referencedColumnName="id")},
		inverseJoinColumns={@JoinColumn(name="privilegeId",referencedColumnName="id")}
		)
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	
	
}
