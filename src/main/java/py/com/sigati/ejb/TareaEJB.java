/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.sigati.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.sigati.entities.Incidente;
import py.com.sigati.entities.Tarea;

/**
 *
 * @author Nelson182py
 */
@Stateless
public class TareaEJB extends AbstractFacade<Tarea> {

    @PersistenceContext(unitName = "com.mycompany_Rollout_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TareaEJB() {
        super(Tarea.class);
    }

    public List<Tarea> findIncidentesDeTareas(Incidente incidenteSeleccionado) {
        List<Tarea> lista = new ArrayList<>();

        try{
            Query q = em.createQuery("Select t from Tarea t where t.idIncidente = :incidente")
                    .setParameter("incidente", incidenteSeleccionado);
            lista = q.getResultList();
                         
        }catch(Exception e){
            
        }
        return lista;
    }
    
}