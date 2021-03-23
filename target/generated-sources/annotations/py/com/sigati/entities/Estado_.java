package py.com.sigati.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.sigati.entities.Entregable;
import py.com.sigati.entities.Incidente;
import py.com.sigati.entities.Proyecto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-03-23T10:51:24")
@StaticMetamodel(Estado.class)
public class Estado_ { 

    public static volatile SingularAttribute<Estado, String> descripcion;
    public static volatile ListAttribute<Estado, Proyecto> proyectoList;
    public static volatile ListAttribute<Estado, Entregable> entregableList;
    public static volatile ListAttribute<Estado, Incidente> incidenteList;
    public static volatile SingularAttribute<Estado, Integer> id;

}