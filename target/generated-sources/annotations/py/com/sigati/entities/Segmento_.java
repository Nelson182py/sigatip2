package py.com.sigati.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.sigati.entities.Incidente;
import py.com.sigati.entities.Proyecto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-03-23T10:51:23")
@StaticMetamodel(Segmento.class)
public class Segmento_ { 

    public static volatile SingularAttribute<Segmento, String> descripcion;
    public static volatile ListAttribute<Segmento, Proyecto> proyectoList;
    public static volatile ListAttribute<Segmento, Incidente> incidenteList;
    public static volatile SingularAttribute<Segmento, Integer> id;

}