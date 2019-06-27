package Modelo;

import Modelo.Detallecompra;
import Modelo.Detalleventa;
import Modelo.Productos;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-26T21:53:59")
@StaticMetamodel(Articulos.class)
public class Articulos_ { 

    public static volatile SingularAttribute<Articulos, Long> idArticulo;
    public static volatile SingularAttribute<Articulos, String> estado;
    public static volatile SingularAttribute<Articulos, Integer> porcentajeGanacia;
    public static volatile SingularAttribute<Articulos, Integer> valorVenta;
    public static volatile SingularAttribute<Articulos, String> descripcionArticulo;
    public static volatile SingularAttribute<Articulos, Integer> valorCompra;
    public static volatile CollectionAttribute<Articulos, Detalleventa> detalleventaCollection;
    public static volatile SingularAttribute<Articulos, Integer> stockArticulo;
    public static volatile SingularAttribute<Articulos, Date> createdAt;
    public static volatile SingularAttribute<Articulos, Date> deletedAt;
    public static volatile SingularAttribute<Articulos, Productos> idProducto;
    public static volatile SingularAttribute<Articulos, String> codigoBarras;
    public static volatile CollectionAttribute<Articulos, Detallecompra> detallecompraCollection;
    public static volatile SingularAttribute<Articulos, String> nombreArticulo;
    public static volatile SingularAttribute<Articulos, Date> updatedAt;

}