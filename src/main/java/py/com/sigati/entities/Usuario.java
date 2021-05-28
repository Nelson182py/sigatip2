/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.sigati.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nelson182py
 */
@Entity
@Table(name = "usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByCodigoHumano", query = "SELECT u FROM Usuario u WHERE u.codigoHumano = :codigoHumano")
    , @NamedQuery(name = "Usuario.findByFechaIngreso", query = "SELECT u FROM Usuario u WHERE u.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "Usuario.findByCelularCorporativo", query = "SELECT u FROM Usuario u WHERE u.celularCorporativo = :celularCorporativo")
    , @NamedQuery(name = "Usuario.findByCorreoCorporativo", query = "SELECT u FROM Usuario u WHERE u.correoCorporativo = :correoCorporativo")
    , @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT u FROM Usuario u WHERE u.usuario = :usuario")
    , @NamedQuery(name = "Usuario.findByContrasenha", query = "SELECT u FROM Usuario u WHERE u.contrasenha = :contrasenha")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_humano", nullable = false)
    private Integer codigoHumano;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_ingreso", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Size(max = 50)
    @Column(name = "celular_corporativo", length = 50)
    private String celularCorporativo;
    @Size(max = 50)
    @Column(name = "correo_corporativo", length = 50)
    private String correoCorporativo;
    @Size(max = 2147483647)
    @Column(name = "usuario", length = 2147483647)
    private String usuario;
    @Size(max = 2147483647)
    @Column(name = "contrasenha", length = 2147483647)
    private String contrasenha;
    @OneToMany(mappedBy = "idResponsable")
    private List<Proyecto> proyectoList;
    @OneToMany(mappedBy = "idReportador")
    private List<Incidente> incidenteList;
    @OneToMany(mappedBy = "idResponsable")
    private List<Tarea> tareaList;
    @JoinColumn(name = "id_area", referencedColumnName = "id")
    @ManyToOne
    private Area idArea;
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    @ManyToOne
    private Persona idPersona;
    @JoinColumn(name = "id_rol", referencedColumnName = "id")
    @ManyToOne
    private Rol idRol;

    public Usuario() {
    }

    public Usuario(Integer codigoHumano) {
        this.codigoHumano = codigoHumano;
    }

    public Usuario(Integer codigoHumano, Date fechaIngreso) {
        this.codigoHumano = codigoHumano;
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getCodigoHumano() {
        return codigoHumano;
    }

    public void setCodigoHumano(Integer codigoHumano) {
        this.codigoHumano = codigoHumano;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getCelularCorporativo() {
        return celularCorporativo;
    }

    public void setCelularCorporativo(String celularCorporativo) {
        this.celularCorporativo = celularCorporativo;
    }

    public String getCorreoCorporativo() {
        return correoCorporativo;
    }

    public void setCorreoCorporativo(String correoCorporativo) {
        this.correoCorporativo = correoCorporativo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public List<Proyecto> getProyectoList() {
        return proyectoList;
    }

    public void setProyectoList(List<Proyecto> proyectoList) {
        this.proyectoList = proyectoList;
    }

    public List<Incidente> getIncidenteList() {
        return incidenteList;
    }

    public void setIncidenteList(List<Incidente> incidenteList) {
        this.incidenteList = incidenteList;
    }

    public List<Tarea> getTareaList() {
        return tareaList;
    }

    public void setTareaList(List<Tarea> tareaList) {
        this.tareaList = tareaList;
    }

    public Area getIdArea() {
        return idArea;
    }

    public void setIdArea(Area idArea) {
        this.idArea = idArea;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoHumano != null ? codigoHumano.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.codigoHumano == null && other.codigoHumano != null) || (this.codigoHumano != null && !this.codigoHumano.equals(other.codigoHumano))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entidadessigati.Usuario[ codigoHumano=" + codigoHumano + " ]";
    }
    
}
