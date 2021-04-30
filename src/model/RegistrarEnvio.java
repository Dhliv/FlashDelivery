package model;

import org.jooq.Result;
import org.jooq.impl.*;
import java.sql.Date;
import org.jooq.Record;
import java.util.List;
import java.util.ArrayList;

import static utilities.Globals.db;
import utilities.Globals;
import model.Clientes.Cliente;

/**
 * Clase encargada de almacenar todos los datos para registrar un envio y
 * posteriormente guardarlos en la base de datos.
 * @author Julián Orejuela
 * @version 1.0, 29/4/2021
 */
public class RegistrarEnvio {
  private Cliente remitente;
  private Cliente destinatario;
  private List<Paquete> paquetes;

  public RegistrarEnvio() {
    paquetes = new ArrayList<>();
  }

  /**
   * Busca si un cliente ya se encuentra registrado en la base de datos.
   * @param cedula
   * @param tipo
   * @return El cliente o {@code null} si no existe.
   */
  public Cliente buscarCliente(String cedula, TipoCliente tipo) {
    Record rs = db().select().from("cliente").where("cedula='" + cedula + "'").fetchOne();
    Cliente cliente = rs != null ? rs.into(Cliente.class) : null;
    if (tipo == TipoCliente.Remitente) remitente = cliente;
    if (tipo == TipoCliente.Destinatario) destinatario = cliente;
    Globals.closeConnection();
    return cliente;
  }

  /**
   * Modifica el estado del cliente.
   */
  public void setCliente(String cedula, String nombre, String direccion, String telefono, TipoCliente tipo) {
    Cliente cliente = tipo == TipoCliente.Remitente ? remitente : destinatario;
    if (cliente == null) cliente = new Cliente();
    cliente.cedula = cedula;
    cliente.nombre = nombre;
    cliente.direccion = direccion;
    cliente.telefono = telefono;
  }

  public void agregarPaquete(Integer peso, Integer valor, String descripcion, Integer ancho, Integer largo, Integer alto) {

  }

  public void editarPaquete() {

  }

  public void eliminarPaquete() {

  }

  public Integer getCost(Integer peso, Integer volumen, Integer valor, int ciudadOrigen, int ciudadDestino) {
    Integer cost = 0;

    return cost;
  }

  // #---------------------------------------------------------------------------
  // # AUXILIARES
  // #---------------------------------------------------------------------------

  // #---------------------------------------------------------------------------
  // # ENUMS
  // #---------------------------------------------------------------------------

  public enum TipoCliente {
    Remitente, Destinatario
  }

  // #---------------------------------------------------------------------------
  // # POJOs
  // #---------------------------------------------------------------------------

  public static class Envio {
    public Integer ID_Envio;
    public Date Fecha_registro;
    public String Metodo_pago;
    public Double Costo;
    public Boolean Seguro;
    public Double Impuesto_envio;
    public String Direccion_entrega;
    public Integer ID_Sede;
    public Integer Emp_Entrega;
    public Boolean Delivered;
    public String Cliente_Envio;
    public String Cliente_Recogida;
  }

  public static class Paquete {
    public String descripcion;
    public Integer peso;
    public Dim volumen;
    public Integer valor;
    public Integer id_envio;
  }

  public static class Dim {
    public Integer alto, ancho, largo;
  }
}
