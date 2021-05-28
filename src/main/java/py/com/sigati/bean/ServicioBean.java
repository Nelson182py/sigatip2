/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.sigati.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.PrimeFaces;
import py.com.sigati.ejb.ServicioEJB;
import py.com.sigati.entities.Servicio;

/**
 *
 * @author juahni
 */
@ManagedBean
@SessionScoped
public class ServicioBean extends AbstractBean implements Serializable {

    private List<Servicio> listaServicio = new ArrayList<>();
    private Servicio servicioSeleccionado;
    private boolean editando;

    @EJB
    private ServicioEJB servicioEJB;

    @PostConstruct
    public void init() {
        servicioSeleccionado = new Servicio();
        listaServicio = servicioEJB.findAll();
    }

    @Override
    public void resetearValores() {
        servicioSeleccionado = new Servicio();
        editando = false;
    }

    @Override
    public void inicializarListas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
            servicioEJB.create(servicioSeleccionado);
            infoMessage("Se guardó correctamente.");
            listaServicio = servicioEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void antesActualizar() {
        editando = true;
        listaServicio = servicioEJB.findAll();
    }

    @Override
    public void actualizar() {
        try {
            servicioEJB.edit(servicioSeleccionado);
            infoMessage("Se actualizó correctamente.");
            listaServicio = servicioEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void eliminar() {
        try {
            servicioEJB.remove(servicioSeleccionado);
            infoMessage("Eliminado correctamente");
           listaServicio = servicioEJB.findAll();
        } catch (Exception e) {
            errorMessage("No se pudo eliminar el registro");
        }

    }

    public void agregar() {
        resetearValores();
        listaServicio = servicioEJB.findAll();
    }

    public List<Servicio> getListaServicio() {
        return listaServicio;
    }

    public void setListaServicio(List<Servicio> listaServicio) {
        this.listaServicio = listaServicio;
    }

   

    public Servicio getServicioSeleccionado() {
        return servicioSeleccionado;
    }

    public void setServicioSeleccionado(Servicio servicioSeleccionado) {
        this.servicioSeleccionado = servicioSeleccionado;
    }

   

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

}
