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
import py.com.sigati.ejb.AreaEJB;
import py.com.sigati.entities.Area;

/**
 *
 * @author juahni
 */
@ManagedBean
@SessionScoped
public class AreaBean extends AbstractBean implements Serializable {

    private List<Area> listaArea = new ArrayList<>();
    private Area areaSeleccionada;
    private boolean editando;

    @EJB
    private AreaEJB areaEJB;

    @PostConstruct
    public void init() {
        areaSeleccionada = new Area();
        listaArea = areaEJB.findAll();
    }

    @Override
    public void resetearValores() {
        areaSeleccionada = new Area();
        editando = false;
    }

    @Override
    public void inicializarListas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
            areaEJB.create(areaSeleccionada);
            infoMessage("Se guardó correctamente.");
            listaArea = areaEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void antesActualizar() {
        editando = true;
        listaArea = areaEJB.findAll();
    }

    @Override
    public void actualizar() {
        try {
            areaEJB.edit(areaSeleccionada);
            infoMessage("Se actualizó correctamente.");
            listaArea = areaEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void eliminar() {
        try {
           areaEJB.remove(areaSeleccionada);
           infoMessage("Eliminado correctamente");
           listaArea = areaEJB.findAll();
        } catch (Exception e) {
            errorMessage("No se pudo eliminar el registro");
        }

    }

    public void agregar() {
        resetearValores();
        listaArea = areaEJB.findAll();
    }

    public List<Area> getListaArea() {
        return listaArea;
    }

    public void setListaArea(List<Area> listaArea) {
        this.listaArea = listaArea;
    }

   

    public Area getAreaSeleccionado() {
        return areaSeleccionada;
    }

    public void setAreaSeleccionado(Area areaSeleccionada) {
        this.areaSeleccionada = areaSeleccionada;
    }

   

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

}