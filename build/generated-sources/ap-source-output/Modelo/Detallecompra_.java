package Modelo;

import Modelo.Articulos;
import Modelo.Compras;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-26T21:53:59")
@StaticMetamodel(Detallecompra.class)
public class Detallecompra_ { 

    public static volatile SingularAttribute<Detallecompra, Articulos> idArticulo;
    public static volatile SingularAttribute<Detallecompra, Date> createdAt;
    public static volatile SingularAttribute<Detallecompra, Date> deletedAt;
    public static volatile SingularAttribute<Detallecompra, Integer> valorTotal;
    public static volatile SingularAttribute<Detallecompra, Compras> idCompra;
    public static volatile SingularAttribute<Detallecompra, Long> idDetalleCompra;
    public static volatile SingularAttribute<Detallecompra, Integer> cantidad;
    public static volatile SingularAttribute<Detallecompra, Integer> valorUnitario;
    public static volatile SingularAttribute<Detallecompra, Date> updatedAt;

}