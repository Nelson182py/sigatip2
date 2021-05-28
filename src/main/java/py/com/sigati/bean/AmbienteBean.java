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
import py.com.sigati.ejb.AmbienteEJB;
import py.com.sigati.entities.Ambiente;

/**
 *
 * @author juahni
 */
@ManagedBean
@SessionScoped
public class AmbienteBean extends AbstractBean implements Serializable {

    private List<Ambiente> listaAmbiente = new ArrayList<>();
    private Ambiente ambienteSeleccionado;
    private boolean editando;

    @EJB
    private AmbienteEJB ambienteEJB;

    @PostConstruct
    public void init() {
        ambienteSeleccionado = new Ambiente();
        listaAmbiente = ambienteEJB.findAll();
    }

    @Override
    public void resetearValores() {
        ambienteSeleccionado = new Ambiente();
        editando = false;
    }

    @Override
    public void inicializarListas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
            ambienteEJB.create(ambienteSeleccionado);
            infoMessage("Se guardó correctamente.");
            listaAmbiente = ambienteEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void antesActualizar() {
        editando = true;
        listaAmbiente = ambienteEJB.findAll();
    }

    @Override
    public void actualizar() {
        try {
            ambienteEJB.edit(ambienteSeleccionado);
            infoMessage("Se actualizó correctamente.");
            listaAmbiente = ambienteEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void eliminar() {
        try {
            ambienteEJB.remove(ambienteSeleccionado);
            infoMessage("Eliminado correctamente");
           listaAmbiente = ambienteEJB.findAll();
        } catch (Exception e) {
            errorMessage("No se pudo eliminar el registro");
        }

    }

    public void agregar() {
        resetearValores();
        listaAmbiente = ambienteEJB.findAll();
    }

    public List<Ambiente> getListaAmbiente() {
        return listaAmbiente;
    }

    public void setListaAmbiente(List<Ambiente> listaAmbiente) {
        this.listaAmbiente = listaAmbiente;
    }

   

    public Ambiente getAmbienteSeleccionado() {
        return ambienteSeleccionado;
    }

    public void setAmbienteSeleccionado(Ambiente ambienteSeleccionado) {
        this.ambienteSeleccionado = ambienteSeleccionado;
    }

   

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

}