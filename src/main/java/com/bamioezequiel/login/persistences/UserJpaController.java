package com.bamioezequiel.login.persistences;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.bamioezequiel.login.logica.Rol;
import com.bamioezequiel.login.logica.User;
import com.bamioezequiel.login.persistences.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public UserJpaController() {
        emf = Persistence.createEntityManagerFactory("loginPU");
    }
    public void create(User User) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol rol = User.getRol();
            if (rol != null) {
                rol = em.getReference(rol.getClass(), rol.getId());
                User.setRol(rol);
            }
            em.persist(User);
            if (rol != null) {
                rol.getListUsers().add(User);
                rol = em.merge(rol);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User User) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, User.getId());
            Rol rolOld = persistentUser.getRol();
            Rol rolNew = User.getRol();
            if (rolNew != null) {
                rolNew = em.getReference(rolNew.getClass(), rolNew.getId());
                User.setRol(rolNew);
            }
            User = em.merge(User);
            if (rolOld != null && !rolOld.equals(rolNew)) {
                rolOld.getListUsers().remove(User);
                rolOld = em.merge(rolOld);
            }
            if (rolNew != null && !rolNew.equals(rolOld)) {
                rolNew.getListUsers().add(User);
                rolNew = em.merge(rolNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = User.getId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The User with id " + id + " no longer exists.");
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
            User User;
            try {
                User = em.getReference(User.class, id);
                User.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The User with id " + id + " no longer exists.", enfe);
            }
            Rol rol = User.getRol();
            if (rol != null) {
                rol.getListUsers().remove(User);
                rol = em.merge(rol);
            }
            em.remove(User);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}