package com.bamioezequiel.login.persistences;

import com.bamioezequiel.login.logica.Rol;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.bamioezequiel.login.logica.User;
import com.bamioezequiel.login.persistences.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RolJpaController implements Serializable {

    public RolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public RolJpaController() {
        emf = Persistence.createEntityManagerFactory("loginPU");
    }

    public void create(Rol rol) {
        if (rol.getListUsers() == null) {
            rol.setListUsers(new ArrayList<User>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<User> attachedListaUsers = new ArrayList<User>();
            for (User listaUsersToAttach : rol.getListUsers()) {
                listaUsersToAttach = em.getReference(listaUsersToAttach.getClass(), listaUsersToAttach.getId());
                attachedListaUsers.add(listaUsersToAttach);
            }
            rol.setListUsers(attachedListaUsers);
            em.persist(rol);
            for (User listaUsers : rol.getListUsers()) {
                Rol oldUnRolOfListaUsers = listaUsers.getRol();
                listaUsers.setRol(rol);
                listaUsers = em.merge(listaUsers);
                if (oldUnRolOfListaUsers != null) {
                    rol.getListUsers().remove(listaUsers);
                    oldUnRolOfListaUsers = em.merge(oldUnRolOfListaUsers);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rol rol) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol persistentRol = em.find(Rol.class, rol.getId());
            List<User> listaUsersOld = rol.getListUsers();
            List<User> listaUsersNew = rol.getListUsers();
            List<User> attachedListaUsersNew = new ArrayList<User>();
            for (User listaUsersNewUserToAttach : listaUsersNew) {
                listaUsersNewUserToAttach = em.getReference(listaUsersNewUserToAttach.getClass(), listaUsersNewUserToAttach.getId());
                attachedListaUsersNew.add(listaUsersNewUserToAttach);
            }
            listaUsersNew = attachedListaUsersNew;
            rol.setListUsers(listaUsersNew);
            rol = em.merge(rol);
            for (User listaUsersOldUser : listaUsersOld) {
                if (!listaUsersNew.contains(listaUsersOldUser)) {
                    listaUsersOldUser.setRol(null);
                    listaUsersOldUser = em.merge(listaUsersOldUser);
                }
            }
            for (User listaUsersNewUser : listaUsersNew) {
                if (!listaUsersOld.contains(listaUsersNewUser)) {
                    Rol oldUnRolOfListaUsersNewUser = listaUsersNewUser.getRol();
                    listaUsersNewUser.setRol(rol);
                    listaUsersNewUser = em.merge(listaUsersNewUser);
                    if (oldUnRolOfListaUsersNewUser != null && !oldUnRolOfListaUsersNewUser.equals(rol)) {
                        rol.getListUsers().remove(listaUsersNewUser);
                        oldUnRolOfListaUsersNewUser = em.merge(oldUnRolOfListaUsersNewUser);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = rol.getId();
                if (findRol(id) == null) {
                    throw new NonexistentEntityException("The rol with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol rol;
            try {
                rol = em.getReference(Rol.class, id);
                rol.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rol with id " + id + " no longer exists.", enfe);
            }
            List<User> listUsers = rol.getListUsers();
            for (User user : listUsers) {
                user.setRol(null);
                user = em.merge(user);
            }
            em.remove(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rol> findRolEntities() {
        return findRolEntities(true, -1, -1);
    }

    public List<Rol> findRolEntities(int maxResults, int firstResult) {
        return findRolEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Rol findRol(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
