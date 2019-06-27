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
import Modelo.Articulos;
import Modelo.Detalleventa;
import Modelo.Ventas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Carlos
 */
public class DetalleventaJpaController implements Serializable {

    public DetalleventaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Detalleventa detalleventa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulos idArticulo = detalleventa.getIdArticulo();
            if (idArticulo != null) {
                idArticulo = em.getReference(idArticulo.getClass(), idArticulo.getIdArticulo());
                detalleventa.setIdArticulo(idArticulo);
            }
            Ventas idVentas = detalleventa.getIdVentas();
            if (idVentas != null) {
                idVentas = em.getReference(idVentas.getClass(), idVentas.getIdVentas());
                detalleventa.setIdVentas(idVentas);
            }
            em.persist(detalleventa);
            if (idArticulo != null) {
                idArticulo.getDetalleventaCollection().add(detalleventa);
                idArticulo = em.merge(idArticulo);
            }
            if (idVentas != null) {
                idVentas.getDetalleventaCollection().add(detalleventa);
                idVentas = em.merge(idVentas);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Detalleventa detalleventa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detalleventa persistentDetalleventa = em.find(Detalleventa.class, detalleventa.getIdDetalleVenta());
            Articulos idArticuloOld = persistentDetalleventa.getIdArticulo();
            Articulos idArticuloNew = detalleventa.getIdArticulo();
            Ventas idVentasOld = persistentDetalleventa.getIdVentas();
            Ventas idVentasNew = detalleventa.getIdVentas();
            if (idArticuloNew != null) {
                idArticuloNew = em.getReference(idArticuloNew.getClass(), idArticuloNew.getIdArticulo());
                detalleventa.setIdArticulo(idArticuloNew);
            }
            if (idVentasNew != null) {
                idVentasNew = em.getReference(idVentasNew.getClass(), idVentasNew.getIdVentas());
                detalleventa.setIdVentas(idVentasNew);
            }
            detalleventa = em.merge(detalleventa);
            if (idArticuloOld != null && !idArticuloOld.equals(idArticuloNew)) {
                idArticuloOld.getDetalleventaCollection().remove(detalleventa);
                idArticuloOld = em.merge(idArticuloOld);
            }
            if (idArticuloNew != null && !idArticuloNew.equals(idArticuloOld)) {
                idArticuloNew.getDetalleventaCollection().add(detalleventa);
                idArticuloNew = em.merge(idArticuloNew);
            }
            if (idVentasOld != null && !idVentasOld.equals(idVentasNew)) {
                idVentasOld.getDetalleventaCollection().remove(detalleventa);
                idVentasOld = em.merge(idVentasOld);
            }
            if (idVentasNew != null && !idVentasNew.equals(idVentasOld)) {
                idVentasNew.getDetalleventaCollection().add(detalleventa);
                idVentasNew = em.merge(idVentasNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = detalleventa.getIdDetalleVenta();
                if (findDetalleventa(id) == null) {
                    throw new NonexistentEntityException("The detalleventa with id " + id + " no longer exists.");
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
            Detalleventa detalleventa;
            try {
                detalleventa = em.getReference(Detalleventa.class, id);
                detalleventa.getIdDetalleVenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleventa with id " + id + " no longer exists.", enfe);
            }
            Articulos idArticulo = detalleventa.getIdArticulo();
            if (idArticulo != null) {
                idArticulo.getDetalleventaCollection().remove(detalleventa);
                idArticulo = em.merge(idArticulo);
            }
            Ventas idVentas = detalleventa.getIdVentas();
            if (idVentas != null) {
                idVentas.getDetalleventaCollection().remove(detalleventa);
                idVentas = em.merge(idVentas);
            }
            em.remove(detalleventa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Detalleventa> findDetalleventaEntities() {
        return findDetalleventaEntities(true, -1, -1);
    }

    public List<Detalleventa> findDetalleventaEntities(int maxResults, int firstResult) {
        return findDetalleventaEntities(false, maxResults, firstResult);
    }

    private List<Detalleventa> findDetalleventaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Detalleventa.class));
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

    public Detalleventa findDetalleventa(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Detalleventa.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleventaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Detalleventa> rt = cq.from(Detalleventa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
