package py.com.sigati.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.sigati.entities.Entregable;
import py.com.sigati.entities.Tarea;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-03-23T10:51:23")
@StaticMetamodel(Complejidad.class)
public class Complejidad_ { 

    public static volatile SingularAttribute<Complejidad, String> descripcion;
    public static volatile ListAttribute<Complejidad, Tarea> tareaList;
    public static volatile ListAttribute<Complejidad, Entregable> entregableList;
    public static volatile SingularAttribute<Complejidad, Integer> id;

}