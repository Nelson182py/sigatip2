/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.sigati.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author nelson182py
 */
@Entity
@Table(name = "tareas_diarias")
@NamedQueries({
    @NamedQuery(name = "TareasDiarias.findAll", query = "SELECT t FROM TareasDiarias t")
    , @NamedQuery(name = "TareasDiarias.findById", query = "SELECT t FROM TareasDiarias t WHERE t.id = :id")
    , @NamedQuery(name = "TareasDiarias.findByFecha", query = "SELECT t FROM TareasDiarias t WHERE t.fecha = :fecha")
    , @NamedQuery(name = "TareasDiarias.findByHoras", query = "SELECT t FROM TareasDiarias t WHERE t.horas = :horas")
    , @NamedQuery(name = "TareasDiarias.findByDescripcion", query = "SELECT t FROM TareasDiarias t WHERE t.descripcion = :descripcion")})
public class TareasDiarias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "horas")
    private Integer horas;
    @Size(max = 2147483647)
    @Column(name = "descripcion", length = 2147483647)
    private String descripcion;
    @JoinColumn(name = "id_tarea", referencedColumnName = "id")
    @ManyToOne
    private Tarea idTarea;

    public TareasDiarias() {
    }

    public TareasDiarias(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Tarea getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Tarea idTarea) {
        this.idTarea = idTarea;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof TareasDiarias)) {
            return false;
        }
        TareasDiarias other = (TareasDiarias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entidadessigati.TareasDiarias[ id=" + id + " ]";
    }
    
}
