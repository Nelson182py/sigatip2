package py.com.sigati.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.sigati.entities.Entregable;
import py.com.sigati.entities.Incidente;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-02-26T22:38:51")
@StaticMetamodel(Servicio.class)
public class Servicio_ { 

    public static volatile SingularAttribute<Servicio, String> descripcion;
    public static volatile ListAttribute<Servicio, Entregable> entregableList;
    public static volatile ListAttribute<Servicio, Incidente> incidenteList;
    public static volatile SingularAttribute<Servicio, Integer> id;

}