package model;

import org.jooq.impl.DSL;

import controller.RegistrarEnvio.PaqueteT;
import utilities.Globals;

public class Paquetes {
  public static class Paquete {
    public Integer id;
    public String descripcion;
    public Integer peso;
    public Integer id_envio;
  }

  public static void createPaquete(String desc, Integer peso, Integer id_envio) {
    Globals.db().insertInto(DSL.table("paquete"), DSL.field("descripcion"), DSL.field("peso"), DSL.field("id_envio")).values(desc, peso, id_envio).execute();
    Globals.closeConnection();
  }

  public static void createPaquetes(PaqueteT[] p, Integer id_envio) {
    for (int i = 0; i < p.length; i++) {
      createPaquete(p[i].getDescripcion(), p[i].getPeso(), id_envio);
    }
  }
}
