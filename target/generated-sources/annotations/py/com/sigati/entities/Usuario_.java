package py.com.sigati.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import py.com.sigati.entities.Area;
import py.com.sigati.entities.Incidente;
import py.com.sigati.entities.Persona;
import py.com.sigati.entities.Proyecto;
import py.com.sigati.entities.Rol;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-03-23T10:51:24")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, Integer> codigoHumano;
    public static volatile SingularAttribute<Usuario, Date> fechaIngreso;
    public static volatile ListAttribute<Usuario, Proyecto> proyectoList;
    public static volatile SingularAttribute<Usuario, Rol> idRol;
    public static volatile SingularAttribute<Usuario, Area> idArea;
    public static volatile SingularAttribute<Usuario, String> correoCorporativo;
    public static volatile ListAttribute<Usuario, Incidente> incidenteList;
    public static volatile SingularAttribute<Usuario, String> celularCorporativo;
    public static volatile SingularAttribute<Usuario, Persona> idPersona;

}