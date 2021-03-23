package py.com.sigati.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.sigati.entities.Rol;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-03-23T10:51:23")
@StaticMetamodel(Permiso.class)
public class Permiso_ { 

    public static volatile SingularAttribute<Permiso, String> descripcion;
    public static volatile SingularAttribute<Permiso, Integer> id;
    public static volatile ListAttribute<Permiso, Rol> rolList;

}