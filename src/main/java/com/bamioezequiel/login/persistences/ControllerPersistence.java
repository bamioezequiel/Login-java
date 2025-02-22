package com.bamioezequiel.login.persistences;

import com.bamioezequiel.login.logica.Rol;
import com.bamioezequiel.login.logica.User;
import com.bamioezequiel.login.persistences.exceptions.NonexistentEntityException;
import java.util.List;

public class ControllerPersistence {
    UserJpaController userJpa = new UserJpaController();
    RolJpaController rolJpa = new RolJpaController();
    
    public List<User> getUsers() {
        List<User> listUsers = userJpa.findUserEntities();
        return listUsers;
    }

    public void createUser(String username, String password, Rol rol) {
        User user = new User(username, password, rol);
        userJpa.create(user);
    }

    public List<Rol> getRoles() {
        return rolJpa.findRolEntities();
    }

    public void deleteUser(int id_user) throws NonexistentEntityException {
        userJpa.destroy(id_user);
    }

    public void updateUser(User user) throws Exception {
        userJpa.edit(user);
    }

}
