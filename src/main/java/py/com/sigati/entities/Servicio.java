/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.sigati.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author nelson182py
 */
@Entity
@Table(name = "servicio")
@NamedQueries({
    @NamedQuery(name = "Servicio.findAll", query = "SELECT s FROM Servicio s")
    , @NamedQuery(name = "Servicio.findById", query = "SELECT s FROM Servicio s WHERE s.id = :id")
    , @NamedQuery(name = "Servicio.findByDescripcion", query = "SELECT s FROM Servicio s WHERE s.descripcion = :descripcion")})
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 100)
    @Column(name = "descripcion", length = 100)
    private String descripcion;
    @OneToMany(mappedBy = "idServicio")
    private List<Entregable> entregableList;
    @OneToMany(mappedBy = "idServicio")
    private List<Incidente> incidenteList;

    public Servicio() {
    }

    public Servicio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Entregable> getEntregableList() {
        return entregableList;
    }

    public void setEntregableList(List<Entregable> entregableList) {
        this.entregableList = entregableList;
    }

    public List<Incidente> getIncidenteList() {
        return incidenteList;
    }

    public void setIncidenteList(List<Incidente> incidenteList) {
        this.incidenteList = incidenteList;
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
        if (!(object instanceof Servicio)) {
            return false;
        }
        Servicio other = (Servicio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entidadessigati.Servicio[ id=" + id + " ]";
    }
    
}
