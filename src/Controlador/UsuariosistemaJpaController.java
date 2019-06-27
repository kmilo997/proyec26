/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Roles;
import Modelo.Usuariosistema;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Carlos
 */
public class UsuariosistemaJpaController implements Serializable {

    public UsuariosistemaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuariosistema usuariosistema) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles idRol = usuariosistema.getIdRol();
            if (idRol != null) {
                idRol = em.getReference(idRol.getClass(), idRol.getIdRol());
                usuariosistema.setIdRol(idRol);
            }
            em.persist(usuariosistema);
            if (idRol != null) {
                idRol.getUsuariosistemaCollection().add(usuariosistema);
                idRol = em.merge(idRol);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuariosistema usuariosistema) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuariosistema persistentUsuariosistema = em.find(Usuariosistema.class, usuariosistema.getIdUsuarioSistema());
            Roles idRolOld = persistentUsuariosistema.getIdRol();
            Roles idRolNew = usuariosistema.getIdRol();
            if (idRolNew != null) {
                idRolNew = em.getReference(idRolNew.getClass(), idRolNew.getIdRol());
                usuariosistema.setIdRol(idRolNew);
            }
            usuariosistema = em.merge(usuariosistema);
            if (idRolOld != null && !idRolOld.equals(idRolNew)) {
                idRolOld.getUsuariosistemaCollection().remove(usuariosistema);
                idRolOld = em.merge(idRolOld);
            }
            if (idRolNew != null && !idRolNew.equals(idRolOld)) {
                idRolNew.getUsuariosistemaCollection().add(usuariosistema);
                idRolNew = em.merge(idRolNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = usuariosistema.getIdUsuarioSistema();
                if (findUsuariosistema(id) == null) {
                    throw new NonexistentEntityException("The usuariosistema with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuariosistema usuariosistema;
            try {
                usuariosistema = em.getReference(Usuariosistema.class, id);
                usuariosistema.getIdUsuarioSistema();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuariosistema with id " + id + " no longer exists.", enfe);
            }
            Roles idRol = usuariosistema.getIdRol();
            if (idRol != null) {
                idRol.getUsuariosistemaCollection().remove(usuariosistema);
                idRol = em.merge(idRol);
            }
            em.remove(usuariosistema);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuariosistema> findUsuariosistemaEntities() {
        return findUsuariosistemaEntities(true, -1, -1);
    }

    public List<Usuariosistema> findUsuariosistemaEntities(int maxResults, int firstResult) {
        return findUsuariosistemaEntities(false, maxResults, firstResult);
    }

    private List<Usuariosistema> findUsuariosistemaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuariosistema.class));
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

    public Usuariosistema findUsuariosistema(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuariosistema.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosistemaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuariosistema> rt = cq.from(Usuariosistema.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
