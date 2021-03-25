/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.sigati.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.com.sigati.entities.Permiso;
import py.com.sigati.entities.Rol;
import py.com.sigati.entities.RolPermiso;
import py.com.sigati.ejb.RolPermisoEJB;

/**
 *
 * @author Nelson182py
 */
@Stateless
public class RolEJB extends AbstractFacade<Rol> {

    @EJB
    RolPermisoEJB rolPermisoEJB;
    
    @PersistenceContext(unitName = "com.mycompany_Rollout_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolEJB() {
        super(Rol.class);
    }
    
    public void create(Rol rol,List<Permiso> permisos){
        
        for(Permiso p:permisos){
            RolPermiso rolPermiso = new RolPermiso();
            rolPermiso.setIdPermiso(p);
            rolPermiso.setIdRol(rol);
            rolPermisoEJB.create(rolPermiso);
            
        }
        em.persist(rol);
        
    }

    public List<Permiso> findPermisos(Rol rolSeleccionado) {
        List<Permiso> lista = new ArrayList<>();
        List<RolPermiso> listaAux = new ArrayList<>();
        try{
            Query q = em.createQuery("Select r from RolPermiso r where r.idRol = :rol")
                    .setParameter("rol", rolSeleccionado);
            listaAux = q.getResultList();
            for(RolPermiso rp : listaAux){
                lista.add(rp.getIdPermiso());
            }
            
                    
        }catch(Exception e){
            
        }
        return lista;
    }
    
}
