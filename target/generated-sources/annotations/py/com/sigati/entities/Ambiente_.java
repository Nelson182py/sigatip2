package py.com.sigati.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.sigati.entities.Tarea;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-03-23T10:51:23")
@StaticMetamodel(Ambiente.class)
public class Ambiente_ { 

    public static volatile SingularAttribute<Ambiente, String> descripcion;
    public static volatile ListAttribute<Ambiente, Tarea> tareaList;
    public static volatile SingularAttribute<Ambiente, Integer> id;

}