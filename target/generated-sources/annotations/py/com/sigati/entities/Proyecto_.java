package py.com.sigati.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.sigati.entities.Entregable;
import py.com.sigati.entities.Estado;
import py.com.sigati.entities.Segmento;
import py.com.sigati.entities.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-03-23T10:51:23")
@StaticMetamodel(Proyecto.class)
public class Proyecto_ { 

    public static volatile SingularAttribute<Proyecto, String> descripcion;
    public static volatile SingularAttribute<Proyecto, Date> fechaInicioEstimado;
    public static volatile SingularAttribute<Proyecto, Estado> idEstado;
    public static volatile SingularAttribute<Proyecto, Segmento> idSegmento;
    public static volatile SingularAttribute<Proyecto, Date> fechaInicio;
    public static volatile ListAttribute<Proyecto, Entregable> entregableList;
    public static volatile SingularAttribute<Proyecto, Date> fechaFinEstimado;
    public static volatile SingularAttribute<Proyecto, Usuario> idResponsable;
    public static volatile SingularAttribute<Proyecto, Integer> id;
    public static volatile SingularAttribute<Proyecto, String> nombre;

}