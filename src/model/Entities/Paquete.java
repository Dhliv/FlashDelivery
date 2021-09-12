package model.Entities;

import java.util.List;
import org.jooq.impl.DSL;

import model.Envio;
import model.RegistrarEnvio;
import utilities.Conexion;

public class Paquete extends Envio {
  // SQL Fields
  public Integer id;
  public String descripcion;
  public Integer peso;
  public Integer id_envio;
  // Others
  public Dim volumen;
  public Integer valor;
  public Boolean seguro;
  public int valorenvio;
  public int total;

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

  public int getValor() {
    return valor;
  }

  public int getVolumen() {
    return volumen.volumen();
  }

  public int getValorenvio() {
    return valorenvio;
  }

  public int getTotal() {
    return total;
  }

  public static void createPaquete(String desc, Integer peso, Integer id_envio) {
    Conexion.db().insertInto(DSL.table("paquete"), DSL.field("descripcion"), DSL.field("peso"), DSL.field("id_envio"))
        .values(desc, peso, id_envio).execute();
    Conexion.closeConnection();
  }

  public static void createPaquetes(List<Paquete> p, Integer id_envio) {
    for (int i = 0; i < p.size(); i++) {
      createPaquete(p.get(i).descripcion, p.get(i).peso, id_envio);
    }
  }

  public static List<Paquete> queryPaquetesSede(Integer id_sede) {
    List<Paquete> pq = Conexion.db().select().from("paquete").innerJoin("envio").on("paquete.id_envio = envio.id")
        .where("id_sede = " + id_sede).fetch().into(Paquete.class);
    return pq;
  }

  public static class Dim {
    public Integer alto, ancho, largo;

    public Integer volumen() {
      return alto * ancho * largo;
    }
  }
}
