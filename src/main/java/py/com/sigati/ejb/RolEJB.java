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
    
    @EJB
    PermisoEJB permisoEJB;
    
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

    public void edit2(Rol rol,List<Permiso> permisos){

        String tmp ="";
        tmp=rol.getDescripcion();
        for(Permiso p:permisos){
            RolPermiso rolPermiso = new RolPermiso();
            rolPermiso.setIdPermiso(p);
            rolPermiso.setIdRol(rol);
            rolPermisoEJB.edit(rolPermiso);
            
        }
        em.merge(rol);
        
    }

    public void edit(Rol rol, List<Permiso> permisoList) {
        
        getEntityManager().merge(rol);
        List<RolPermiso> listaMenuSubmenu = rolPermisoEJB.obtenerListaRolPermiso(rol);
        List<Permiso> permisoListAux = new ArrayList<>();
        List<Permiso> aAgregar = new ArrayList<>();
        List<Permiso> aEliminar = new ArrayList<>();
        
        for (RolPermiso m : listaMenuSubmenu) {
            if (!permisoListAux.contains(m.getIdPermiso())) {
                permisoListAux.add(m.getIdPermiso());
            }
        }

        for (Permiso s : permisoListAux) {
            if (!permisoList.contains(s)) {
                aEliminar.add(s);
            }
        }

        for (Permiso s : permisoList) {
            if (!permisoListAux.contains(s)) {
                aAgregar.add(s);
            }
        }

        for (Permiso s : aEliminar) {
            RolPermiso eliminar = rolPermisoEJB.obtenerRolPermiso(rol, s);
            if (eliminar != null) {
                rolPermisoEJB.remove(eliminar);
            }
        }

        for (Permiso s : aAgregar) {
            RolPermiso m = new RolPermiso();
            m.setIdRol(rol);
            m.setIdPermiso(s);
            rolPermisoEJB.create(m);
        }
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
