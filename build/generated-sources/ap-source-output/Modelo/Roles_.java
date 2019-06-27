package Modelo;

import Modelo.Usuariosistema;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-26T21:53:59")
@StaticMetamodel(Roles.class)
public class Roles_ { 

    public static volatile SingularAttribute<Roles, Long> idRol;
    public static volatile SingularAttribute<Roles, String> descripcionRol;
    public static volatile SingularAttribute<Roles, String> nombreRol;
    public static volatile CollectionAttribute<Roles, Usuariosistema> usuariosistemaCollection;

}