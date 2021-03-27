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
import py.com.sigati.ejb.PersonaEJB;
import py.com.sigati.entities.Nacionalidad;
import py.com.sigati.entities.Permiso;
import py.com.sigati.entities.Persona;
import py.com.sigati.entities.Rol;
import py.com.sigati.entities.RolPermiso;
import py.com.sigati.entities.Usuario;

/**
 *
 * @author Juanhi
 */
@ManagedBean
@SessionScoped
public class PersonaBean extends AbstractBean implements Serializable {

    private List<Persona> listaPersona = new ArrayList<>();
    private List<Nacionalidad> listaNacionalidades = new ArrayList<>();
    private Persona personaSeleccionado;
    private Nacionalidad nacionalidadSeleccionada;

    private boolean editando;
    private String alta = "alta";
    private String baja = "baja";
    private String modificacion = "modificacion";
    private String completo = "completo"; 
    private String ninguno = "ninguno";
    private String informes = "informes"; 
        

    @EJB
    private PersonaEJB personaEJB;
    @EJB
    private NacionalidadEJB nacionalidadEJB;

    
    @PostConstruct
    public void init() {
        personaSeleccionado = new Persona();
        listaNacionalidades = nacionalidadEJB.findAll();
        listaPersona = personaEJB.findAll();
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
            personaSeleccionado.setIdNacionalidad(nacionalidadSeleccionada);
            personaEJB.create(personaSeleccionado);
            infoMessage("Se guardó correctamente.");
            listaPersona = personaEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void antesActualizar() {
        editando = true;
        listaPersona = personaEJB.findAll();
        listaNacionalidades = nacionalidadEJB.findAll();
    }

    @Override
    public void actualizar() {
        try {
            personaEJB.edit(personaSeleccionado);
            infoMessage("Se actualizó correctamente.");
            listaPersona = personaEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void eliminar() {
        try {
            personaEJB.remove(personaSeleccionado);
            infoMessage("Eliminado correctamente");
            listaPersona = personaEJB.findAll();
        } catch (Exception e) {
            errorMessage("No se pudo eliminar el registro");
        }

    }

    public void agregar() {
        resetearValores();
        listaPersona = personaEJB.findAll();
    }

    public List<Persona> getListaPersona() {
        return listaPersona;
    }

    public void setListaPersona(List<Persona> listaPersona) {
        this.listaPersona = listaPersona;
    }

    public List<Nacionalidad> getListaNacionalidades() {
        return listaNacionalidades;
    }

    public void setListaNacionalidades(List<Nacionalidad> listaNacionalidades) {
        this.listaNacionalidades = listaNacionalidades;
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
    
    public Nacionalidad getNacionalidadSeleccionada() {
        return nacionalidadSeleccionada;
    }

    public void setNacionalidadSeleccionada(Nacionalidad nacionalidadSeleccionada) {
        this.nacionalidadSeleccionada = nacionalidadSeleccionada;
    }
    // Puede acceder modificacion y total
    public boolean mostrarNuevo(){        
        Usuario u =  loginBean.getUsuarioLogueado();
        Rol r = u.getIdRol();
        List<RolPermiso> permisos = r.getRolPermisoList();
        
        for(RolPermiso p:permisos){                 
            if (p != null){
                if( p.getIdPermiso().getDescripcion().equals(alta) ||
                    p.getIdPermiso().getDescripcion().equals(alta))
                return true;
            }            
        }
        return false;
    }
}
