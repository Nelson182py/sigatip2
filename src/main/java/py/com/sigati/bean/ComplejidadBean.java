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
import py.com.sigati.ejb.ComplejidadEJB;
import py.com.sigati.entities.Complejidad;

/**
 *
 * @author juahni
 */
@ManagedBean
@SessionScoped
public class ComplejidadBean extends AbstractBean implements Serializable {

    private List<Complejidad> listaComplejidad = new ArrayList<>();
    private Complejidad complejidadSeleccionada;
    private boolean editando;

    @EJB
    private ComplejidadEJB complejidadEJB;

    @PostConstruct
    public void init() {
        complejidadSeleccionada = new Complejidad();
        listaComplejidad = complejidadEJB.findAll();
    }

    @Override
    public void resetearValores() {
        complejidadSeleccionada = new Complejidad();
        editando = false;
    }

    @Override
    public void inicializarListas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
            complejidadEJB.create(complejidadSeleccionada);
            infoMessage("Se guardó correctamente.");
            listaComplejidad = complejidadEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void antesActualizar() {
        editando = true;
        listaComplejidad = complejidadEJB.findAll();
    }

    @Override
    public void actualizar() {
        try {
            complejidadEJB.edit(complejidadSeleccionada);
            infoMessage("Se actualizó correctamente.");
            listaComplejidad = complejidadEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void eliminar() {
        try {
            complejidadEJB.remove(complejidadSeleccionada);
            infoMessage("Eliminado correctamente");
            listaComplejidad = complejidadEJB.findAll();
        } catch (Exception e) {
            errorMessage("No se pudo eliminar el registro");
        }

    }

    public void agregar() {
        resetearValores();
        listaComplejidad = complejidadEJB.findAll();
    }

    public List<Complejidad> getListaComplejidad() {
        return listaComplejidad;
    }

    public void setListaComplejidad(List<Complejidad> listaComplejidad) {
        this.listaComplejidad = listaComplejidad;
    }

   

    public Complejidad getComplejidadSeleccionado() {
        return complejidadSeleccionada;
    }

    public void setComplejidadSeleccionado(Complejidad complejidadSeleccionada) {
        this.complejidadSeleccionada = complejidadSeleccionada;
    }

   

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

}