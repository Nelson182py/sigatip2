/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.sigati.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
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
    private DonutChartModel donutModel3;
    private DonutChartModel donutModelConsolidado;
    private LineChartModel cartesianLinerModel;
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
         donutModel3 = new DonutChartModel();
         donutModelConsolidado = new DonutChartModel();
         actualizarGraficoConsolidado();
         cartesianLinerModel = new LineChartModel();
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
    
    public void actualizarGrafico3() throws ParseException {
        createDonutModel3();
    }

    public void actualizarGraficoConsolidado() {
        createDonutModelConsolidado();
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
    
    public DonutChartModel getDonutModel3() {
        return donutModel3;
    }
    
    public void setDonutModel3(DonutChartModel donutModel) {
        this.donutModel3 = donutModel;
    }
    
    public LineChartModel getCartesianLinerModel() {
        return cartesianLinerModel;
    }

    public void setCartesianLinerModel(LineChartModel cartesianLinerModel) {
        this.cartesianLinerModel = cartesianLinerModel;
    }

    public DonutChartModel getDonutModelConsolidado() {
        return donutModelConsolidado;
    }

    public void setDonutModelConsolidado(DonutChartModel donutModelConsolidado) {
        this.donutModelConsolidado = donutModelConsolidado;
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
    
    public void createDonutModel3() throws ParseException {
         
        List<Tarea> listaTarea = new ArrayList<>();   
        listaTarea =tareaEJB.findIncidentesDeTareas(incidenteSeleccionado );
        
        int estimadas1al5=0;
        int estimadas6al10=0;
        int estimadas11al15=0;
        int estimadas16al20=0;
        int estimadas21al25=0;
        int estimadas26al30=0;
        int reales1al5=0;
        int reales6al10=0;
        int reales11al15=0;
        int reales16al20=0;
        int reales21al25=0;
        int reales26al30=0;

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdformat.parse("2021-07-01");
        Date d2 = sdformat.parse("2021-07-02");
        Date d3 = sdformat.parse("2021-07-03");
        Date d4 = sdformat.parse("2021-07-04");
        Date d5 = sdformat.parse("2021-07-05");
        Date d6 = sdformat.parse("2021-07-06");
        Date d7 = sdformat.parse("2021-07-07");
        Date d8 = sdformat.parse("2021-07-08");
        Date d9 = sdformat.parse("2021-07-09");
        Date d10 = sdformat.parse("2021-07-10");
        Date d11 = sdformat.parse("2021-07-11");
        Date d12 = sdformat.parse("2021-07-12");
        Date d13 = sdformat.parse("2021-07-13");
        Date d14 = sdformat.parse("2021-07-14");
        Date d15 = sdformat.parse("2021-07-15");
        Date d16 = sdformat.parse("2021-07-16");
        Date d17 = sdformat.parse("2021-07-17");
        Date d18 = sdformat.parse("2021-07-18");
        Date d19 = sdformat.parse("2021-07-19");
        Date d20 = sdformat.parse("2021-07-20");
        Date d21 = sdformat.parse("2021-07-21");
        Date d22 = sdformat.parse("2021-07-22");
        Date d23 = sdformat.parse("2021-07-23");
        Date d24 = sdformat.parse("2021-07-24");
        Date d25 = sdformat.parse("2021-07-25");
        Date d26 = sdformat.parse("2021-07-26");
        Date d27 = sdformat.parse("2021-07-27");
        Date d28 = sdformat.parse("2021-07-28");
        Date d29 = sdformat.parse("2021-07-29");
        Date d30 = sdformat.parse("2021-07-30");
        
        
        //calcular el porcentaje        
        for (Tarea t:listaTarea) {
            if (t != null){
                if(t.getFechaFin().compareTo(d1)==0){
                    reales1al5++;
                }
                if(t.getFechaFin().compareTo(d2)==0){
                    reales1al5++;
                }
                if(t.getFechaFin().compareTo(d3)==0){
                    reales1al5++;
                }
                if(t.getFechaFin().compareTo(d4)==0){
                    reales1al5++;
                }
                if(t.getFechaFin().compareTo(d5)==0){
                    reales1al5++;
                }
                if(t.getFechaFin().compareTo(d6)==0){
                    reales6al10++;
                }
                if(t.getFechaFin().compareTo(d7)==0){
                    reales6al10++;
                }
                if(t.getFechaFin().compareTo(d8)==0){
                    reales6al10++;
                }
                if(t.getFechaFin().compareTo(d9)==0){
                    reales6al10++;
                }
                if(t.getFechaFin().compareTo(d10)==0){
                    reales6al10++;
                }
                if(t.getFechaFin().compareTo(d11)==0){
                    reales11al15++;
                }
                if(t.getFechaFin().compareTo(d12)==0){
                    reales11al15++;
                }
                if(t.getFechaFin().compareTo(d13)==0){
                    reales11al15++;
                }
                if(t.getFechaFin().compareTo(d14)==0){
                    reales11al15++;
                }
                if(t.getFechaFin().compareTo(d15)==0){
                    reales11al15++;
                }       
                if(t.getFechaFin().compareTo(d16)==0){
                    reales16al20++;
                }
                if(t.getFechaFin().compareTo(d17)==0){
                     reales16al20++;
                }
                if(t.getFechaFin().compareTo(d18)==0){
                     reales16al20++;
                }
                if(t.getFechaFin().compareTo(d19)==0){
                     reales16al20++;
                }
                if(t.getFechaFin().compareTo(d20)==0){
                     reales16al20++;
                } 
                if(t.getFechaFin().compareTo(d21)==0){
                     reales21al25++;
                }
                if(t.getFechaFin().compareTo(d22)==0){
                    reales21al25++;
                }
                if(t.getFechaFin().compareTo(d23)==0){
                    reales21al25++;
                }
                if(t.getFechaFin().compareTo(d24)==0){
                    reales21al25++;
                }
                if(t.getFechaFin().compareTo(d25)==0){
                    reales21al25++;
                }       
                if(t.getFechaFin().compareTo(d26)==0){
                    reales26al30++;
                }
                if(t.getFechaFin().compareTo(d27)==0){
                    reales26al30++;
                }
                if(t.getFechaFin().compareTo(d28)==0){
                    reales26al30++;
                }
                if(t.getFechaFin().compareTo(d29)==0){
                    reales26al30++;
                }
                if(t.getFechaFin().compareTo(d30)==0){
                    reales26al30++;
                } 
                
                if(t.getFechaFinEstimado().compareTo(d1)==0){
                    estimadas1al5++;
                }
                if(t.getFechaFinEstimado().compareTo(d2)==0){
                    estimadas1al5++;
                }
                if(t.getFechaFinEstimado().compareTo(d3)==0){
                    estimadas1al5++;
                }
                if(t.getFechaFinEstimado().compareTo(d4)==0){
                    estimadas1al5++;
                }
                if(t.getFechaFinEstimado().compareTo(d5)==0){
                    estimadas1al5++;
                }
                if(t.getFechaFinEstimado().compareTo(d6)==0){
                    estimadas6al10++;
                }
                if(t.getFechaFinEstimado().compareTo(d7)==0){
                    estimadas6al10++;
                }
                if(t.getFechaFinEstimado().compareTo(d8)==0){
                    estimadas6al10++;
                }
                if(t.getFechaFinEstimado().compareTo(d9)==0){
                    estimadas6al10++;
                }
                if(t.getFechaFinEstimado().compareTo(d10)==0){
                    estimadas6al10++;
                }
                if(t.getFechaFinEstimado().compareTo(d11)==0){
                    estimadas11al15++;
                }
                if(t.getFechaFinEstimado().compareTo(d12)==0){
                    estimadas11al15++;
                }
                if(t.getFechaFinEstimado().compareTo(d13)==0){
                    estimadas11al15++;
                }
                if(t.getFechaFinEstimado().compareTo(d14)==0){
                    estimadas11al15++;
                }
                if(t.getFechaFinEstimado().compareTo(d15)==0){
                    estimadas11al15++;
                }       
                if(t.getFechaFinEstimado().compareTo(d16)==0){
                    estimadas16al20++;
                }
                if(t.getFechaFinEstimado().compareTo(d17)==0){
                     estimadas16al20++;
                }
                if(t.getFechaFinEstimado().compareTo(d18)==0){
                     estimadas16al20++;
                }
                if(t.getFechaFinEstimado().compareTo(d19)==0){
                     estimadas16al20++;
                }
                if(t.getFechaFinEstimado().compareTo(d20)==0){
                     estimadas16al20++;
                } 
                if(t.getFechaFinEstimado().compareTo(d21)==0){
                     estimadas21al25++;
                }
                if(t.getFechaFinEstimado().compareTo(d22)==0){
                    estimadas21al25++;
                }
                if(t.getFechaFinEstimado().compareTo(d23)==0){
                    estimadas21al25++;
                }
                if(t.getFechaFinEstimado().compareTo(d24)==0){
                    estimadas21al25++;
                }
                if(t.getFechaFinEstimado().compareTo(d25)==0){
                    estimadas21al25++;
                }       
                if(t.getFechaFinEstimado().compareTo(d26)==0){
                    estimadas26al30++;
                }
                if(t.getFechaFinEstimado().compareTo(d27)==0){
                    estimadas26al30++;
                }
                if(t.getFechaFinEstimado().compareTo(d28)==0){
                    estimadas26al30++;
                }
                if(t.getFechaFinEstimado().compareTo(d29)==0){
                    estimadas26al30++;
                }
                if(t.getFechaFinEstimado().compareTo(d30)==0){
                    estimadas26al30++;
                } 
            }
        }
        
        //dibujar
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> values = new ArrayList<>();
        values.add(reales1al5);
        values.add(reales6al10);
        values.add(reales11al15);
        values.add(reales16al20);
        values.add(reales21al25);
        values.add(reales26al30);
        dataSet.setData(values);
        dataSet.setLabel("Tiempo real");
        dataSet.setYaxisID("left-y-axis");
        dataSet.setBorderColor("YELLOW");

        LineChartDataSet dataSet2 = new LineChartDataSet();
        List<Object> values2 = new ArrayList<>();
        values2.add(estimadas1al5);
        values2.add(estimadas6al10);
        values2.add(estimadas11al15);
        values2.add(estimadas16al20);
        values2.add(estimadas21al25);
        values2.add(estimadas26al30);
        dataSet.setData(values);
        dataSet2.setData(values2);
        dataSet2.setLabel("Tiempo Estimado");
        dataSet2.setYaxisID("right-y-axis");
        dataSet2.setBorderColor("GREEN");

        data.addChartDataSet(dataSet);
        data.addChartDataSet(dataSet2);

        List<String> labels = new ArrayList<>();
        labels.add("1-5");
        labels.add("6-10");
        labels.add("11-15");
        labels.add("15-20");
        labels.add("20-15");
        labels.add("25-30");
        data.setLabels(labels);
        cartesianLinerModel.setData(data);

        //Options
        LineChartOptions options = new LineChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setId("left-y-axis");
        linearAxes.setPosition("left");
        CartesianLinearAxes linearAxes2 = new CartesianLinearAxes();
        linearAxes2.setId("right-y-axis");
        linearAxes2.setPosition("right");

        cScales.addYAxesData(linearAxes);
        cScales.addYAxesData(linearAxes2);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Tiempo estimado vs Tiempo real");
        options.setTitle(title);

        cartesianLinerModel.setOptions(options);
    }
    
    // Consolidado
    public void createDonutModelConsolidado() {
        
        List<Incidente> listaIncidente = new ArrayList<>();   
        listaIncidente = incidenteEJB.findAll();
        
        int noIniciado = 0;
        int iniciado = 0;
        int enProgreso = 0;
        int retrasado = 0;
        int cancelado = 0;
        int finalizado = 0;
        int cerrado = 0;
        
        for (Incidente i:listaIncidente) {
            if ( i != null){
                if(("No iniciado").equals(i.getIdEstado().getDescripcion())) {
                    noIniciado=noIniciado+1;
                }
                if(("Iniciado").equals(i.getIdEstado().getDescripcion())){
                    iniciado=iniciado+1;
                }
                if(("En Progreso").equals(i.getIdEstado().getDescripcion())){
                    enProgreso=enProgreso+1;
                }
                if(("Retrasado").equals(i.getIdEstado().getDescripcion())){
                    retrasado=retrasado+1;
                }
                if(("Cancelado").equals(i.getIdEstado().getDescripcion())){
                    cancelado=cancelado+1;
                }
                if(("Finalizado").equals(i.getIdEstado().getDescripcion())){
                    finalizado=finalizado+1;
                }
                if(("Cerrado").equals(i.getIdEstado().getDescripcion())){
                    cerrado=cerrado+1;
                }
            }
        }
        
        //dibujar
        donutModelConsolidado = new DonutChartModel();
        ChartData data = new ChartData();

        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(noIniciado);
        values.add(iniciado);
        values.add(enProgreso);
        values.add(retrasado);
        values.add(cancelado);
        values.add(finalizado);
        values.add(cerrado);        
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(92, 192, 192)"); //gris
        bgColors.add("rgb(54, 162, 235)"); //azul
        bgColors.add("rgb(255, 205, 86)"); //amarillo
        bgColors.add("rgb(255, 99, 132)"); //rojo
        bgColors.add("rgb(128, 0, 0))"); //marron
        bgColors.add("rgb(128, 128, 0)"); //verder oscuro
        bgColors.add("rgb(132, 255, 99)"); //verde 
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("noIniciado/s");
        labels.add("Iniciado/s");
        labels.add("En Progreso");
        labels.add("Retrasado/s");
        labels.add("Cancelado/s");
        labels.add("Finalizado/s");
        labels.add("Cerrado/s");
        data.setLabels(labels);

        donutModelConsolidado.setData(data);
    }
     
}
