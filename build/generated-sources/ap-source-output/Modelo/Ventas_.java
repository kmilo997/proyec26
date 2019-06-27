package Modelo;

import Modelo.Detalleventa;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-26T21:53:59")
@StaticMetamodel(Ventas.class)
public class Ventas_ { 

    public static volatile SingularAttribute<Ventas, Date> createdAt;
    public static volatile SingularAttribute<Ventas, Date> deletedAt;
    public static volatile SingularAttribute<Ventas, String> estadoVenta;
    public static volatile CollectionAttribute<Ventas, Detalleventa> detalleventaCollection;
    public static volatile SingularAttribute<Ventas, Long> idVentas;
    public static volatile SingularAttribute<Ventas, Integer> valorTotalVenta;
    public static volatile SingularAttribute<Ventas, Date> fechaVenta;
    public static volatile SingularAttribute<Ventas, Date> updatedAt;

}