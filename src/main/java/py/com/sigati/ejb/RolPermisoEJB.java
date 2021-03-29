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
import py.com.sigati.entities.Permiso;
import py.com.sigati.entities.Rol;
import py.com.sigati.entities.RolPermiso;

/**
 *
 * @author Nelson182py
 */
@Stateless
public class RolPermisoEJB extends AbstractFacade<RolPermiso> {

    @PersistenceContext(unitName = "com.mycompany_Rollout_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolPermiso obtenerRolPermiso(Rol rol, Permiso permiso) {
        RolPermiso m = null;
        try {
            Query q = em.createQuery("Select m from RolPermiso m where m.idRol = :rol and m.idPermiso = :permiso", RolPermiso.class)
                    .setParameter("rol", rol)
                    .setParameter("permiso", permiso);
            m = (RolPermiso) q.getSingleResult();
        } catch (Exception e) {

        }

        return m;

    }
    
    public List<RolPermiso> obtenerListaRolPermiso(Rol rol) {
        List<RolPermiso> menuSubmenuList = new ArrayList<>();
        try {
            Query q = em.createQuery("Select m from RolPermiso m where m.idRol = :rol")
                    .setParameter("rol", rol);
                    
            menuSubmenuList =  q.getResultList();
        } catch (Exception e) {

        }

        return menuSubmenuList;

    }
    
    public RolPermisoEJB() {
        super(RolPermiso.class);
    }
    
}
