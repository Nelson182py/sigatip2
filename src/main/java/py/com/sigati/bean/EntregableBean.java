
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
import py.com.sigati.ejb.EntregableEJB;
import py.com.sigati.entities.Entregable;
import py.com.sigati.entities.Usuario;


/**
 *
 * @author Juanhi
 */
@ManagedBean
@SessionScoped
public class EntregableBean extends AbstractBean implements Serializable {

    private List<Entregable> listaEntregable = new ArrayList<>();
    private List<Entregable> listaEntregablesActivos;
    private Entregable entregableSeleccionado;
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

    @EJB
    private EntregableEJB entregableEJB;

    @PostConstruct
    public void init() {
        entregableSeleccionado = new Entregable();
        listaEntregable = entregableEJB.findAll();
    }

    @Override
    public void resetearValores() {
        entregableSeleccionado = new Entregable();
        editando = false;
    }

    @Override
    public void inicializarListas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
            entregableEJB.create(entregableSeleccionado);
            infoMessage("Se guardó correctamente.");
            listaEntregable = entregableEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void antesActualizar() {
        editando = true;
        listaEntregable = entregableEJB.findAll();
    }

    @Override
    public void actualizar() {
        try {
            entregableEJB.edit(entregableSeleccionado);
            infoMessage("Se actualizó correctamente.");
            listaEntregable = entregableEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void eliminar() {
        try {
            entregableEJB.remove(entregableSeleccionado);
            infoMessage("Eliminado correctamente");
           listaEntregable = entregableEJB.findAll();
        } catch (Exception e) {
            errorMessage("No se pudo eliminar el registro");
        }

    }

    public void agregar() {
        resetearValores();
        listaEntregable = entregableEJB.findAll();
    }

    public List<Entregable> getListaEntregable() {
        return listaEntregable;
    }

    public void setListaEntregable(List<Entregable> listaEntregable) {
        this.listaEntregable = listaEntregable;
    }

   

    public Entregable getEntregableSeleccionado() {
        return entregableSeleccionado;
    }

    public void setEntregableSeleccionado(Entregable entregableSeleccionado) {
        this.entregableSeleccionado = entregableSeleccionado;
    }

   

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }
   
    public boolean agregarEntregable() {
        Usuario u = loginBean.getUsuarioLogueado();

        if (u != null) {
            if (u.getIdRol().getDescripcion().equals(liderTecnico)
                    || u.getIdRol().getDescripcion().equals(admin)) {

                return true;
            }
        }
        return false;
    }

    public boolean editarEntregable() {
        Usuario u = loginBean.getUsuarioLogueado();

        if (u != null) {
            if (u.getIdRol().getDescripcion().equals(liderTecnico)
                    || u.getIdRol().getDescripcion().equals(admin)) {

                return true;
            }
        }
        return false;
    }
   
     public List<Entregable> getListaEntregablesActivos() {
        listaEntregablesActivos = new ArrayList<>();
        listaEntregable = entregableEJB.findAll();
         for (Entregable  e:listaEntregable) {
            if(e != null){
                if(e.getIdEstado().getDescripcion().equals("Iniciado") ||
                        e.getIdEstado().getDescripcion().equals("En curso")){
                    listaEntregablesActivos.add(e);
                }      
            }
         }    
        return listaEntregablesActivos;
    }
}
