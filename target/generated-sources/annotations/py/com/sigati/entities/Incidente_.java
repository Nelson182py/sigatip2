package py.com.sigati.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.sigati.entities.Estado;
import py.com.sigati.entities.Prioridad;
import py.com.sigati.entities.Segmento;
import py.com.sigati.entities.Servicio;
import py.com.sigati.entities.Tarea;
import py.com.sigati.entities.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-03-23T10:51:23")
@StaticMetamodel(Incidente.class)
public class Incidente_ { 

    public static volatile SingularAttribute<Incidente, String> descripcion;
    public static volatile SingularAttribute<Incidente, String> estado;
    public static volatile SingularAttribute<Incidente, Date> fechaFinEstimado;
    public static volatile SingularAttribute<Incidente, String> nombre;
    public static volatile SingularAttribute<Incidente, Date> fechaFin;
    public static volatile SingularAttribute<Incidente, String> prioridad;
    public static volatile ListAttribute<Incidente, Tarea> tareaList;
    public static volatile SingularAttribute<Incidente, String> complejidad;
    public static volatile SingularAttribute<Incidente, Date> fechaInicioEstimado;
    public static volatile SingularAttribute<Incidente, Estado> idEstado;
    public static volatile SingularAttribute<Incidente, Segmento> idSegmento;
    public static volatile SingularAttribute<Incidente, Date> fechaInicio;
    public static volatile SingularAttribute<Incidente, Integer> id;
    public static volatile SingularAttribute<Incidente, Prioridad> idPrioridad;
    public static volatile SingularAttribute<Incidente, Servicio> idServicio;
    public static volatile SingularAttribute<Incidente, Usuario> idReportador;

}