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
import py.com.sigati.ejb.NacionalidadEJB;
import py.com.sigati.entities.Nacionalidad;

/**
 *
 * @author juahni
 */
@ManagedBean
@SessionScoped
public class NacionalidadBean extends AbstractBean implements Serializable {

    private List<Nacionalidad> listaNacionalidad = new ArrayList<>();
    private Nacionalidad nacionalidadSeleccionada;
    private boolean editando;

    @EJB
    private NacionalidadEJB nacionalidadEJB;

    @PostConstruct
    public void init() {
        nacionalidadSeleccionada = new Nacionalidad();
        listaNacionalidad = nacionalidadEJB.findAll();
    }

    @Override
    public void resetearValores() {
        nacionalidadSeleccionada = new Nacionalidad();
        editando = false;
    }

    @Override
    public void inicializarListas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
            nacionalidadEJB.create(nacionalidadSeleccionada);
            infoMessage("Se guardó correctamente.");
            listaNacionalidad = nacionalidadEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void antesActualizar() {
        editando = true;
        listaNacionalidad = nacionalidadEJB.findAll();
    }

    @Override
    public void actualizar() {
        try {
            nacionalidadEJB.edit(nacionalidadSeleccionada);
            infoMessage("Se actualizó correctamente.");
            listaNacionalidad = nacionalidadEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void eliminar() {
        try {
            nacionalidadEJB.remove(nacionalidadSeleccionada);
            infoMessage("Eliminado correctamente");
            listaNacionalidad = nacionalidadEJB.findAll();
        } catch (Exception e) {
            errorMessage("No se pudo eliminar el registro");
        }

    }

    public void agregar() {
        resetearValores();
        listaNacionalidad = nacionalidadEJB.findAll();
    }

    public List<Nacionalidad> getListaNacionalidad() {
        return listaNacionalidad;
    }

    public void setListaNacionalidad(List<Nacionalidad> listaNacionalidad) {
        this.listaNacionalidad = listaNacionalidad;
    }

   

    public Nacionalidad getNacionalidadSeleccionado() {
        return nacionalidadSeleccionada;
    }

    public void setNacionalidadSeleccionado(Nacionalidad nacionalidadSeleccionada) {
        this.nacionalidadSeleccionada = nacionalidadSeleccionada;
    }

   

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

}
