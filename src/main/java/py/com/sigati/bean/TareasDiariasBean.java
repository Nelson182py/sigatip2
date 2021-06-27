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
import py.com.sigati.ejb.TareaEJB;
import py.com.sigati.ejb.EstadoEJB;
import py.com.sigati.ejb.TareasDiariasEJB;
import py.com.sigati.entities.Estado;
import py.com.sigati.entities.Tarea;
import py.com.sigati.entities.TareasDiarias;
import py.com.sigati.entities.Usuario;

/**
 *
 * @author Juanhi
 */
@ManagedBean
@SessionScoped
public class TareasDiariasBean extends AbstractBean implements Serializable {

    private List<TareasDiarias> listaTareasDiarias = new ArrayList<>();
    private List<TareasDiarias> listaTareasDiariasPorAnalista = new ArrayList<>();
    private List<Tarea> listaTareasPorAnalistas = new ArrayList<>();
    private List<Estado> listaEstadoTarea = new ArrayList<>();
    private List<Tarea> listaTareasPorAnalistaEntregables = new ArrayList<>();
    
    private Tarea tarea;
    private TareasDiarias tareaSeleccionado;
    private Estado estadoSeleccionado;
    private boolean editando;
    private String alta = "alta";
    private String baja = "baja";
    private String modificacion = "modificacion";
    private String completo = "completo"; 
    private String ninguno = "ninguno";
    private String informes = "descarga"; 
    private String admin = "Administrador";
    private String pM = "Project Manager";
    private String liderTecnico = "Lider Tecnico";
    private String analista = "Analista";  
    private String soporte = "Soporte";
    private String estado = "No iniciado";
    private int estadoId = 0;
    
    @EJB
    private TareasDiariasEJB tareasDiariasEJB;
    
    @EJB
    private TareaEJB tareasEJB;
    
    @EJB
    private EstadoEJB estadosEJB;
    
    @PostConstruct
    public void init() {
        tareaSeleccionado = new TareasDiarias();
        estadoSeleccionado = new Estado();
        tarea = new Tarea();
        tareaSeleccionado.setHoras(0);
        tareaSeleccionado.setFecha(new Date());
        listaTareasDiarias = tareasDiariasEJB.findAll();
        listaTareasDiariasPorAnalista = getListaTareasDiariasPorAnalistas();
        listaTareasPorAnalistas = getListaTareasPorAnalistas();
        listaEstadoTarea = getListaEstadosPorTarea();
    }

    @Override
    public void resetearValores() {
        tareaSeleccionado = new TareasDiarias();
        tarea = new Tarea();
        editando = false;
    }

    @Override
    public void inicializarListas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {

            tarea.setHoras(tareaSeleccionado.getHoras()+tarea.getHoras());
            tarea.setIdEstado(estadoSeleccionado);
            tareaSeleccionado.setIdTarea(tarea);
            tareasEJB.edit(tarea);
            tareasDiariasEJB.create(tareaSeleccionado);
            listaTareasDiarias = tareasDiariasEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbTareas').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error. "+e.getMessage());
        }
    }

    @Override
    public void antesActualizar() {
        editando = true;
        listaTareasDiarias = tareasDiariasEJB.findAll();
    }

    @Override
    public void actualizar() {
        try {
            tareasDiariasEJB.edit(tareaSeleccionado);
            infoMessage("Se actualizó correctamente.");
            listaTareasDiarias = tareasDiariasEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbTareas').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void eliminar() {
        try {
            tareasDiariasEJB.remove(tareaSeleccionado);
            infoMessage("Eliminado correctamente");
            listaTareasDiarias = tareasDiariasEJB.findAll();
        } catch (Exception e) {
            errorMessage("No se pudo eliminar el registro");
        }

    }

    public void agregar() {
        resetearValores();
        //listaTareasPorAnalistas = getListaTareasPorAnalistas();
        listaTareasPorAnalistas = getListaTareasPorAnalistasNoFinalizadas();
    }

     public boolean agregarTarea() {
        Usuario u = loginBean.getUsuarioLogueado();

        if (u != null) {
            if (u.getIdRol().getDescripcion().equals(liderTecnico)
                    || u.getIdRol().getDescripcion().equals(admin)) {

                return true;
            }
        }
        return false;
    }

    public boolean editarTarea() {
        Usuario u = loginBean.getUsuarioLogueado();

        if (u != null) {
            if (u.getIdRol().getDescripcion().equals(admin)
                    || u.getIdRol().getDescripcion().equals(liderTecnico)
                    || u.getIdRol().getDescripcion().equals(analista)) {

                return true;
            }
        }
        return false;
    }
   
    public boolean eliminarTarea() {
        Usuario u = loginBean.getUsuarioLogueado();

        if (u != null) {
            if (u.getIdRol().getDescripcion().equals(admin)
                    || u.getIdRol().getDescripcion().equals(liderTecnico)) {

                return true;
            }
        }
        return false;
    }
    
    public List<TareasDiarias> getListaTareasDiariasPorAnalistas() {
        
       Usuario u =  loginBean.getUsuarioLogueado();       
       listaTareasDiarias = tareasDiariasEJB.findAll();
       listaTareasDiariasPorAnalista = new ArrayList<>();
    
        for (TareasDiarias t:listaTareasDiarias) {
            if (t != null){
                if (u.getUsuario().equals(t.getIdTarea().getIdResponsable().getUsuario())){
                    if(!t.getIdTarea().getIdEstado().getDescripcion().equals("Finalizado") 
                       && !t.getIdTarea().getIdEstado().getDescripcion().equals("Cancelado")){
                    
                        listaTareasDiariasPorAnalista.add(t);
                    }
                }
            }
        }
        return listaTareasDiariasPorAnalista;
    }
    
    public List<Estado> getListaEstadosPorTarea() {
        
       List<Estado> listaEstado = new ArrayList<>();
       List<Estado> listaEstadoTmp = new ArrayList<>();
       listaEstado = estadosEJB.findAll();
       
       for (Estado e:listaEstado) {
            if (e != null){
                if (!e.getDescripcion().equals("No iniciado")){
                        listaEstadoTmp.add(e);
                }
                
            }
        }
       
        return listaEstadoTmp;
    }
    
    public List<Tarea> getListaTareasPorAnalistasEntregables() {
        
       Usuario u =  loginBean.getUsuarioLogueado();       
       List<Tarea> listaTarea = tareasEJB.findAll();
       listaTareasPorAnalistaEntregables = new ArrayList<>();
    
        for (Tarea t:listaTarea) {
            if (t != null){
                if (u.getUsuario().equals(t.getIdResponsable().getUsuario()) &&
                        t.getIdEntregable() != null){
                     listaTareasPorAnalistaEntregables.add(t);
                }
            }
        }
        return listaTareasPorAnalistaEntregables;
    }
      
      
    public List<Tarea> getListaTareasPorAnalistas() {
        
       Usuario u =  loginBean.getUsuarioLogueado();       
       List<Tarea> listaTarea = tareasEJB.findAll();
       listaTareasPorAnalistas = new ArrayList<>();
    
        for (Tarea t:listaTarea) {
            if (t != null){
                if (u.getUsuario().equals(t.getIdResponsable().getUsuario())){
                     listaTareasPorAnalistas.add(t);
                }
            }
        }
        return listaTareasPorAnalistas;
    }
    
    public List<Tarea> getListaTareasPorAnalistasNoFinalizadas() {
        
       Usuario u =  loginBean.getUsuarioLogueado();       
       List<Tarea> listaTarea = tareasEJB.findAll();
       listaTareasPorAnalistas = new ArrayList<>();
    
        for (Tarea t:listaTarea) {
            if (t != null){
                if (u.getUsuario().equals(t.getIdResponsable().getUsuario())){
                    if(!t.getDescripcion().equals("Finalizado"))
                     listaTareasPorAnalistas.add(t);
                }
            }
        }
        return listaTareasPorAnalistas;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estado) {
        this.estadoId = estado;
    }

    public List<TareasDiarias> getListaTareasDiarias() {
        return listaTareasDiarias;
    }

    public void setListaTareasDiarias(List<TareasDiarias> listaTareasDiarias) {
        this.listaTareasDiarias = listaTareasDiarias;
    }

    public List<TareasDiarias> getListaTareasDiariasPorAnalista() {
        return listaTareasDiariasPorAnalista;
    }

    public void setListaTareasDiariasPorAnalista(List<TareasDiarias> listaTareasDiariasPorAnalista) {
        this.listaTareasDiariasPorAnalista = listaTareasDiariasPorAnalista;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public List<Estado> getListaEstadoTarea() {
        return listaEstadoTarea;
    }

    public void setListaEstadoTarea(List<Estado> listaEstadoTarea) {
        this.listaEstadoTarea = listaEstadoTarea;
    }

    public Estado getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(Estado estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }
    
     public List<TareasDiarias> getListaTarea() {
        return listaTareasDiarias;
    }

    public void setListaTarea(List<TareasDiarias> listaTarea) {
        this.listaTareasDiarias = listaTarea;
    }

    public TareasDiarias getTareaSeleccionado() {
        return tareaSeleccionado;
    }

    public void setTareaSeleccionado(TareasDiarias tareaSeleccionado) {
        this.tareaSeleccionado = tareaSeleccionado;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }
    
}