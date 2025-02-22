package com.bamioezequiel.login.logica;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Rol implements Serializable {
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nameRol;
    private String description;
    
    @OneToMany (mappedBy="rol")
    private List<User> listUsers;

    public Rol() {
    }

    public Rol(int id, String nameRol, String description, List<User> listUsers) {
        this.id = id;
        this.nameRol = nameRol;
        this.description = description;
        this.listUsers = listUsers;
    }

    
    public Rol(int id, String nameRol, String description) {
        this.id = id;
        this.nameRol = nameRol;
        this.description = description;
    }

    public List<User> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<User> listUsers) {
        this.listUsers = listUsers;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameRol() {
        return nameRol;
    }

    public void setNameRol(String nameRol) {
        this.nameRol = nameRol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
