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
import py.com.sigati.ejb.PermisoEJB;
import py.com.sigati.entities.Permiso;
import py.com.sigati.entities.Rol;
import py.com.sigati.entities.RolPermiso;
import py.com.sigati.entities.Usuario;

/**
 *
 * @author Nelson182py
 */
@ManagedBean
@SessionScoped
public class PermisoBean extends AbstractBean implements Serializable {

    private List<Permiso> listaPermiso = new ArrayList<>();
    private Permiso permisoSeleccionado;
    private boolean editando;
    private String alta = "alta";
    private String baja = "baja";
    private String modificacion = "modificacion";
    private String completo = "completo"; 
    private String ninguno = "ninguno";
    private String informes = "descarga"; 
    
    @EJB
    private PermisoEJB PermisoEJB;

    @PostConstruct
    public void init() {
        permisoSeleccionado = new Permiso();
        listaPermiso = PermisoEJB.findAll();
    }

    @Override
    public void resetearValores() {
        permisoSeleccionado = new Permiso();
        editando = false;
    }

    @Override
    public void inicializarListas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
            PermisoEJB.create(permisoSeleccionado);
            infoMessage("Se guardó correctamente.");
            listaPermiso = PermisoEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbPermisos').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void antesActualizar() {
        editando = true;
        listaPermiso = PermisoEJB.findAll();
    }

    @Override
    public void actualizar() {
        try {
            PermisoEJB.edit(permisoSeleccionado);
            infoMessage("Se actualizó correctamente.");
            listaPermiso = PermisoEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void eliminar() {
        try {
            PermisoEJB.remove(permisoSeleccionado);
            infoMessage("Eliminado correctamente");
            listaPermiso = PermisoEJB.findAll();
        } catch (Exception e) {
            errorMessage("No se pudo eliminar el registro");
        }

    }

    public boolean mostrarMenu(String rol){        
        Usuario u =  loginBean.getUsuarioLogueado();
        
        if (u != null){
           if( u.getIdRol().getDescripcion().equals(rol)){
               return true;
           }            
        }
        return false;
    }
    
    public void agregar() {
        resetearValores();
        listaPermiso = PermisoEJB.findAll();
    }

    public List<Permiso> getListaPermiso() {
        return listaPermiso;
    }

    public void setListaPermiso(List<Permiso> listaPermiso) {
        this.listaPermiso = listaPermiso;
    }

   

    public Permiso getPermisoSeleccionado() {
        return permisoSeleccionado;
    }

    public void setPermisoSeleccionado(Permiso PermisoSeleccionado) {
        this.permisoSeleccionado = PermisoSeleccionado;
    }

   

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
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
