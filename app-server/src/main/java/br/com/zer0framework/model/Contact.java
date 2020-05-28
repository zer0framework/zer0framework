package br.com.zer0framework.model;

import java.util.Date;

public class Contact {

	private Integer id;
	private Integer userId;
	private String forename;
	private String surname;
	private String telefone;
	private Date created;

	public Contact() {
	}

	public Contact(Integer id, Integer userId, String forename, String surname, String telefone, Date created) {
		this.id = id;
		this.userId = userId;
		this.forename = forename;
		this.surname = surname;
		this.telefone = telefone;
		this.created = created;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

}
