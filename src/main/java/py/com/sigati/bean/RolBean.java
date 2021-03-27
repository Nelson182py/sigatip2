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
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import py.com.sigati.ejb.PermisoEJB;
import py.com.sigati.ejb.RolEJB;
import py.com.sigati.entities.Permiso;
import py.com.sigati.entities.Rol;
import py.com.sigati.entities.RolPermiso;
import py.com.sigati.entities.Usuario;

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
    private List<Permiso> listaPermisosDisponibles = new ArrayList<>(); 
    private List<Permiso> listaPermisosSeleccionados = new ArrayList<>(); 
    private String alta = "alta";
    private String baja = "baja";
    private String modificacion = "modificacion";
    private String completo = "completo"; 
    private String ninguno = "ninguno";
    private String informes = "informes"; 
    
    @EJB
    private RolEJB rolEJB;

    @EJB
    private PermisoEJB permisoEJB;
    
    @Inject
    LoginBean loginBean;
     
    @PostConstruct
    public void init() {
        rolSeleccionado = new Rol();
        listaRol = rolEJB.findAll();
        listaPermisosDisponibles = permisoEJB.findAll();
      
    }

    @Override
    public void resetearValores() {
        rolSeleccionado = new Rol();
        editando = false;
        listaPermisosSeleccionados = new ArrayList<>();
    }

    @Override
    public void inicializarListas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
             
             rolEJB.create(rolSeleccionado,listaPermisosSeleccionados);
             infoMessage("Se guardó correctamente.");
             listaRol = rolEJB.findAll();
             resetearValores();
             PrimeFaces.current().executeScript("PF('wbRoles').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void antesActualizar() {
        editando = true;
        listaRol = rolEJB.findAll();
        listaPermisosSeleccionados =  rolEJB.findPermisos(rolSeleccionado);
    }

    @Override
    public void actualizar() {
        try {
            rolEJB.edit(rolSeleccionado);
            infoMessage("Se actualizó correctamente.");
            listaRol = rolEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbRoles').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void eliminar() {
        try {
            rolEJB.remove(rolSeleccionado);
            infoMessage("Eliminado correctamente");
            listaRol = rolEJB.findAll();
        } catch (Exception e) {
            errorMessage("No se pudo eliminar el registro");
        }

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
    
    public void agregar() {
        resetearValores();
        listaRol = rolEJB.findAll();
    }

    public List<Rol> getListaRol() {
        return listaRol;
    }

    public void setListaRol(List<Rol> listaRol) {
        this.listaRol = listaRol;
    }

    public String salir(){        
        return "login_b";
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

    List<Rol> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Permiso> getListaPermisosDisponibles() {
        return listaPermisosDisponibles;
    }

    public void setListaPermisosDisponibles(List<Permiso> listaPermisosDisponibles) {
        this.listaPermisosDisponibles = listaPermisosDisponibles;
    }

    public List<Permiso> getListaPermisosSeleccionados() {
        return listaPermisosSeleccionados;
    }

    public void setListaPermisosSeleccionados(List<Permiso> listaPermisosSeleccionados) {
        this.listaPermisosSeleccionados = listaPermisosSeleccionados;
    }

    public RolEJB getRolEJB() {
        return rolEJB;
    }

    public void setRolEJB(RolEJB RolEJB) {
        this.rolEJB = RolEJB;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
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
    
    // Puede acceder ADMIN, PM, LIDERTECNICO
    public boolean mostrarMenuActividades(){        
        Usuario u =  loginBean.getUsuarioLogueado();
        
        String admin = "Administrador";
        String pM = "Project Manager";
        String liderTecnico = "Lider Tecnico";
        String analista = "Analista";        
                
        if (u != null){
           if( u.getIdRol().getDescripcion().equals(admin) ||
               u.getIdRol().getDescripcion().equals(pM) ||
               u.getIdRol().getDescripcion().equals(liderTecnico) ){
               return true;
           }            
        }
        return false;
    }
    
    // Puede acceder ADMIN, PM, LIDERTECNICO
    public boolean mostrarMenuGestion(){        
        Usuario u =  loginBean.getUsuarioLogueado();
        
        String admin = "Administrador";
        String pM = "Project Manager";
        String liderTecnico = "Lider Tecnico";
        String analista = "Analista";        
                
        if (u != null){
           if( u.getIdRol().getDescripcion().equals(admin) ||
               u.getIdRol().getDescripcion().equals(pM) ||
               u.getIdRol().getDescripcion().equals(liderTecnico) ){
               return true;
           }            
        }
        return false;
    }
    
    // Puede acceder ADMIN
    public boolean mostrarMenuParametrizacion(){        
        Usuario u =  loginBean.getUsuarioLogueado();
        
        String admin = "Administrador";
        String pM = "Project Manager";
        String liderTecnico = "Lider Tecnico";
        String analista = "Analista";        
                
        if (u != null){
           if( u.getIdRol().getDescripcion().equals(admin) ){
               return true;
           }            
        }
        return false;
    }
    
    // Puede acceder ADMIN, PM, LIDERTECNICO y ANALISTA
    public boolean mostrarMenuInformes(){        
        Usuario u =  loginBean.getUsuarioLogueado();
        
        String admin = "Administrador";
        String pM = "Project Manager";
        String liderTecnico = "Lider Tecnico";
        String analista = "Analista";        
                
        if (u != null){
           if( u.getIdRol().getDescripcion().equals(admin) ||
               u.getIdRol().getDescripcion().equals(pM) ||
               u.getIdRol().getDescripcion().equals(liderTecnico) ||
               u.getIdRol().getDescripcion().equals(analista) ){
               return true;
           }            
        }
        return false;
    }
}
