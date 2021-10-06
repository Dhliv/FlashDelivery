package model;

import model.Entities.Cliente;
import model.Entities.Paquete;

/**
 * Clase encargada de almacenar todos los datos en memoria para registrar un
 * envio y posteriormente guardarlos en la base de datos.
 * 
 * @author Julián Orejuela
 * @version 1.1, 23/9/2021
 */
public class RegistrarEnvio {
  private Cliente remitente;
  private Cliente destinatario;
  private Paquete paquete;

  public RegistrarEnvio() {
    remitente = null;
    destinatario = null;
    paquete = null;
  }

  /**
   * Busca si un cliente ya se encuentra registrado en la base de datos.
   * 
   * @param cedula
   * @param tipo
   * @return El cliente o {@code null} si no existe.
   */
  public synchronized Cliente buscarCliente(String cedula, TipoCliente tipo) {
    Cliente cliente = Cliente.buscarCliente(cedula);
    if (tipo == TipoCliente.Remitente)
      remitente = cliente;
    if (tipo == TipoCliente.Destinatario)
      destinatario = cliente;
    return cliente;
  }

  /**
   * Modifica el estado del cliente.
   */
  public void setCliente(String cedula, String nombre, String ciudad, String direccion, String telefono,
      TipoCliente tipo) {
    Cliente cliente = new Cliente(cedula, nombre, ciudad, direccion, telefono);
    if (tipo == TipoCliente.Destinatario)
      destinatario = cliente;
    else
      remitente = cliente;
  }

  public void agregarPaqueteP(Double peso, Double valor, String descripcion, Double ancho, Double largo, Double alto,
      Boolean seguro) {
    if (paquete == null)
      this.paquete = new Paquete();
    paquete.peso = peso;
    paquete.valor = valor;
    paquete.descripcion = descripcion;
    paquete.ancho = ancho;
    paquete.largo = largo;
    paquete.alto = alto;
    paquete.seguro = seguro;

  }

  public void eliminarPaquete() {
    paquete = null;
  }

  public Double getCost(Double peso, Double volumen) { // añadir parametros int ciudadOrigen, int ciudadDestino
    return (volumen * Pago.ValorCM3 + peso * Pago.ValorKG);
  }

  public Double getTotal(Double costo, Double valor_declarado, Boolean seguro) {
    return (costo + (int) (costo * Pago.IMPUESTO) + (seguro ? (valor_declarado * Pago.SEGURO) : 0.0));
  }

  // #---------------------------------------------------------------------------
  // # FUNCIONES AUXILIARES
  // #---------------------------------------------------------------------------

  public Cliente getRemitente() {
    return remitente;
  }

  public Cliente getDestinatario() {
    return destinatario;
  }

  public Paquete getPaquete() {
    return paquete;
  }

  // #---------------------------------------------------------------------------
  // # ENUMS
  // #---------------------------------------------------------------------------

  public enum TipoCliente {
    Remitente, Destinatario
  }

}
