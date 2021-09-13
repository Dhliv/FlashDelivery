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
  public Boolean seguro;
  public Integer alto, ancho, largo;  
  public Integer valor;
  // Others
  
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
  public Boolean getSeguro(){
    return seguro;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public int getValor() {
    return valor;
  }

  public int getVolumen() {
    return largo*alto*ancho;
  }

  public int getValorenvio() {
    return valorenvio;
  }

  public int getTotal() {
    return total;
  }

  public int getAlto(){
    return alto;
  }
  public int getAncho(){
    return ancho;
  }
  public int getLargo(){
    return largo;
  }
  public static void createPaquete(Paquete p) {
    Conexion.db().insertInto(DSL.table("paquete"), DSL.field("descripcion"), DSL.field("peso"), DSL.field("seguro"),
     DSL.field("alto"), DSL.field("ancho"), DSL.field("largo"), DSL.field("valor"))
        .values(p.getDescripcion(), p.getPeso(), p.getSeguro(), p.getAlto(), p.getAncho(), p.getLargo(), p.getValor()).execute();
    Conexion.closeConnection();
  }

  /*public static void createPaquetes(List<Paquete> p, Integer id_envio) {
    for (int i = 0; i < p.size(); i++) {
      createPaquete(p.get(i).descripcion, p.get(i).peso, id_envio);
    }
  }*/
  /**
   * @deprecated
   * !TODO NO ESTÁ IMPLEMENTADO DE ACUERDO A LOS CAMBIOS
   * @param id_sede
   * @return
   */
  public static List<Paquete> queryPaquetesSede(Integer id_sede) {
    List<Paquete> pq = Conexion.db().select().from("paquete").innerJoin("envio").on("paquete.id_envio = envio.id")
        .where("id_sede = " + id_sede).fetch().into(Paquete.class);
    return pq;
  }

}
