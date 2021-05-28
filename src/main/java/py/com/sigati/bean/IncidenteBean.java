/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.sigati.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import py.com.sigati.ejb.IncidenteEJB;
import py.com.sigati.ejb.TareaEJB;
import py.com.sigati.entities.Incidente;
import py.com.sigati.entities.Tarea;
import py.com.sigati.entities.Usuario;

/**
 *
 * @author Juanhi
 */
@ManagedBean
@SessionScoped
public class IncidenteBean extends AbstractBean implements Serializable {

    private List<Incidente> listaIncidente = new ArrayList<>();
    private Incidente incidenteSeleccionado;
    private DonutChartModel donutModel;
    private DonutChartModel donutModel2;
    private boolean editando;

    @EJB
    private IncidenteEJB incidenteEJB;
    @EJB
    private TareaEJB tareaEJB;
    
    @PostConstruct
    public void init() {
        incidenteSeleccionado = new Incidente();
        listaIncidente = this.getListaIncidenteActivos();
         //createDonutModel();
         donutModel = new DonutChartModel();
         donutModel2 = new DonutChartModel();
    }

    @Override
    public void resetearValores() {
        incidenteSeleccionado = new Incidente();
        editando = false;
    }

    @Override
    public void inicializarListas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
            incidenteEJB.create(incidenteSeleccionado);
            infoMessage("Se guardó correctamente.");
            listaIncidente = this.getListaIncidenteActivos();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbIncidentes').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void antesActualizar() {
        editando = true;
        listaIncidente = incidenteEJB.findAll();
    }

    @Override
    public void actualizar() {
        try {
            incidenteEJB.edit(incidenteSeleccionado);
            infoMessage("Se actualizó correctamente.");
            listaIncidente = this.getListaIncidenteActivos();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbIncidentes').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void eliminar() {
        try {
            //incidenteEJB.remove(incidenteSeleccionado);
            // cambiar incidenteSeleccionado.setActivo(0);
            incidenteEJB.edit(incidenteSeleccionado);
            infoMessage("Eliminado correctamente");
            listaIncidente = this.getListaIncidenteActivos();
        } catch (Exception e) {
            errorMessage("No se pudo eliminar el registro");
        }

    }
    
    public void actualizarGrafico() {
        createDonutModel();
    }
    
    public void actualizarGrafico2() {
        createDonutModel2();
    }
    
    public void agregar() {
        resetearValores();
        listaIncidente = this.getListaIncidenteActivos();
    }

    public List<Incidente> getListaIncidente() {
        return listaIncidente;
    }

    public void setListaIncidente(List<Incidente> listaIncidente) {
        this.listaIncidente = listaIncidente;
    }

    public Incidente getIncidenteSeleccionado() {
        return incidenteSeleccionado;
    }

    public void setIncidenteSeleccionado(Incidente incidenteSeleccionado) {
        this.incidenteSeleccionado = incidenteSeleccionado;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public DonutChartModel getDonutModel() {
        return donutModel;
    }

    public void setDonutModel(DonutChartModel donutModel) {
        this.donutModel = donutModel;
    }
    
    public DonutChartModel getDonutModel2() {
        return donutModel2;
    }
    
    public void setDonutModel2(DonutChartModel donutModel) {
        this.donutModel2 = donutModel;
    }
    
    public List<Incidente> getListaIncidenteActivos() {
         
        List<Incidente> listaIncidenteActivos = new ArrayList<>();
        listaIncidente = incidenteEJB.findAll();
        for (Incidente i : listaIncidente) {
            // cambiar if(i.getActivo().equals(1)){
            // cambiar     listaIncidenteActivos.add(i);
            // cambiar }       
        }
        listaIncidenteActivos=listaIncidente;
        return listaIncidenteActivos;
    }

     public void createDonutModel() {
         
        //obtener una tareaa seleccionada
        List<Tarea> listaTarea = new ArrayList<>();   
        //listaTarea = incidenteSeleccionado.getTareaList();
        //incidenteSeleccionado = incidenteEJB.find(incidenteSeleccionado.getId());
        listaTarea =tareaEJB.findIncidentesDeTareas(incidenteSeleccionado );
        
        int totalHorasEstimadas=0;
        int totalHorasConsumidas=0;
        int totalHorasDisponibles=0;

        //calcular el porcentaje        
        for (Tarea t:listaTarea) {
            if (t != null){
                totalHorasEstimadas=totalHorasEstimadas+t.getHorasEstimadas();
                totalHorasConsumidas=totalHorasConsumidas+t.getHoras();
            }
        }
        totalHorasDisponibles=totalHorasEstimadas-totalHorasConsumidas;
        //dibujar
        donutModel = new DonutChartModel();
        ChartData data = new ChartData();

        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(totalHorasDisponibles);
        values.add(totalHorasConsumidas);
        values.add(0);
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(132, 255, 99)"); //verde
        bgColors.add("rgb(54, 162, 235)"); //auzl
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Disponibles");
        labels.add("Comsumidas");
        data.setLabels(labels);

        donutModel.setData(data);
    }
     
    public void createDonutModel2() {
         
        //obtener una tareaa seleccionada
        List<Tarea> listaTarea = new ArrayList<>();   
        //listaTarea = incidenteSeleccionado.getTareaList();
        //incidenteSeleccionado = incidenteEJB.find(incidenteSeleccionado.getId());
        listaTarea =tareaEJB.findIncidentesDeTareas(incidenteSeleccionado );
        
        int totalTareasTotales=0;
        int totalTareasNoIniciadas=0;
        int totalTareasEnProgreso=0;
        int totalTareasFinalizadas=0;
        int totalTareasCanceladas=0;
        //calcular el porcentaje        
        for (Tarea t:listaTarea) {
            if (t != null){
                if(t.getIdEstado().getDescripcion().compareTo("No iniciado")==0){
                        totalTareasNoIniciadas++;
                    }
                    if(t.getIdEstado().getDescripcion().compareTo("En Progreso")==0){
                        totalTareasEnProgreso++;
                    }
                    if(t.getIdEstado().getDescripcion().compareTo("Finalizado")==0){
                        totalTareasFinalizadas++;
                    }
                    if(t.getIdEstado().getDescripcion().compareTo("Cancelada")==0){
                        totalTareasCanceladas++;
                    }
                    totalTareasTotales++;
            }
        }

        //dibujar
        donutModel2 = new DonutChartModel();
        ChartData data = new ChartData();
        
        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(totalTareasNoIniciadas);
        values.add(totalTareasEnProgreso);
        values.add(totalTareasFinalizadas);
        values.add(totalTareasCanceladas);
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 205, 86)"); //amarillo
        bgColors.add("rgb(54, 162, 235)"); //auzl
        bgColors.add("rgb(132, 255, 99)"); //verde
        bgColors.add("rgb(255, 99, 132)"); //rojo
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("No iniciadas");
        labels.add("En Progreso");
        labels.add("Finalizadas");
        labels.add("Canceladas");
        data.setLabels(labels);

        donutModel2.setData(data);
    } 
}
