/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "articulos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulos.findAll", query = "SELECT a FROM Articulos a")
    , @NamedQuery(name = "Articulos.findByIdArticulo", query = "SELECT a FROM Articulos a WHERE a.idArticulo = :idArticulo")
    , @NamedQuery(name = "Articulos.findByNombreArticulo", query = "SELECT a FROM Articulos a WHERE a.nombreArticulo = :nombreArticulo")
    , @NamedQuery(name = "Articulos.findByDescripcionArticulo", query = "SELECT a FROM Articulos a WHERE a.descripcionArticulo = :descripcionArticulo")
    , @NamedQuery(name = "Articulos.findByValorCompra", query = "SELECT a FROM Articulos a WHERE a.valorCompra = :valorCompra")
    , @NamedQuery(name = "Articulos.findByValorVenta", query = "SELECT a FROM Articulos a WHERE a.valorVenta = :valorVenta")
    , @NamedQuery(name = "Articulos.findByStockArticulo", query = "SELECT a FROM Articulos a WHERE a.stockArticulo = :stockArticulo")
    , @NamedQuery(name = "Articulos.findByPorcentajeGanacia", query = "SELECT a FROM Articulos a WHERE a.porcentajeGanacia = :porcentajeGanacia")
    , @NamedQuery(name = "Articulos.findByCodigoBarras", query = "SELECT a FROM Articulos a WHERE a.codigoBarras = :codigoBarras")
    , @NamedQuery(name = "Articulos.findByCreatedAt", query = "SELECT a FROM Articulos a WHERE a.createdAt = :createdAt")
    , @NamedQuery(name = "Articulos.findByUpdatedAt", query = "SELECT a FROM Articulos a WHERE a.updatedAt = :updatedAt")
    , @NamedQuery(name = "Articulos.findByDeletedAt", query = "SELECT a FROM Articulos a WHERE a.deletedAt = :deletedAt")
    , @NamedQuery(name = "Articulos.findByEstado", query = "SELECT a FROM Articulos a WHERE a.estado = :estado")})
public class Articulos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idArticulo")
    private Long idArticulo;
    @Column(name = "nombreArticulo")
    private String nombreArticulo;
    @Column(name = "descripcionArticulo")
    private String descripcionArticulo;
    @Basic(optional = false)
    @Column(name = "valorCompra")
    private int valorCompra;
    @Basic(optional = false)
    @Column(name = "valorVenta")
    private int valorVenta;
    @Column(name = "stockArticulo")
    private Integer stockArticulo;
    @Column(name = "porcentajeGanacia")
    private Integer porcentajeGanacia;
    @Column(name = "codigoBarras")
    private String codigoBarras;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "idProducto", referencedColumnName = "idProducto")
    @ManyToOne(optional = false)
    private Productos idProducto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idArticulo")
    private Collection<Detalleventa> detalleventaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idArticulo")
    private Collection<Detallecompra> detallecompraCollection;

    public Articulos() {
    }

    public Articulos(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Articulos(Long idArticulo, int valorCompra, int valorVenta) {
        this.idArticulo = idArticulo;
        this.valorCompra = valorCompra;
        this.valorVenta = valorVenta;
    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public String getDescripcionArticulo() {
        return descripcionArticulo;
    }

    public void setDescripcionArticulo(String descripcionArticulo) {
        this.descripcionArticulo = descripcionArticulo;
    }

    public int getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(int valorCompra) {
        this.valorCompra = valorCompra;
    }

    public int getValorVenta() {
        return valorVenta;
    }

    public void setValorVenta(int valorVenta) {
        this.valorVenta = valorVenta;
    }

    public Integer getStockArticulo() {
        return stockArticulo;
    }

    public void setStockArticulo(Integer stockArticulo) {
        this.stockArticulo = stockArticulo;
    }

    public Integer getPorcentajeGanacia() {
        return porcentajeGanacia;
    }

    public void setPorcentajeGanacia(Integer porcentajeGanacia) {
        this.porcentajeGanacia = porcentajeGanacia;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Productos getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Productos idProducto) {
        this.idProducto = idProducto;
    }

    @XmlTransient
    public Collection<Detalleventa> getDetalleventaCollection() {
        return detalleventaCollection;
    }

    public void setDetalleventaCollection(Collection<Detalleventa> detalleventaCollection) {
        this.detalleventaCollection = detalleventaCollection;
    }

    @XmlTransient
    public Collection<Detallecompra> getDetallecompraCollection() {
        return detallecompraCollection;
    }

    public void setDetallecompraCollection(Collection<Detallecompra> detallecompraCollection) {
        this.detallecompraCollection = detallecompraCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArticulo != null ? idArticulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulos)) {
            return false;
        }
        Articulos other = (Articulos) object;
        if ((this.idArticulo == null && other.idArticulo != null) || (this.idArticulo != null && !this.idArticulo.equals(other.idArticulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Articulos[ idArticulo=" + idArticulo + " ]";
    }
    
}
