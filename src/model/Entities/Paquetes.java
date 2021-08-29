package model.Entities;

import java.util.List;
import org.jooq.impl.DSL;
import org.jooq.*;
import org.jooq.Record;

import java.util.*;
import model.Envios;
import model.Envios.Envio;
import model.RegistrarEnvio;
import utilities.Conexion;

public class Paquetes {
  public static class Paquete extends Envio {
    public Integer id;
    public String descripcion;
    public Integer peso;
    public Integer id_envio;

    public Integer getIdpaquete() {
      return id;
    }

    public String getDestinatario() {
      return cliente_recogida;
    }

    public Integer getPeso() {
      return peso;
    }

    public Boolean getEstado() {
      return delivered;
    }

    public String getDescripcion() {
      return descripcion;
    }
  }

  public static void createPaquete(String desc, Integer peso, Integer id_envio) {
    Conexion.db().insertInto(DSL.table("paquete"), DSL.field("descripcion"), DSL.field("peso"), DSL.field("id_envio"))
        .values(desc, peso, id_envio).execute();
    Conexion.closeConnection();
  }

  public static void createPaquetes(List<RegistrarEnvio.Paquete> p, Integer id_envio) {
    for (int i = 0; i < p.size(); i++) {
      createPaquete(p.get(i).descripcion, p.get(i).peso, id_envio);
    }
  }

  public static List<Paquete> queryPaquetesSede(Integer id_sede) {
    List<Paquete> pq = Conexion.db().select().from("paquete").innerJoin("envio").on("paquete.id_envio = envio.id")
        .where("id_sede = " + id_sede).fetch().into(Paquete.class);
    return pq;
  }
}
