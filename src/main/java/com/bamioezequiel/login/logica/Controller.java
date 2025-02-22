package com.bamioezequiel.login.logica;

import com.bamioezequiel.login.persistences.ControllerPersistence;
import com.bamioezequiel.login.persistences.exceptions.NonexistentEntityException;
import java.util.List;

public class Controller {
    ControllerPersistence controllerPersistence;

    public Controller() {
        this.controllerPersistence = new ControllerPersistence();
    }
    
    public List<User> getUsers() {
        return controllerPersistence.getUsers();
    }
    
    public User login(String username, String password) {
         
        User user = null;
        List<User> listUsers = controllerPersistence.getUsers();
        
        for(User userBD : listUsers) {
            if(userBD.getUsername().equals(username)) {
                if(userBD.getPassword().equals(password)) {
                    user = userBD;
                    return user;
                } else {
                    user = null;
                    return user;
                }
            } else {
                user = null;
                //return user;
            }
        }
        return user;
    }

    public List<Rol> getRoles() {
       return controllerPersistence.getRoles();
    }

    public void createUser(String username, String password, String rol) {
        List<Rol> roles = getRoles(); 
        
        for(Rol dbRol : roles) {
           if(dbRol.getNameRol().equals(rol)) {
                controllerPersistence.createUser(username, password, dbRol);
           } 
        }
    }

    public void deleteUser(int id_user) throws NonexistentEntityException {
        controllerPersistence.deleteUser(id_user);
    }

    public User getUser(int id_user_update) {
        User findUser = null;
        List<User> users = getUsers();
        for(User u : users) {
            if(u.getId() == id_user_update) {
                findUser = u;
            }
        }
        
        return findUser;
    }

    public void updateUser(int id, String username, String password, String rol) throws Exception  {
        List<Rol> roles = getRoles(); 
        
        for(Rol dbRol : roles) {
           if(dbRol.getNameRol().equals(rol)) {
               User user = new User(id, username, password, dbRol);
               controllerPersistence.updateUser(user);
           } 
        }
    }

    
}
