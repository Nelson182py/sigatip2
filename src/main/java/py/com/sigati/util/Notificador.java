/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.sigati.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import py.com.sigati.ejb.EntregableEJB;
import py.com.sigati.ejb.IncidenteEJB;
import py.com.sigati.ejb.TareaEJB;
import py.com.sigati.entities.Entregable;
import py.com.sigati.entities.Incidente;
import py.com.sigati.entities.Tarea;

/**
 *
 * @author nelson182py
 */
@ManagedBean
@SessionScoped
public class Notificador implements Serializable {
    
    @EJB
    private IncidenteEJB incidenteEJB;
    
    @EJB
    private TareaEJB tareaEJB;

    @EJB
    private EntregableEJB entregableEJB;
    
    private String direccion = "nelson.molinas@gmail.com";
    private String mensaje =   "<h1>La iniciativa se encuentra en estado retrasado";
    private String asunto = "Notificacion SIGATI";
     
    private List<Entregable> listaEntregable = new ArrayList<>();   
    private List<Tarea> listaTarea = new ArrayList<>(); 
    private List<Incidente> listaIncidente = new ArrayList<>(); 
    
    @PostConstruct
    public void init() {
        
        listaEntregable = entregableEJB.findAll();
        listaIncidente = incidenteEJB.findAll();
        listaTarea =tareaEJB.findAll();
    }
    
    public void verificar(){
    
        direccion = "nelson.molinas@gmail.com";
        
        // Verificar estado de Proyectos
        mensaje =   "<h1>El Entregalbe del Proyecto se encuentra en estado retrasado</h1>";
        
        
        //calcular el estado       
        for (Entregable e:listaEntregable) {
            
            if (e != null){
                if(e.getIdEstado().getDescripcion().compareTo("Retrasado")==0){
                    
                    SendMail.enviarCorreo(direccion, mensaje,asunto+" el entregable del proyecto "+e.getNombre()+" se encuentra con retraso ", true);
                }
            }
        }
        
        // Verificar estado de Incidentes
        mensaje =   "<h1>El Incidente se encuentra en estado retrasado </h1>";
         
        //calcular el estado       
        for (Incidente i:listaIncidente) {
            
            if (i != null){
                if(("Retrasado").equals(i.getIdEstado().getDescripcion())){
                    
                    SendMail.enviarCorreo(direccion, mensaje,asunto+" el incidente "+i.getNombre()+" se encuentra con retraso ", true);
                }
            }
        }
        
         // Verificar estado de Entregables
        mensaje =   "<h1>El Entregable se encuentra en estado retrasado en:</h1>";
        
        int totalHorasEstimadas=0;
        int totalHorasConsumidas=0;
        int totalHorasDisponibles=0;

        //calcular el porcentaje        
        for (Tarea t:listaTarea) {
            totalHorasEstimadas=0;
            totalHorasConsumidas=0;
            totalHorasDisponibles=0;
            if (t != null){
                totalHorasEstimadas=totalHorasEstimadas+t.getHorasEstimadas();
                totalHorasConsumidas=totalHorasConsumidas+t.getHoras();
                totalHorasDisponibles=totalHorasEstimadas-totalHorasConsumidas;
                if(totalHorasDisponibles<0){
                    
                    SendMail.enviarCorreo(direccion, mensaje,asunto+" "+t.getIdResponsable().getIdPersona().getNombre()+" la tarea "+t.getNombre()+" se encuentra con retraso ", true);
                }
            }
        } 

    }
    
}
