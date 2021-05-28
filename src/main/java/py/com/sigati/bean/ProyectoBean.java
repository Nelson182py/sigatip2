package py.com.sigati.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.PrimeFaces;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import py.com.sigati.ejb.ProyectoEJB;
import py.com.sigati.entities.Entregable;
import py.com.sigati.entities.Proyecto;
import py.com.sigati.entities.Tarea;
import py.com.sigati.entities.Usuario;

/**
 *
 * @author Juanhi
 */
@ManagedBean
@SessionScoped
public class ProyectoBean extends AbstractBean implements Serializable {

    private List<Proyecto> listaProyecto = new ArrayList<>();
    private List<Proyecto> listaProyectosActivos;
    private Proyecto proyectoSeleccionado;
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
    private DonutChartModel donutModel;
    private DonutChartModel donutModel2;

    @EJB
    private ProyectoEJB proyectoEJB;

    @PostConstruct
    public void init() {
        proyectoSeleccionado = new Proyecto();
        listaProyecto = proyectoEJB.findAll();
         //createDonutModel();
         donutModel = new DonutChartModel();
         donutModel2 = new DonutChartModel();
    }

    @Override
    public void resetearValores() {
        proyectoSeleccionado = new Proyecto();
        editando = false;
    }

    @Override
    public void inicializarListas() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        try {
            proyectoEJB.create(proyectoSeleccionado);
            infoMessage("Se guardó correctamente.");
            listaProyecto = proyectoEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbProyectos').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void antesActualizar() {
        editando = true;
        listaProyecto = proyectoEJB.findAll();
    }
    
    public void actualizarGrafico() {
        createDonutModel();
    }
    
    public void actualizarGrafico2() {
        createDonutModel2();
    }
    
    @Override
    public void actualizar() {
        try {
            proyectoEJB.edit(proyectoSeleccionado);
            infoMessage("Se actualizó correctamente.");
            listaProyecto = proyectoEJB.findAll();
            resetearValores();
            PrimeFaces.current().executeScript("PF('wbProyectos').hide()");
        } catch (Exception e) {
            errorMessage("Se produjo un error.");
        }
    }

    @Override
    public void eliminar() {
        try {
            proyectoEJB.remove(proyectoSeleccionado);
            infoMessage("Eliminado correctamente");
           listaProyecto = proyectoEJB.findAll();
        } catch (Exception e) {
            errorMessage("No se pudo eliminar el registro");
        }

    }

    public void agregar() {
        resetearValores();
        listaProyecto = proyectoEJB.findAll();
    }

    public List<Proyecto> getListaProyecto() {
        return listaProyecto;
    }

    public void setListaProyecto(List<Proyecto> listaProyecto) {
        this.listaProyecto = listaProyecto;
    }

   public DonutChartModel getDonutModel() {
        return donutModel;
    }

    public void setDonutModel(DonutChartModel donutModel) {
        this.donutModel = donutModel;
    }
    
    //grafico de horas consumidas
    public void createDonutModel() {
        donutModel = new DonutChartModel();
        ChartData data = new ChartData();    
        int totalHorasEstimadas=0;
        int totalHorasConsumidas=0;
        
        //obtener una tareaa seleccionada
        List<Tarea> listaTarea = new ArrayList<>();  
        List<Entregable> listaEntregable= new ArrayList<>();  
        
        listaEntregable= proyectoSeleccionado.getEntregableList();
        
        //calcular el porcentaje        
        for (Entregable e:listaEntregable) {
            listaTarea=e.getTareaList();
            for (Tarea t:listaTarea) {
                if (t != null){
                    totalHorasEstimadas=totalHorasEstimadas+t.getHorasEstimadas();
                    totalHorasConsumidas=totalHorasConsumidas+t.getHoras();
                }
            }
        }
        
        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(totalHorasEstimadas);
        values.add(totalHorasConsumidas);
        //values.add(0);
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        //bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Consumidas");
        labels.add("Disponibles");
        //labels.add("Pendientes");
        data.setLabels(labels);

        donutModel.setData(data);
    }

    //Grafico de tareas terminadas
    public void createDonutModel2() {
        donutModel2 = new DonutChartModel();
        ChartData data = new ChartData();    
        int totalTareasTotales=0;
        int totalTareasNoIniciadas=0;
        int totalTareasEnProgreso=0;
        int totalTareasFinalizadas=0;
        
        //obtener una tareaa seleccionada
        List<Tarea> listaTarea = new ArrayList<>();  
        List<Entregable> listaEntregable= new ArrayList<>();  
        
        listaEntregable= proyectoSeleccionado.getEntregableList();
        
        //calcular el porcentaje        
        for (Entregable e:listaEntregable) {
            listaTarea=e.getTareaList();
            for (Tarea t:listaTarea) {
                if (t != null){
                    if(t.getIdEstado().getDescripcion().compareTo("No iniciado")==0){
                        totalTareasNoIniciadas++;
                    }
                    if(t.getIdEstado().getDescripcion().compareTo("En progreso")==0){
                        totalTareasEnProgreso++;
                    }
                    if(t.getIdEstado().getDescripcion().compareTo("Finalizado")==0){
                        totalTareasFinalizadas++;
                    }
                    totalTareasTotales++;
                }
            }
        }
        
        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(totalTareasNoIniciadas);
        values.add(totalTareasEnProgreso);
        values.add(totalTareasFinalizadas);
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("No iniciadas");
        labels.add("En Progreso");
        labels.add("Finalizadas");
        data.setLabels(labels);

        donutModel2.setData(data);
    }
    
    public Proyecto getProyectoSeleccionado() {
        return proyectoSeleccionado;
    }

    public void setProyectoSeleccionado(Proyecto proyectoSeleccionado) {
        this.proyectoSeleccionado = proyectoSeleccionado;
    }

    public boolean isEditando() {
        return editando;
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }
    
    public boolean agregarProyecto(){
         Usuario u =  loginBean.getUsuarioLogueado();
        
        if (u != null){
           if( u.getIdRol().getDescripcion().equals(pM)
             || u.getIdRol().getDescripcion().equals(admin)){
               
               return true;
           }            
        }
        return false;      
    }
     
    public boolean editarProyecto() {
        Usuario u = loginBean.getUsuarioLogueado();

        if (u != null) {
            if (u.getIdRol().getDescripcion().equals(pM)
                    || u.getIdRol().getDescripcion().equals(admin)) {

                return true;
            }
        }
        return false;
    }
    
    public List<Proyecto> getListaProyectoActivos() {
        listaProyectosActivos = new ArrayList<>();
        listaProyecto = proyectoEJB.findAll();
         for (Proyecto  p:listaProyecto) {
            if(p != null){
                if(p.getIdEstado().getDescripcion().equals("Iniciado") || 
                        p.getIdEstado().getDescripcion().equals("En curso")){
                    listaProyectosActivos.add(p);
                }       
            }
         }    
        return listaProyectosActivos;
    }
    
    public DonutChartModel getDonutModel2() {
        return donutModel2;
    }

    public void setDonutModel2(DonutChartModel donutModel2) {
        this.donutModel2 = donutModel2;
    }
    
    
}


