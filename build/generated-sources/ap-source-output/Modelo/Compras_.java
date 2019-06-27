package Modelo;

import Modelo.Detallecompra;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-26T21:53:59")
@StaticMetamodel(Compras.class)
public class Compras_ { 

    public static volatile SingularAttribute<Compras, Date> fechaCompra;
    public static volatile SingularAttribute<Compras, Date> createdAt;
    public static volatile SingularAttribute<Compras, Date> deletedAt;
    public static volatile SingularAttribute<Compras, Integer> valorTotalCompra;
    public static volatile SingularAttribute<Compras, Long> idCompra;
    public static volatile CollectionAttribute<Compras, Detallecompra> detallecompraCollection;
    public static volatile SingularAttribute<Compras, Date> updatedAt;

}