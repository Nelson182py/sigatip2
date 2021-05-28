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
import py.com.sigati.ejb.SegmentoEJB;
import py.com.sigati.entities.Segmento;

/**
 *
 * @author juahni
 */
@ManagedBean
@SessionScoped
public class SegmentoBean extends AbstractBean implements Serializable {

    private List<Segmento> listaSegmento = new ArrayList<>();
    private Segmento segmentoSeleccionado;
    private boolean editando;

    @EJB
    private SegmentoEJB segmentoEJB;

    @PostConstruct
    public void init() {
        segmentoSeleccionado = new Segmento();
        listaSegmento = segmentoEJB.findAll();
    }

    @Override
    public void resetearValores() {
        segmentoSeleccionado = new Segmento();
        editando = false;
    }

    @Override
    public void inicializarListas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
            segmentoEJB.create(segmentoSeleccionado);
            infoMessage("Se guardó correctamente.");
            listaSegmento = segmentoEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void antesActualizar() {
        editando = true;
        listaSegmento = segmentoEJB.findAll();
    }

    @Override
    public void actualizar() {
        try {
            segmentoEJB.edit(segmentoSeleccionado);
            infoMessage("Se actualizó correctamente.");
            listaSegmento = segmentoEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbGeneric').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void eliminar() {
        try {
            segmentoEJB.remove(segmentoSeleccionado);
            infoMessage("Eliminado correctamente");
           listaSegmento = segmentoEJB.findAll();
        } catch (Exception e) {
            errorMessage("No se pudo eliminar el registro");
        }

    }

    public void agregar() {
        resetearValores();
        listaSegmento = segmentoEJB.findAll();
    }

    public List<Segmento> getListaSegmento() {
        return listaSegmento;
    }

    public void setListaSegmento(List<Segmento> listaSegmento) {
        this.listaSegmento = listaSegmento;
    }

   

    public Segmento getSegmentoSeleccionado() {
        return segmentoSeleccionado;
    }

    public void setSegmentoSeleccionado(Segmento segmentoSeleccionado) {
        this.segmentoSeleccionado = segmentoSeleccionado;
    }

   

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

}
