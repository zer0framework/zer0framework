package br.com.zer0framework.model;

import java.util.Date;

public class User {

    private Integer id;
    private String username;
    private String password;
    private Integer personId;
    private Date created;
    private String email;

    public User(Integer id, String username, String password, Integer personId, Date created, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.personId = personId;
        this.created = created;
        this.email = email;
    }

    public User() {
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getEmail() { return email;}

    public void setEmail(String email) {
        this.email = email;
    }
}