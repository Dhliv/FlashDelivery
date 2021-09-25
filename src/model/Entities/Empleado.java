package model.Entities;

import java.time.LocalDate;
import java.util.List;
import utilities.Conexion;

/**
 * Clase de Empleado. Se almacenan los parámetros relacionados a los datos de un
 * empleado. Posee métodos para la comunicación con la BD pertinentes al
 * empleado.
 * 
 * @author David Henao
 * @author Reynel Arkad Devji Quevedo
 * @version 1.0
 * @since 24/09/2021
 */
public class Empleado {
  private String cedula;
  private String nombres, apellidos, rol, direccion, telefono;
  private LocalDate birthdate;
  public int id_sede;

  public Empleado() {

  }

  /**
   * Constructor vacío de Empleado. Se conserva para que no haya errores al
   * insertar datos en la clase de Empleado despues de hacer join de Empleado y
   * Usuario.
   */
  public Empleado() {
  }

  /**
   * Constructor de la clase empleado. Se crea directamente un empleado.
   * 
   * @param cedula    del empleado.
   * @param nombres   del empleado.
   * @param apellidos del empleado.
   * @param rol       del empleado.
   * @param direccion del empleado.
   * @param telefono  del empleado.
   * @param birthdate del empleado.
   * @param id_sede   del empleado.
   */
  public Empleado(String cedula, String nombres, String apellidos, String rol, String direccion, String telefono,
      LocalDate birthdate, int id_sede) {
    this.cedula = cedula;
    this.nombres = nombres;
    this.apellidos = apellidos;
    this.rol = rol;
    this.direccion = direccion;
    this.telefono = telefono;
    this.birthdate = birthdate;
    this.id_sede = id_sede;
  }

  public Empleado(String cedula, String nombres, String apellidos, String rol, String direccion, String telefono,
      LocalDate birthdate, int id_sede, int id, String username, String password, boolean enabled) {
    this.cedula = cedula;
    this.nombres = nombres;
    this.apellidos = apellidos;
    this.rol = rol;
    this.direccion = direccion;
    this.telefono = telefono;
    this.birthdate = birthdate;
    this.id_sede = id_sede;
  }

  public String getCedula() {
    return cedula;
  }

  public String getNombres() {
    return nombres;
  }

  public String getApellidos() {
    return apellidos;
  }

  public String getRol() {
    return rol;
  }

  public String getDireccion() {
    return direccion;
  }

  public String getTelefono() {
    return telefono;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public int getSede() {
    return id_sede;
  }

  public void setCedula(String cedula) {
    this.cedula = cedula;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }

  public void setSede(int sede) {
    this.id_sede = sede;
  }

  @Override
  public String toString() {
    return "Empleado{" + "cedula=" + cedula + ", nombres=" + nombres + ", apellidos=" + apellidos + ", rol=" + rol
        + ", direccion=" + direccion + ", telefono=" + telefono + ", birthdate=" + birthdate + ", id_sede=" + id_sede
        + '}';
  }

  /**
   * Obtiene todos los empleados en la base de datos y los retorna.
   * 
   * @return lista de empleados existentes en la BD.
   */
  public static List<Empleado> getEmpleados() {
    List<Empleado> sedes = Conexion.db().select().from("empleado").fetch().into(Empleado.class); // Ejecuto la query
                                                                                                 // 'sql'.
    Conexion.closeConnection();

    return sedes;
  }

  /**
   * Actualiza en la BD los datos del empleado que fueron editados (la cedula no
   * cambia nunca).
   * 
   * @param empleado informacion completa a actualizar (incluso sin cambios).
   */
  public static void updateEmpleado(Empleado empleado) {
    String sql = "update empleado set nombres='" + empleado.getNombres() + "', apellidos='" + empleado.getApellidos()
        + "', rol='" + empleado.getRol() + "', direccion='" + empleado.getDireccion() + "', telefono='"
        + empleado.getTelefono() + "', birthdate='" + empleado.getBirthdate() + "', id_sede=" + empleado.getSede()
        + " where cedula='" + empleado.getCedula() + "'";

    Conexion.db().execute(sql);
  }

  /**
   * El método inserta un empleado en la base de datos.
   * 
   * @param e Empleado a insertar.
   * @return 0 si hubo un error en la inserción del empleado, 1 de lo contrario.
   */
  public static int crearEmpleado(Empleado e) {
    int res = 0;
    try {
      String sql = "insert into empleado VALUES ('" + e.cedula + "', '" + e.getNombres() + "', '" + e.getApellidos()
          + "', '" + e.getRol() + "', '" + e.getDireccion() + "', '" + e.getTelefono() + "', '"
          + e.getBirthdate().toString() + "', " + e.getSede() + ")";
      res = Conexion.db().execute(sql);
    } catch (Exception ex) {
      res = 0;
    }

    return res;
  }

  /**
   * Carga un objeto de tipo Empleado si existe un empleado en la base de datos
   * con la cedula introducida
   * 
   * @param cedula la cedula del empleado a buscar
   * @return un objeto de la clase Empleado si existe en la base de datos, null si
   *         no existe
   */
  public static Empleado cargarEmpleado(String cedula) {

    List<Empleado> empleado = Conexion.db().select().from("empleado").where("cedula ='" + cedula + "'").fetch()
        .into(Empleado.class);
    Conexion.closeConnection();
    return (!empleado.isEmpty() ? empleado.get(0) : null);
  }

  /**
   * Obtiene todos los empleados en la base de datos que están habilitados (el
   * atributo enabled correspondiente en la tabla usuario es true).
   * 
   * @return lista de Empleado.
   */
  public static List<Empleado> getEmpleadosHabilitados() {
    List<Empleado> sedes = Conexion.db().select().from("empleado").join("usuario").on("empleado.cedula = usuario.id")
        .where("enabled = true").fetch().into(Empleado.class);
    Conexion.closeConnection();

    return sedes;
  }

  /**
   * Obtiene todos los empleados en la base de datos que están habilitados (el
   * atributo enabled correspondiente en la tabla usuario es false).
   * 
   * @return lista de Empleado.
   */
  public static List<Empleado> getEmpleadosDeshabilitados() {

    List<Empleado> sedes = Conexion.db().select().from("empleado").join("usuario").on("empleado.cedula = usuario.id")
        .where("enabled = false").fetch().into(Empleado.class);
    Conexion.closeConnection();

    return sedes;
  }

  /**
   * El método revisa si un empleado existe en la base de datos mediante su
   * cédula.
   *
   * @param id Cédula del epleado.
   * @return True si el epleado ya existía en la base de datos, False de lo
   *         contrario.
   */
  public static Boolean checkExistence(String id) {
    var res = Conexion.db().select().from("empleado").where("cedula = '" + id + "'").fetch().into(Empleado.class);
    return res.size() != 0;
  }
}
