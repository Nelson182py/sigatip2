package py.com.sigati.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.sigati.entities.Ambiente;
import py.com.sigati.entities.Complejidad;
import py.com.sigati.entities.Entregable;
import py.com.sigati.entities.Incidente;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-03-23T10:51:23")
@StaticMetamodel(Tarea.class)
public class Tarea_ { 

    public static volatile SingularAttribute<Tarea, String> descripcion;
    public static volatile SingularAttribute<Tarea, Incidente> idIncidente;
    public static volatile SingularAttribute<Tarea, Date> fechaInicioEstimado;
    public static volatile SingularAttribute<Tarea, Date> fechaInicio;
    public static volatile SingularAttribute<Tarea, Ambiente> idAmbiente;
    public static volatile SingularAttribute<Tarea, Complejidad> idComplejidad;
    public static volatile SingularAttribute<Tarea, Date> fechaFinEstimado;
    public static volatile SingularAttribute<Tarea, Entregable> idEntregable;
    public static volatile SingularAttribute<Tarea, Integer> id;
    public static volatile SingularAttribute<Tarea, String> nombre;
    public static volatile SingularAttribute<Tarea, Date> fechaFin;

}