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
import py.com.sigati.ejb.RolEJB;
import py.com.sigati.entities.Rol;

/**
 *
 * @author Juanhi
 */
@ManagedBean
@SessionScoped
public class RolBean extends AbstractBean implements Serializable {

    private List<Rol> listaRol = new ArrayList<>();
    private Rol rolSeleccionado;
    private boolean editando;

    @EJB
    private RolEJB RolEJB;

    @PostConstruct
    public void init() {
        rolSeleccionado = new Rol();
        listaRol = RolEJB.findAll();
    }

    @Override
    public void resetearValores() {
        rolSeleccionado = new Rol();
        editando = false;
    }

    @Override
    public void inicializarListas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
             RolEJB.create(rolSeleccionado);
             infoMessage("Se guardó correctamente.");
             listaRol = RolEJB.findAll();
             resetearValores();
             PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void antesActualizar() {
        editando = true;
        listaRol = RolEJB.findAll();
    }

    @Override
    public void actualizar() {
        try {
            RolEJB.edit(rolSeleccionado);
            infoMessage("Se actualizó correctamente.");
            listaRol = RolEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void eliminar() {
        try {
            RolEJB.remove(rolSeleccionado);
            infoMessage("Eliminado correctamente");
           listaRol = RolEJB.findAll();
        } catch (Exception e) {
            errorMessage("No se pudo eliminar el registro");
        }

    }

    public void agregar() {
        resetearValores();
        listaRol = RolEJB.findAll();
    }

    public List<Rol> getListaRol() {
        return listaRol;
    }

    public void setListaRol(List<Rol> listaRol) {
        this.listaRol = listaRol;
    }

   

    public Rol getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(Rol RolSeleccionado) {
        this.rolSeleccionado = RolSeleccionado;
    }

   

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

}
