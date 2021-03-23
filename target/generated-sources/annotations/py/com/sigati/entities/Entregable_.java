package py.com.sigati.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.sigati.entities.Complejidad;
import py.com.sigati.entities.Estado;
import py.com.sigati.entities.Prioridad;
import py.com.sigati.entities.Proyecto;
import py.com.sigati.entities.Servicio;
import py.com.sigati.entities.Tarea;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-03-23T10:51:23")
@StaticMetamodel(Entregable.class)
public class Entregable_ { 

    public static volatile SingularAttribute<Entregable, String> descripcion;
    public static volatile SingularAttribute<Entregable, Proyecto> idProyecto;
    public static volatile SingularAttribute<Entregable, Date> fechaFinEstimado;
    public static volatile SingularAttribute<Entregable, String> nombre;
    public static volatile SingularAttribute<Entregable, Date> fechaFin;
    public static volatile ListAttribute<Entregable, Tarea> tareaList;
    public static volatile SingularAttribute<Entregable, Date> fechaInicioEstimado;
    public static volatile SingularAttribute<Entregable, Estado> idEstado;
    public static volatile SingularAttribute<Entregable, Date> fechaInicio;
    public static volatile SingularAttribute<Entregable, Complejidad> idComplejidad;
    public static volatile SingularAttribute<Entregable, Integer> id;
    public static volatile SingularAttribute<Entregable, Prioridad> idPrioridad;
    public static volatile SingularAttribute<Entregable, Servicio> idServicio;

}