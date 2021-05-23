package model;

import java.sql.Date;
import java.util.List;
import org.jooq.impl.DSL;
import utilities.Globals;

public class Envios {
  public static class Envio {
    public Integer id;
    public Date fecha_registro;
    public String metodo_pago;
    public Integer costo;
    public Boolean seguro;
    public Integer impuesto_envio;
    public String direccion_entrega;
    public Integer id_sede;
    public String emp_entrega;
    public Boolean delivered;
    public String cliente_envio;
    public String cliente_recogida;
  }

  public static Integer createEnvio(Date fecha_registro, String metodo_pago, Integer costo, Boolean seguro, Integer impuesto_envio, String direccion_entrega, Integer id_sede, String emp_entrega, String cliente_envio,
      String cliente_recogida) {
    String sql = "insert into envio(fecha_registro, metodo_pago, costo, seguro, impuesto_envio, direccion_entrega, id_sede, emp_entrega, delivered, cliente_envio, cliente_recogida) values('" + fecha_registro + "', '" + metodo_pago + "', "
        + costo + ", " + seguro + ", " + impuesto_envio + ", '" + direccion_entrega + "', " + id_sede + ", '" + emp_entrega + "', FALSE, '" + cliente_envio + "', '" + cliente_recogida + "')";
    Globals.db().fetch(sql);

    sql = "select * from envio where fecha_registro='" + fecha_registro + "' and metodo_pago='" + metodo_pago + "' and costo=" + costo + " and impuesto_envio=" + impuesto_envio + " and direccion_entrega='" + direccion_entrega
        + "' and id_sede=" + id_sede + " and emp_entrega='" + emp_entrega + "' and cliente_envio='" + cliente_envio + "' and cliente_recogida='" + cliente_recogida + "'";
    List<Envios.Envio> query = Globals.db().fetch(sql).into(Envio.class);
    Globals.closeConnection();

    return query.get(0).id;
  }
}
