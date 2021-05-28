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
@Table(name = "proyecto")
@NamedQueries({
    @NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p")
    , @NamedQuery(name = "Proyecto.findById", query = "SELECT p FROM Proyecto p WHERE p.id = :id")
    , @NamedQuery(name = "Proyecto.findByNombre", query = "SELECT p FROM Proyecto p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Proyecto.findByDescripcion", query = "SELECT p FROM Proyecto p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Proyecto.findByFechaInicio", query = "SELECT p FROM Proyecto p WHERE p.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Proyecto.findByFechaInicioEstimado", query = "SELECT p FROM Proyecto p WHERE p.fechaInicioEstimado = :fechaInicioEstimado")
    , @NamedQuery(name = "Proyecto.findByFechaFinEstimado", query = "SELECT p FROM Proyecto p WHERE p.fechaFinEstimado = :fechaFinEstimado")})
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 100)
    @Column(name = "nombre", length = 100)
    private String nombre;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio_estimado", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicioEstimado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_fin_estimado", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaFinEstimado;
    @OneToMany(mappedBy = "idProyecto")
    private List<Entregable> entregableList;
    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    @ManyToOne
    private Estado idEstado;
    @JoinColumn(name = "id_segmento", referencedColumnName = "id")
    @ManyToOne
    private Segmento idSegmento;
    @JoinColumn(name = "id_responsable", referencedColumnName = "codigo_humano")
    @ManyToOne
    private Usuario idResponsable;

    public Proyecto() {
    }

    public Proyecto(Integer id) {
        this.id = id;
    }

    public Proyecto(Integer id, Date fechaInicio, Date fechaInicioEstimado, Date fechaFinEstimado) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaInicioEstimado = fechaInicioEstimado;
        this.fechaFinEstimado = fechaFinEstimado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaInicioEstimado() {
        return fechaInicioEstimado;
    }

    public void setFechaInicioEstimado(Date fechaInicioEstimado) {
        this.fechaInicioEstimado = fechaInicioEstimado;
    }

    public Date getFechaFinEstimado() {
        return fechaFinEstimado;
    }

    public void setFechaFinEstimado(Date fechaFinEstimado) {
        this.fechaFinEstimado = fechaFinEstimado;
    }

    public List<Entregable> getEntregableList() {
        return entregableList;
    }

    public void setEntregableList(List<Entregable> entregableList) {
        this.entregableList = entregableList;
    }

    public Estado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Estado idEstado) {
        this.idEstado = idEstado;
    }

    public Segmento getIdSegmento() {
        return idSegmento;
    }

    public void setIdSegmento(Segmento idSegmento) {
        this.idSegmento = idSegmento;
    }

    public Usuario getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Usuario idResponsable) {
        this.idResponsable = idResponsable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entidadessigati.Proyecto[ id=" + id + " ]";
    }
    
}
