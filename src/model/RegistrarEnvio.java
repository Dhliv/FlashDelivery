package model;

import java.sql.Date;

import model.Entities.Cliente;
import model.Entities.Paquete;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;

/**
 * Clase encargada de almacenar todos los datos en memoria para registrar un
 * envio y posteriormente guardarlos en la base de datos.
 * 
 * @author Julián Orejuela
 * @version 1.0, 29/4/2021
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
  public Cliente buscarCliente(String cedula, TipoCliente tipo) {
    Cliente cliente = Cliente.buscarCliente(cedula);
    if (tipo == TipoCliente.Remitente) remitente = cliente;
    if (tipo == TipoCliente.Destinatario) destinatario = cliente;
    return cliente;
  }

  /**
   * Modifica el estado del cliente.
   */
  public void setCliente(String cedula, String nombre, String ciudad, String direccion, String telefono, TipoCliente tipo) {
    Cliente cliente = new Cliente();
    if (cedula.trim().equals("") || nombre.trim().equals("") || ciudad.equals("") || direccion.equals("") || telefono.equals(""))
      cliente = null;
    else {
      cliente.cedula = cedula;
      cliente.nombre = nombre;
      cliente.ciudad = ciudad;
      cliente.direccion = direccion;
      cliente.telefono = telefono;
    }

    if (tipo == TipoCliente.Destinatario) {
      destinatario = cliente;
    } else {
      remitente = cliente;
    }
  }

  public void agregarPaqueteP(Integer peso, Integer valor, String descripcion, Integer ancho, Integer largo, Integer alto, Boolean seguro) {
    if (paquete == null) this.paquete = new Paquete();
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

  public Integer getCost(Integer peso, Integer volumen) { // añadir parametros int ciudadOrigen, int ciudadDestino
    return (int) (volumen * Pago.ValorCM3 + peso * Pago.ValorKG);
  }

  public Integer getTotal(Integer costo, Integer valor_declarado, Boolean seguro) {
    return (int) (costo + (int) (costo * Pago.IMPUESTO) + (seguro ? (valor_declarado * Pago.SEGURO) : 0.0));
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

  public boolean checkClientes() {
    return !(remitente == null || destinatario == null);
  }

  // #---------------------------------------------------------------------------
  // # ENUMS
  // #---------------------------------------------------------------------------

  public enum TipoCliente {
    Remitente, Destinatario
  }

}
