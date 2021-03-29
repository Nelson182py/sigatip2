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
public class PermisoEJB extends AbstractFacade<Permiso> {

    @PersistenceContext(unitName = "com.mycompany_Rollout_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermisoEJB() {
        super(Permiso.class);
    }
    
}
