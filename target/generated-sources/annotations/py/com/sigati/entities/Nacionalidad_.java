package py.com.sigati.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.sigati.entities.Persona;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-03-23T10:51:23")
@StaticMetamodel(Nacionalidad.class)
public class Nacionalidad_ { 

    public static volatile SingularAttribute<Nacionalidad, String> descripcion;
    public static volatile ListAttribute<Nacionalidad, Persona> personaList;
    public static volatile SingularAttribute<Nacionalidad, Integer> id;

}