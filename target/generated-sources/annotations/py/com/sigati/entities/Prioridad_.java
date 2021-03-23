package py.com.sigati.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.sigati.entities.Entregable;
import py.com.sigati.entities.Incidente;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-03-23T10:51:23")
@StaticMetamodel(Prioridad.class)
public class Prioridad_ { 

    public static volatile SingularAttribute<Prioridad, String> descripcion;
    public static volatile ListAttribute<Prioridad, Entregable> entregableList;
    public static volatile ListAttribute<Prioridad, Incidente> incidenteList;
    public static volatile SingularAttribute<Prioridad, Integer> id;

}