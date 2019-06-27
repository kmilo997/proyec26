/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "usuariosistema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuariosistema.findAll", query = "SELECT u FROM Usuariosistema u")
    , @NamedQuery(name = "Usuariosistema.findByIdUsuarioSistema", query = "SELECT u FROM Usuariosistema u WHERE u.idUsuarioSistema = :idUsuarioSistema")
    , @NamedQuery(name = "Usuariosistema.findByNombreUsuario", query = "SELECT u FROM Usuariosistema u WHERE u.nombreUsuario = :nombreUsuario")
    , @NamedQuery(name = "Usuariosistema.findByContrase\u00f1a", query = "SELECT u FROM Usuariosistema u WHERE u.contrase\u00f1a = :contrase\u00f1a")})
public class Usuariosistema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUsuarioSistema")
    private Long idUsuarioSistema;
    @Basic(optional = false)
    @Column(name = "nombreUsuario")
    private String nombreUsuario;
    @Basic(optional = false)
    @Column(name = "contrase\u00f1a")
    private String contraseña;
    @JoinColumn(name = "idRol", referencedColumnName = "idRol")
    @ManyToOne(optional = false)
    private Roles idRol;

    public Usuariosistema() {
    }

    public Usuariosistema(Long idUsuarioSistema) {
        this.idUsuarioSistema = idUsuarioSistema;
    }

    public Usuariosistema(Long idUsuarioSistema, String nombreUsuario, String contraseña) {
        this.idUsuarioSistema = idUsuarioSistema;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
    }

    public Long getIdUsuarioSistema() {
        return idUsuarioSistema;
    }

    public void setIdUsuarioSistema(Long idUsuarioSistema) {
        this.idUsuarioSistema = idUsuarioSistema;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Roles getIdRol() {
        return idRol;
    }

    public void setIdRol(Roles idRol) {
        this.idRol = idRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuarioSistema != null ? idUsuarioSistema.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuariosistema)) {
            return false;
        }
        Usuariosistema other = (Usuariosistema) object;
        if ((this.idUsuarioSistema == null && other.idUsuarioSistema != null) || (this.idUsuarioSistema != null && !this.idUsuarioSistema.equals(other.idUsuarioSistema))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Usuariosistema[ idUsuarioSistema=" + idUsuarioSistema + " ]";
    }
    
}
