package model.Entities;

import java.util.List;

import utilities.Conexion;

public class Usuario {

    // Atributos de la entidad
    private int id;
    private String username;
    private String password;
    private boolean enabled;

    public Usuario() {
    }

    public Usuario(int id, String username, String password, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public int getId() {
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

    public void setId(int id) {
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

    public static int entradaUsuario(String user, String pass) {
        int code = -1;
        if (verificarUsuario(user) && verificarPassword(pass)) {
            List<Usuario> usuario = Conexion.db().select().from("usuario")
                    .where("username ='" + user + "' and password ='" + pass + "'").fetch().into(Usuario.class);
            Conexion.closeConnection();
            if (!usuario.isEmpty()) {
                Usuario u = usuario.get(0);
                if (u.enabled)
                    code = u.id;
                else
                    code = -2;
            }
        }
        return code;
    }

    /**
     * Verifica que el user tenga el formato correcto, comprobando caracteres
     * erroneos como #'.', ',', '\'', '\"', '*', '=', '+', '-', '_', '!' , y también
     * verificando si está vacía o no
     * 
     * @param user La contraseña ingresada.
     * @return true si es correcta la contraseña, false si tiene algún error de los
     *         antes descritos.
     */
    private static boolean verificarUsuario(String user) {
        char F[] = { '.', ',', '\'', '\"', '*', '=', '+', '-', '_', '!' };
        for (int i = 0; i < user.length(); ++i)
            for (int j = 0; j < F.length; ++j)
                if (user.charAt(i) == F[j])
                    return false;
        return true;
    }

    /**
     * Verifica que el password tenga el formato correcto, comprobando caracteres
     * erroneos como #'.', ',', '\'', '\"', '+', '-', '_', '!' , y también
     * verificando si está vacía o no
     * 
     * @param pass La contraseña ingresada.
     * @return true si es correcta la contraseña, false si tiene algún error de los
     *         antes descritos.
     */
    private static boolean verificarPassword(String pass) {
        boolean valid = false;
        if (!pass.trim().equals(pass))
            return valid;
        char F[] = { '.', ',', '\'', '\"', '+', '-', '_', '!' };
        for (int i = 0; i < pass.length(); ++i)
            for (int j = 0; j < F.length; ++j)
                if (pass.charAt(i) == F[j])
                    return valid;
        valid = true;
        return valid;
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
     * !TODO USAR LAS OPCIONES QUE PROPORCIONA jOOQ
     * Cambia el estado del atributo enabled
     * de la tabla usuario a true, de un usuario con un id especificado
     * 
     * @param id      El ID del usuario a modificar
     * @param enabled el estado a modificar
     * @return
     */
    private static void cambiarEstado(String id, boolean enabled) {
        String sql = "update usuario set enabled =" + enabled + " where id = '" + id + "'";
        Conexion.db().execute(sql);
    }

}
