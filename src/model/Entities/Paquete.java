package model.Entities;

import java.util.List;
import org.jooq.impl.DSL;

import utilities.Conexion;
import utilities.Globals;

public class Paquete {
  // SQL Fields
  public int id;
  public Double valor;
  public Double valorenvio;
  public Double total;
  public Double peso;
  public Double alto, ancho, largo;
  public String descripcion;
  public boolean seguro;
  // Others

  public int getIdpaquete() {
    return id;
  }

  public Double getPeso() {
    return Globals.roundAvoid(peso, 2);
  }

  public Boolean getSeguro() {
    return seguro;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public Double getValor() {
    return valor;
  }

  public Double getVolumen() {
    return Globals.roundAvoid(largo * alto * ancho, 2);
  }

  public Double getValorenvio() {
    return valorenvio;
  }

  public Double getTotal() {
    return Globals.roundAvoid(total, 2);
  }

  public Double getAlto() {
    return Globals.roundAvoid(alto, 2);
  }

  public Double getAncho() {
    return Globals.roundAvoid(ancho, 2);
  }

  public Double getLargo() {
    return Globals.roundAvoid(largo, 2);
  }

  public static void createPaquete(Paquete p, int id_envio) {
    Conexion.db().insertInto(DSL.table("paquete"), DSL.field("id"), DSL.field("descripcion"), DSL.field("peso"), DSL.field("seguro"), DSL.field("alto"), DSL.field("ancho"), DSL.field("largo"), DSL.field("valor"))
        .values(id_envio, p.getDescripcion(), p.getPeso(), p.getSeguro(), p.getAlto(), p.getAncho(), p.getLargo(), p.getValor()).execute();
    Conexion.closeConnection();
  }
}
