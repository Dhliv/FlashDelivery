package model.Entities;

import java.util.List;
import org.jooq.impl.DSL;

import model.RegistrarEnvio;
import utilities.Conexion;

public class Paquetes {
  public static class Paquete {
    public Integer id;
    public String descripcion;
    public Integer peso;
    public Integer id_envio;
  }

  public static void createPaquete(String desc, Integer peso, Integer id_envio) {
    Conexion.db().insertInto(DSL.table("paquete"), DSL.field("descripcion"), DSL.field("peso"), DSL.field("id_envio")).values(desc, peso, id_envio).execute();
    Conexion.closeConnection();
  }

  public static void createPaquetes(List<RegistrarEnvio.Paquete> p, Integer id_envio) {
    for (int i = 0; i < p.size(); i++) {
      createPaquete(p.get(i).descripcion, p.get(i).peso, id_envio);
    }
  }

}
