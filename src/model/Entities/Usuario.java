package model.Entities;

import java.util.List;
import org.jooq.impl.DSL;
import utilities.Conexion;
import utilities.GeneralChecker;

/**
 * Clase Usuario. Contiene los parámetros asignados a un usuario, y los métodos
 * para la comunicación con la BD relacionados a un usuario.
 * 
 * @author David Henao
 * @version 1.0
 * @since 24/09/2021
 */
public class Usuario {

  // <editor-fold defaultstate="collapsed" desc="Atributos de la entidad">
  private String id;
  private String username;
  private String password;
  private boolean enabled;
  // </editor-fold>

  public Usuario() {
  }

  public Usuario(String id, String username, String password, boolean enabled) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.enabled = enabled;
  }

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Busca en la base de datos un Usuario que cuente con el usuario y la
   * contraseña suministrados
   * 
   * @param user El usuario ingresado.
   * @param pass La contraseña a ingresar
   * @return el id (>0) del usuario si existe con los datos suministrados ben la
   *         base de datos, -1 si no existe un usuario con esos datos
   *         suministrados, -2 si el usuario existe pero está deshabilitado.
   * @throws UsuarioInhabilitado
   */
  public static int entradaUsuario(String user, String pass) throws UsuarioInhabilitado {
    int code = -1;
    if (!GeneralChecker.checkChar(new String[] { user, pass })) {
      List<Usuario> usuario = Conexion.db().select().from("usuario")
          .where("username ='" + user + "' and password ='" + pass + "'").fetch().into(Usuario.class);
      Conexion.closeConnection();
      if (!usuario.isEmpty()) {
        Usuario u = usuario.get(0);
        if (u.enabled)
          code = Integer.parseInt(u.id);
        else
          throw new Usuario.UsuarioInhabilitado();
      }
    }
    return code;
  }

  public static class UsuarioInhabilitado extends Exception {

  }

  /**
   * Cambia el estado del atributo enabled de la tabla usuario a true, de un
   * usuario con un id especificado (lo mismo que cambiarEstado(id, true))
   * 
   * @param id El ID del usuario
   * @return
   */
  public static void habilitarUsuario(String id) {
    cambiarEstado(id, true);
  }

  /**
   * Cambia el estado del atributo enabled de la tabla usuario a false, de un
   * usuario con un id especificado (lo mismo que cambiarEstado(id, false))
   * 
   * @param id El ID del usuario
   * @return
   */
  public static void deshabilitarUsuario(String id) {
    cambiarEstado(id, false);
  }

  /**
   * Cambia el estado del atributo enabled de la tabla usuario a true, de un
   * usuario con un id especificado
   * 
   * @param id      El ID del usuario a modificar
   * @param enabled el estado a modificar
   * @return
   */
  private static void cambiarEstado(String id, boolean enabled) {
    String sql = "update usuario set enabled =" + enabled + " where id = '" + id + "'";
    Conexion.db().execute(sql);
  }

  /**
   * Revisa si un usuario ya existía en la BD.
   * 
   * @param username usuario a verificar.
   * @return True si el usuario existía, False de lo contrario.
   */
  public static Boolean checkExistence(String username) {
    if (username == null)
      return false;

    var user = Conexion.db().select().from("usuario").where("username = '" + username + "'").fetch()
        .into(Usuario.class);
    if (user.size() != 0)
      return true;
    return false;
  }

  /**
   * Ingresa un usuario en la BD.
   *
   * @param u Usuario a ingresar.
   * @return True si el registro fue exitoso, False de lo contrario.
   */
  public static Boolean registrarUsuario(Usuario u) {
    int res;
    try {
      res = Conexion.db().insertInto(DSL.table("usuario"), DSL.field("id"), DSL.field("username"),
          DSL.field("password"), DSL.field("enabled")).values(u.getId(), u.getUsername(), u.getPassword(), Boolean.TRUE)
          .execute();
    } catch (Exception e) {
      res = 0;
    }
    return res != 0;
  }
}
