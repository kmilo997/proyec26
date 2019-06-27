/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import Modelo.Compras;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Detallecompra;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Carlos
 */
public class ComprasJpaController implements Serializable {

    public ComprasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compras compras) {
        if (compras.getDetallecompraCollection() == null) {
            compras.setDetallecompraCollection(new ArrayList<Detallecompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Detallecompra> attachedDetallecompraCollection = new ArrayList<Detallecompra>();
            for (Detallecompra detallecompraCollectionDetallecompraToAttach : compras.getDetallecompraCollection()) {
                detallecompraCollectionDetallecompraToAttach = em.getReference(detallecompraCollectionDetallecompraToAttach.getClass(), detallecompraCollectionDetallecompraToAttach.getIdDetalleCompra());
                attachedDetallecompraCollection.add(detallecompraCollectionDetallecompraToAttach);
            }
            compras.setDetallecompraCollection(attachedDetallecompraCollection);
            em.persist(compras);
            for (Detallecompra detallecompraCollectionDetallecompra : compras.getDetallecompraCollection()) {
                Compras oldIdCompraOfDetallecompraCollectionDetallecompra = detallecompraCollectionDetallecompra.getIdCompra();
                detallecompraCollectionDetallecompra.setIdCompra(compras);
                detallecompraCollectionDetallecompra = em.merge(detallecompraCollectionDetallecompra);
                if (oldIdCompraOfDetallecompraCollectionDetallecompra != null) {
                    oldIdCompraOfDetallecompraCollectionDetallecompra.getDetallecompraCollection().remove(detallecompraCollectionDetallecompra);
                    oldIdCompraOfDetallecompraCollectionDetallecompra = em.merge(oldIdCompraOfDetallecompraCollectionDetallecompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Compras compras) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compras persistentCompras = em.find(Compras.class, compras.getIdCompra());
            Collection<Detallecompra> detallecompraCollectionOld = persistentCompras.getDetallecompraCollection();
            Collection<Detallecompra> detallecompraCollectionNew = compras.getDetallecompraCollection();
            List<String> illegalOrphanMessages = null;
            for (Detallecompra detallecompraCollectionOldDetallecompra : detallecompraCollectionOld) {
                if (!detallecompraCollectionNew.contains(detallecompraCollectionOldDetallecompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Detallecompra " + detallecompraCollectionOldDetallecompra + " since its idCompra field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Detallecompra> attachedDetallecompraCollectionNew = new ArrayList<Detallecompra>();
            for (Detallecompra detallecompraCollectionNewDetallecompraToAttach : detallecompraCollectionNew) {
                detallecompraCollectionNewDetallecompraToAttach = em.getReference(detallecompraCollectionNewDetallecompraToAttach.getClass(), detallecompraCollectionNewDetallecompraToAttach.getIdDetalleCompra());
                attachedDetallecompraCollectionNew.add(detallecompraCollectionNewDetallecompraToAttach);
            }
            detallecompraCollectionNew = attachedDetallecompraCollectionNew;
            compras.setDetallecompraCollection(detallecompraCollectionNew);
            compras = em.merge(compras);
            for (Detallecompra detallecompraCollectionNewDetallecompra : detallecompraCollectionNew) {
                if (!detallecompraCollectionOld.contains(detallecompraCollectionNewDetallecompra)) {
                    Compras oldIdCompraOfDetallecompraCollectionNewDetallecompra = detallecompraCollectionNewDetallecompra.getIdCompra();
                    detallecompraCollectionNewDetallecompra.setIdCompra(compras);
                    detallecompraCollectionNewDetallecompra = em.merge(detallecompraCollectionNewDetallecompra);
                    if (oldIdCompraOfDetallecompraCollectionNewDetallecompra != null && !oldIdCompraOfDetallecompraCollectionNewDetallecompra.equals(compras)) {
                        oldIdCompraOfDetallecompraCollectionNewDetallecompra.getDetallecompraCollection().remove(detallecompraCollectionNewDetallecompra);
                        oldIdCompraOfDetallecompraCollectionNewDetallecompra = em.merge(oldIdCompraOfDetallecompraCollectionNewDetallecompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = compras.getIdCompra();
                if (findCompras(id) == null) {
                    throw new NonexistentEntityException("The compras with id " + id + " no longer exists.");
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
            Compras compras;
            try {
                compras = em.getReference(Compras.class, id);
                compras.getIdCompra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compras with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Detallecompra> detallecompraCollectionOrphanCheck = compras.getDetallecompraCollection();
            for (Detallecompra detallecompraCollectionOrphanCheckDetallecompra : detallecompraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Compras (" + compras + ") cannot be destroyed since the Detallecompra " + detallecompraCollectionOrphanCheckDetallecompra + " in its detallecompraCollection field has a non-nullable idCompra field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(compras);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Compras> findComprasEntities() {
        return findComprasEntities(true, -1, -1);
    }

    public List<Compras> findComprasEntities(int maxResults, int firstResult) {
        return findComprasEntities(false, maxResults, firstResult);
    }

    private List<Compras> findComprasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compras.class));
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

    public Compras findCompras(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compras.class, id);
        } finally {
            em.close();
        }
    }

    public int getComprasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compras> rt = cq.from(Compras.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
