package model;

import java.sql.Date;

import model.Entities.Cliente;
import model.Entities.Paquete;
import model.Entities.Paquete.Dim;

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
  private List<Paquete> paquetes;

  public RegistrarEnvio() {
    remitente = null;
    destinatario = null;
    paquetes = new ArrayList<>();
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

  public void agregarPaqueteP(Paquete p) {
    paquetes.add(p);
  }

  public Paquete agregarPaquete(Integer peso, Integer valor, String descripcion, Integer ancho, Integer largo, Integer alto, Boolean seguro, int index) {
    Paquete p = new Paquete();
    p.descripcion = descripcion;
    p.peso = peso;
    p.valor = valor;
    Dim d = new Dim();
    d.alto = alto;
    d.largo = largo;
    d.ancho = ancho;
    p.volumen = d;
    p.seguro = seguro;

    if (index == -1)
      paquetes.add(p);
    else
      paquetes.add(index, p);
    return p;
  }

  public void editarPaquete(int index, Paquete p) {
    paquetes.get(index).descripcion = p.descripcion;
    paquetes.get(index).peso = p.peso;
    paquetes.get(index).volumen = p.volumen;
    paquetes.get(index).valor = p.valor;
  }

  public void eliminarPaquete(int index) {
    paquetes.remove(index);
  }

  public List<Paquete> getPaquetes() {
    return paquetes;
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

  public boolean checkClientes() {
    return !(remitente == null || destinatario == null);
  }

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

}
