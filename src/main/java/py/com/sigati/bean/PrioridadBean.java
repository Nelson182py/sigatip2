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
import py.com.sigati.ejb.PrioridadEJB;
import py.com.sigati.entities.Prioridad;

/**
 *
 * @author Juanhi
 */
@ManagedBean
@SessionScoped
public class PrioridadBean extends AbstractBean implements Serializable {

    private List<Prioridad> listaPrioridad = new ArrayList<>();
    private Prioridad prioridadSeleccionado;
    private boolean editando;

    @EJB
    private PrioridadEJB prioridadEJB;

    @PostConstruct
    public void init() {
        prioridadSeleccionado = new Prioridad();
        listaPrioridad = prioridadEJB.findAll();
    }

    @Override
    public void resetearValores() {
        prioridadSeleccionado = new Prioridad();
        editando = false;
    }

    @Override
    public void inicializarListas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
            prioridadEJB.create(prioridadSeleccionado);
            infoMessage("Se guardó correctamente.");
            listaPrioridad = prioridadEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void antesActualizar() {
        editando = true;
        listaPrioridad = prioridadEJB.findAll();
    }

    @Override
    public void actualizar() {
        try {
            prioridadEJB.edit(prioridadSeleccionado);
            infoMessage("Se actualizó correctamente.");
            listaPrioridad = prioridadEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void eliminar() {
        try {
            prioridadEJB.remove(prioridadSeleccionado);
            infoMessage("Eliminado correctamente");
            listaPrioridad = prioridadEJB.findAll();
        } catch (Exception e) {
            errorMessage("No se pudo eliminar el registro");
        }

    }

    public void agregar() {
        resetearValores();
        listaPrioridad = prioridadEJB.findAll();
    }

    public List<Prioridad> getListaPrioridad() {
        return listaPrioridad;
    }

    public void setListaPrioridad(List<Prioridad> listaPrioridad) {
        this.listaPrioridad = listaPrioridad;
    }

   

    public Prioridad getPrioridadSeleccionado() {
        return prioridadSeleccionado;
    }

    public void setPrioridadSeleccionado(Prioridad prioridadSeleccionado) {
        this.prioridadSeleccionado = prioridadSeleccionado;
    }

   

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

}
