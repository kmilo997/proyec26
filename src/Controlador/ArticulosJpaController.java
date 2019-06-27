/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Controlador.exceptions.IllegalOrphanException;
import Controlador.exceptions.NonexistentEntityException;
import Modelo.Articulos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Productos;
import Modelo.Detalleventa;
import java.util.ArrayList;
import java.util.Collection;
import Modelo.Detallecompra;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Carlos
 */
public class ArticulosJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    
    public ArticulosJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("barrasPU");
    }
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Articulos articulos) {
        if (articulos.getDetalleventaCollection() == null) {
            articulos.setDetalleventaCollection(new ArrayList<Detalleventa>());
        }
        if (articulos.getDetallecompraCollection() == null) {
            articulos.setDetallecompraCollection(new ArrayList<Detallecompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productos idProducto = articulos.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                articulos.setIdProducto(idProducto);
            }
            Collection<Detalleventa> attachedDetalleventaCollection = new ArrayList<Detalleventa>();
            for (Detalleventa detalleventaCollectionDetalleventaToAttach : articulos.getDetalleventaCollection()) {
                detalleventaCollectionDetalleventaToAttach = em.getReference(detalleventaCollectionDetalleventaToAttach.getClass(), detalleventaCollectionDetalleventaToAttach.getIdDetalleVenta());
                attachedDetalleventaCollection.add(detalleventaCollectionDetalleventaToAttach);
            }
            articulos.setDetalleventaCollection(attachedDetalleventaCollection);
            Collection<Detallecompra> attachedDetallecompraCollection = new ArrayList<Detallecompra>();
            for (Detallecompra detallecompraCollectionDetallecompraToAttach : articulos.getDetallecompraCollection()) {
                detallecompraCollectionDetallecompraToAttach = em.getReference(detallecompraCollectionDetallecompraToAttach.getClass(), detallecompraCollectionDetallecompraToAttach.getIdDetalleCompra());
                attachedDetallecompraCollection.add(detallecompraCollectionDetallecompraToAttach);
            }
            articulos.setDetallecompraCollection(attachedDetallecompraCollection);
            em.persist(articulos);
            if (idProducto != null) {
                idProducto.getArticulosCollection().add(articulos);
                idProducto = em.merge(idProducto);
            }
            for (Detalleventa detalleventaCollectionDetalleventa : articulos.getDetalleventaCollection()) {
                Articulos oldIdArticuloOfDetalleventaCollectionDetalleventa = detalleventaCollectionDetalleventa.getIdArticulo();
                detalleventaCollectionDetalleventa.setIdArticulo(articulos);
                detalleventaCollectionDetalleventa = em.merge(detalleventaCollectionDetalleventa);
                if (oldIdArticuloOfDetalleventaCollectionDetalleventa != null) {
                    oldIdArticuloOfDetalleventaCollectionDetalleventa.getDetalleventaCollection().remove(detalleventaCollectionDetalleventa);
                    oldIdArticuloOfDetalleventaCollectionDetalleventa = em.merge(oldIdArticuloOfDetalleventaCollectionDetalleventa);
                }
            }
            for (Detallecompra detallecompraCollectionDetallecompra : articulos.getDetallecompraCollection()) {
                Articulos oldIdArticuloOfDetallecompraCollectionDetallecompra = detallecompraCollectionDetallecompra.getIdArticulo();
                detallecompraCollectionDetallecompra.setIdArticulo(articulos);
                detallecompraCollectionDetallecompra = em.merge(detallecompraCollectionDetallecompra);
                if (oldIdArticuloOfDetallecompraCollectionDetallecompra != null) {
                    oldIdArticuloOfDetallecompraCollectionDetallecompra.getDetallecompraCollection().remove(detallecompraCollectionDetallecompra);
                    oldIdArticuloOfDetallecompraCollectionDetallecompra = em.merge(oldIdArticuloOfDetallecompraCollectionDetallecompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Articulos articulos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulos persistentArticulos = em.find(Articulos.class, articulos.getIdArticulo());
            Productos idProductoOld = persistentArticulos.getIdProducto();
            Productos idProductoNew = articulos.getIdProducto();
            Collection<Detalleventa> detalleventaCollectionOld = persistentArticulos.getDetalleventaCollection();
            Collection<Detalleventa> detalleventaCollectionNew = articulos.getDetalleventaCollection();
            Collection<Detallecompra> detallecompraCollectionOld = persistentArticulos.getDetallecompraCollection();
            Collection<Detallecompra> detallecompraCollectionNew = articulos.getDetallecompraCollection();
            List<String> illegalOrphanMessages = null;
            for (Detalleventa detalleventaCollectionOldDetalleventa : detalleventaCollectionOld) {
                if (!detalleventaCollectionNew.contains(detalleventaCollectionOldDetalleventa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Detalleventa " + detalleventaCollectionOldDetalleventa + " since its idArticulo field is not nullable.");
                }
            }
            for (Detallecompra detallecompraCollectionOldDetallecompra : detallecompraCollectionOld) {
                if (!detallecompraCollectionNew.contains(detallecompraCollectionOldDetallecompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Detallecompra " + detallecompraCollectionOldDetallecompra + " since its idArticulo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                articulos.setIdProducto(idProductoNew);
            }
            Collection<Detalleventa> attachedDetalleventaCollectionNew = new ArrayList<Detalleventa>();
            for (Detalleventa detalleventaCollectionNewDetalleventaToAttach : detalleventaCollectionNew) {
                detalleventaCollectionNewDetalleventaToAttach = em.getReference(detalleventaCollectionNewDetalleventaToAttach.getClass(), detalleventaCollectionNewDetalleventaToAttach.getIdDetalleVenta());
                attachedDetalleventaCollectionNew.add(detalleventaCollectionNewDetalleventaToAttach);
            }
            detalleventaCollectionNew = attachedDetalleventaCollectionNew;
            articulos.setDetalleventaCollection(detalleventaCollectionNew);
            Collection<Detallecompra> attachedDetallecompraCollectionNew = new ArrayList<Detallecompra>();
            for (Detallecompra detallecompraCollectionNewDetallecompraToAttach : detallecompraCollectionNew) {
                detallecompraCollectionNewDetallecompraToAttach = em.getReference(detallecompraCollectionNewDetallecompraToAttach.getClass(), detallecompraCollectionNewDetallecompraToAttach.getIdDetalleCompra());
                attachedDetallecompraCollectionNew.add(detallecompraCollectionNewDetallecompraToAttach);
            }
            detallecompraCollectionNew = attachedDetallecompraCollectionNew;
            articulos.setDetallecompraCollection(detallecompraCollectionNew);
            articulos = em.merge(articulos);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getArticulosCollection().remove(articulos);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getArticulosCollection().add(articulos);
                idProductoNew = em.merge(idProductoNew);
            }
            for (Detalleventa detalleventaCollectionNewDetalleventa : detalleventaCollectionNew) {
                if (!detalleventaCollectionOld.contains(detalleventaCollectionNewDetalleventa)) {
                    Articulos oldIdArticuloOfDetalleventaCollectionNewDetalleventa = detalleventaCollectionNewDetalleventa.getIdArticulo();
                    detalleventaCollectionNewDetalleventa.setIdArticulo(articulos);
                    detalleventaCollectionNewDetalleventa = em.merge(detalleventaCollectionNewDetalleventa);
                    if (oldIdArticuloOfDetalleventaCollectionNewDetalleventa != null && !oldIdArticuloOfDetalleventaCollectionNewDetalleventa.equals(articulos)) {
                        oldIdArticuloOfDetalleventaCollectionNewDetalleventa.getDetalleventaCollection().remove(detalleventaCollectionNewDetalleventa);
                        oldIdArticuloOfDetalleventaCollectionNewDetalleventa = em.merge(oldIdArticuloOfDetalleventaCollectionNewDetalleventa);
                    }
                }
            }
            for (Detallecompra detallecompraCollectionNewDetallecompra : detallecompraCollectionNew) {
                if (!detallecompraCollectionOld.contains(detallecompraCollectionNewDetallecompra)) {
                    Articulos oldIdArticuloOfDetallecompraCollectionNewDetallecompra = detallecompraCollectionNewDetallecompra.getIdArticulo();
                    detallecompraCollectionNewDetallecompra.setIdArticulo(articulos);
                    detallecompraCollectionNewDetallecompra = em.merge(detallecompraCollectionNewDetallecompra);
                    if (oldIdArticuloOfDetallecompraCollectionNewDetallecompra != null && !oldIdArticuloOfDetallecompraCollectionNewDetallecompra.equals(articulos)) {
                        oldIdArticuloOfDetallecompraCollectionNewDetallecompra.getDetallecompraCollection().remove(detallecompraCollectionNewDetallecompra);
                        oldIdArticuloOfDetallecompraCollectionNewDetallecompra = em.merge(oldIdArticuloOfDetallecompraCollectionNewDetallecompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = articulos.getIdArticulo();
                if (findArticulos(id) == null) {
                    throw new NonexistentEntityException("The articulos with id " + id + " no longer exists.");
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
            Articulos articulos;
            try {
                articulos = em.getReference(Articulos.class, id);
                articulos.getIdArticulo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articulos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Detalleventa> detalleventaCollectionOrphanCheck = articulos.getDetalleventaCollection();
            for (Detalleventa detalleventaCollectionOrphanCheckDetalleventa : detalleventaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Articulos (" + articulos + ") cannot be destroyed since the Detalleventa " + detalleventaCollectionOrphanCheckDetalleventa + " in its detalleventaCollection field has a non-nullable idArticulo field.");
            }
            Collection<Detallecompra> detallecompraCollectionOrphanCheck = articulos.getDetallecompraCollection();
            for (Detallecompra detallecompraCollectionOrphanCheckDetallecompra : detallecompraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Articulos (" + articulos + ") cannot be destroyed since the Detallecompra " + detallecompraCollectionOrphanCheckDetallecompra + " in its detallecompraCollection field has a non-nullable idArticulo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Productos idProducto = articulos.getIdProducto();
            if (idProducto != null) {
                idProducto.getArticulosCollection().remove(articulos);
                idProducto = em.merge(idProducto);
            }
            em.remove(articulos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Articulos> findArticulosEntities() {
        return findArticulosEntities(true, -1, -1);
    }

    public List<Articulos> findArticulosEntities(int maxResults, int firstResult) {
        return findArticulosEntities(false, maxResults, firstResult);
    }

    private List<Articulos> findArticulosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Articulos.class));
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

    public Articulos findArticulos(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Articulos.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticulosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Articulos> rt = cq.from(Articulos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
