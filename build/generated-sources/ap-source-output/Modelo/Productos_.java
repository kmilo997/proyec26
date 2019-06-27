package Modelo;

import Modelo.Articulos;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-26T21:53:59")
@StaticMetamodel(Productos.class)
public class Productos_ { 

    public static volatile SingularAttribute<Productos, Date> createdAt;
    public static volatile SingularAttribute<Productos, String> estado;
    public static volatile SingularAttribute<Productos, Date> deletedAt;
    public static volatile SingularAttribute<Productos, String> descripcionProducto;
    public static volatile SingularAttribute<Productos, Long> idProducto;
    public static volatile CollectionAttribute<Productos, Articulos> articulosCollection;
    public static volatile SingularAttribute<Productos, String> nombreProducto;
    public static volatile SingularAttribute<Productos, Date> updatedAt;

}