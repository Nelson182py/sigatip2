package py.com.sigati.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import py.com.sigati.entities.Proyecto;

/**
 *
 * @author Nelson182py
 */
@Stateless
public class ProyectoEJB extends AbstractFacade<Proyecto> {

    @PersistenceContext(unitName = "com.mycompany_Rollout_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProyectoEJB() {
        super(Proyecto.class);
    }


    
}