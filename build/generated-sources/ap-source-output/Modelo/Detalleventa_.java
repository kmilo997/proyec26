package Modelo;

import Modelo.Articulos;
import Modelo.Ventas;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-26T21:53:59")
@StaticMetamodel(Detalleventa.class)
public class Detalleventa_ { 

    public static volatile SingularAttribute<Detalleventa, Articulos> idArticulo;
    public static volatile SingularAttribute<Detalleventa, Date> createdAt;
    public static volatile SingularAttribute<Detalleventa, Date> deletedAt;
    public static volatile SingularAttribute<Detalleventa, Integer> valorTotal;
    public static volatile SingularAttribute<Detalleventa, Long> idDetalleVenta;
    public static volatile SingularAttribute<Detalleventa, Integer> cantidad;
    public static volatile SingularAttribute<Detalleventa, Ventas> idVentas;
    public static volatile SingularAttribute<Detalleventa, Integer> valorUnitario;
    public static volatile SingularAttribute<Detalleventa, Date> updatedAt;

}