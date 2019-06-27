/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import Modelo.Roles;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Usuariosistema;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Carlos
 */
public class RolesJpaController implements Serializable {

    public RolesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Roles roles) {
        if (roles.getUsuariosistemaCollection() == null) {
            roles.setUsuariosistemaCollection(new ArrayList<Usuariosistema>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Usuariosistema> attachedUsuariosistemaCollection = new ArrayList<Usuariosistema>();
            for (Usuariosistema usuariosistemaCollectionUsuariosistemaToAttach : roles.getUsuariosistemaCollection()) {
                usuariosistemaCollectionUsuariosistemaToAttach = em.getReference(usuariosistemaCollectionUsuariosistemaToAttach.getClass(), usuariosistemaCollectionUsuariosistemaToAttach.getIdUsuarioSistema());
                attachedUsuariosistemaCollection.add(usuariosistemaCollectionUsuariosistemaToAttach);
            }
            roles.setUsuariosistemaCollection(attachedUsuariosistemaCollection);
            em.persist(roles);
            for (Usuariosistema usuariosistemaCollectionUsuariosistema : roles.getUsuariosistemaCollection()) {
                Roles oldIdRolOfUsuariosistemaCollectionUsuariosistema = usuariosistemaCollectionUsuariosistema.getIdRol();
                usuariosistemaCollectionUsuariosistema.setIdRol(roles);
                usuariosistemaCollectionUsuariosistema = em.merge(usuariosistemaCollectionUsuariosistema);
                if (oldIdRolOfUsuariosistemaCollectionUsuariosistema != null) {
                    oldIdRolOfUsuariosistemaCollectionUsuariosistema.getUsuariosistemaCollection().remove(usuariosistemaCollectionUsuariosistema);
                    oldIdRolOfUsuariosistemaCollectionUsuariosistema = em.merge(oldIdRolOfUsuariosistemaCollectionUsuariosistema);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Roles roles) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles persistentRoles = em.find(Roles.class, roles.getIdRol());
            Collection<Usuariosistema> usuariosistemaCollectionOld = persistentRoles.getUsuariosistemaCollection();
            Collection<Usuariosistema> usuariosistemaCollectionNew = roles.getUsuariosistemaCollection();
            List<String> illegalOrphanMessages = null;
            for (Usuariosistema usuariosistemaCollectionOldUsuariosistema : usuariosistemaCollectionOld) {
                if (!usuariosistemaCollectionNew.contains(usuariosistemaCollectionOldUsuariosistema)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuariosistema " + usuariosistemaCollectionOldUsuariosistema + " since its idRol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Usuariosistema> attachedUsuariosistemaCollectionNew = new ArrayList<Usuariosistema>();
            for (Usuariosistema usuariosistemaCollectionNewUsuariosistemaToAttach : usuariosistemaCollectionNew) {
                usuariosistemaCollectionNewUsuariosistemaToAttach = em.getReference(usuariosistemaCollectionNewUsuariosistemaToAttach.getClass(), usuariosistemaCollectionNewUsuariosistemaToAttach.getIdUsuarioSistema());
                attachedUsuariosistemaCollectionNew.add(usuariosistemaCollectionNewUsuariosistemaToAttach);
            }
            usuariosistemaCollectionNew = attachedUsuariosistemaCollectionNew;
            roles.setUsuariosistemaCollection(usuariosistemaCollectionNew);
            roles = em.merge(roles);
            for (Usuariosistema usuariosistemaCollectionNewUsuariosistema : usuariosistemaCollectionNew) {
                if (!usuariosistemaCollectionOld.contains(usuariosistemaCollectionNewUsuariosistema)) {
                    Roles oldIdRolOfUsuariosistemaCollectionNewUsuariosistema = usuariosistemaCollectionNewUsuariosistema.getIdRol();
                    usuariosistemaCollectionNewUsuariosistema.setIdRol(roles);
                    usuariosistemaCollectionNewUsuariosistema = em.merge(usuariosistemaCollectionNewUsuariosistema);
                    if (oldIdRolOfUsuariosistemaCollectionNewUsuariosistema != null && !oldIdRolOfUsuariosistemaCollectionNewUsuariosistema.equals(roles)) {
                        oldIdRolOfUsuariosistemaCollectionNewUsuariosistema.getUsuariosistemaCollection().remove(usuariosistemaCollectionNewUsuariosistema);
                        oldIdRolOfUsuariosistemaCollectionNewUsuariosistema = em.merge(oldIdRolOfUsuariosistemaCollectionNewUsuariosistema);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = roles.getIdRol();
                if (findRoles(id) == null) {
                    throw new NonexistentEntityException("The roles with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles roles;
            try {
                roles = em.getReference(Roles.class, id);
                roles.getIdRol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roles with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Usuariosistema> usuariosistemaCollectionOrphanCheck = roles.getUsuariosistemaCollection();
            for (Usuariosistema usuariosistemaCollectionOrphanCheckUsuariosistema : usuariosistemaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Roles (" + roles + ") cannot be destroyed since the Usuariosistema " + usuariosistemaCollectionOrphanCheckUsuariosistema + " in its usuariosistemaCollection field has a non-nullable idRol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(roles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Roles> findRolesEntities() {
        return findRolesEntities(true, -1, -1);
    }

    public List<Roles> findRolesEntities(int maxResults, int firstResult) {
        return findRolesEntities(false, maxResults, firstResult);
    }

    private List<Roles> findRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Roles.class));
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

    public Roles findRoles(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Roles.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Roles> rt = cq.from(Roles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
