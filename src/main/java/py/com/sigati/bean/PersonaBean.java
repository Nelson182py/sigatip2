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
import py.com.sigati.ejb.PersonaEJB;
import py.com.sigati.entities.Persona;

/**
 *
 * @author Juanhi
 */
@ManagedBean
@SessionScoped
public class PersonaBean extends AbstractBean implements Serializable {

    private List<Persona> listaPersona = new ArrayList<>();
    private Persona personaSeleccionado;
    private boolean editando;

    @EJB
    private PersonaEJB PersonaEJB;

    @PostConstruct
    public void init() {
        personaSeleccionado = new Persona();
        listaPersona = PersonaEJB.findAll();
    }

    @Override
    public void resetearValores() {
        personaSeleccionado = new Persona();
        editando = false;
    }

    @Override
    public void inicializarListas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
             PersonaEJB.create(personaSeleccionado);
            infoMessage("Se guardó correctamente.");
            listaPersona = PersonaEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void antesActualizar() {
        editando = true;
        listaPersona = PersonaEJB.findAll();
    }

    @Override
    public void actualizar() {
        try {
            PersonaEJB.edit(personaSeleccionado);
            infoMessage("Se actualizó correctamente.");
            listaPersona = PersonaEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void eliminar() {
        try {
            PersonaEJB.remove(personaSeleccionado);
            infoMessage("Eliminado correctamente");
           listaPersona = PersonaEJB.findAll();
        } catch (Exception e) {
            errorMessage("No se pudo eliminar el registro");
        }

    }

    public void agregar() {
        resetearValores();
        listaPersona = PersonaEJB.findAll();
    }

    public List<Persona> getListaPersona() {
        return listaPersona;
    }

    public void setListaPersona(List<Persona> listaPersona) {
        this.listaPersona = listaPersona;
    }

   

    public Persona getPersonaSeleccionado() {
        return personaSeleccionado;
    }

    public void setPersonaSeleccionado(Persona PersonaSeleccionado) {
        this.personaSeleccionado = PersonaSeleccionado;
    }

   

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

}
