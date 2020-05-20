package br.com.zer0framework.model;

import java.util.Date;

public class Person {
	private Integer id;
	private String name;
	private Date birthdate;
	private String job;
	private Integer managerPersonId;
	private Date created;

	public Person() {
	}

	public Person(Integer id, String name, Date birthdate, String job, Integer managerPersonId) {
		this.id = id;
		this.name = name;
		this.birthdate = birthdate;
		this.job = job;
		this.managerPersonId = managerPersonId;
	}

	public Person(Integer id, String name, Date birthdate, String job, Integer managerPersonId, Date created) {
		this.id = id;
		this.name = name;
		this.birthdate = birthdate;
		this.job = job;
		this.managerPersonId = managerPersonId;
		this.created = created;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Integer getManagerPersonId() {
		return managerPersonId;
	}

	public void setManagerPersonId(Integer managerPersonId) {
		this.managerPersonId = managerPersonId;
	}
}
