package model;

import java.sql.*;

import utilities.Conexion;

public class UsuarioDAO {
    public int crearUsuario(Usuario u) {
        Connection conexion = null;
        PreparedStatement instruccion = null;
        int resultado = 0;
        String sqlStatement;

        try {
            conexion = Conexion.startConnection();
            sqlStatement = "INSERT INTO usuario VALUES (?, ?, ?, ?)";
            instruccion = conexion.prepareStatement(sqlStatement);
            instruccion.setInt(1, u.getID());
            instruccion.setString(2, u.getUsername());
            instruccion.setString(3, u.getPassword());
            instruccion.setBoolean(4, u.isEnabled());

            resultado = instruccion.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (instruccion != null) instruccion.close();
                if (conexion != null) {
                    conexion.close();
                    Conexion.closeConnection();
                }
            } catch (SQLException ex) {

            }
        }
        return resultado;
    }

    public Usuario cargarUsuario(int id) {
        Usuario u = null;
        Connection conexion = null;
        PreparedStatement instruccion = null;
        ResultSet rs = null;
        String sqlStatement;

        try {
            conexion = Conexion.startConnection();
            sqlStatement = "SELECT * FROM usuario WHERE ID = ?";
            instruccion = conexion.prepareStatement(sqlStatement);
            instruccion.setInt(1, id);
            rs = instruccion.executeQuery();

            if (rs.next()) {
                u = new Usuario();
                u.setID(rs.getInt(1));
                u.setUsername(rs.getString(2));
                u.setPassword(rs.getString(3));
                u.setEnabled(rs.getBoolean(4));
            }
        } catch (SQLException e) {

        } finally {
            try {
                if (instruccion != null) instruccion.close();
                if (conexion != null) {
                    conexion.close();
                    Conexion.closeConnection();
                }
            } catch (SQLException ex) {

            }
        }
        return u;
    }

    public int entradaUsuario(String user, String pass) {
        int code = -1;
        if (verificarUsuario(user) && verificarPassword(pass)) {
            Usuario u = null;
            Connection conexion = null;
            PreparedStatement instruccion = null;
            ResultSet rs = null;
            String sqlStatement;

            try {
                conexion = Conexion.startConnection();
                sqlStatement = "SELECT * FROM usuario WHERE Username = ? AND Password = ?";
                instruccion = conexion.prepareStatement(sqlStatement);
                instruccion.setString(1, user);
                instruccion.setString(2, pass);
                rs = instruccion.executeQuery();

                if (rs.next()) {
                    u = new Usuario();
                    u.setID(rs.getInt(1));
                    u.setUsername(rs.getString(2));
                    u.setPassword(rs.getString(3));
                    u.setEnabled(rs.getBoolean(4));
                    if (u.isEnabled()) {
                        code = u.getID();
                    } else {
                        code = -2;
                    }
                } else {
                    code = -1;
                }
            } catch (SQLException e) {
                code = -1;
            } finally {
                try {
                    if (instruccion != null) instruccion.close();
                    if (conexion != null) {
                        conexion.close();
                        Conexion.closeConnection();
                    }
                } catch (SQLException ex) {

                }
            }

        } else {
            code = -1;
        }
        return code;
    }

    public int modificarUsuario(Usuario u) {
        Connection conexion = null;
        PreparedStatement instrucciones = null;
        int resultado = 0;
        String sqlStatement;
        conexion = Conexion.startConnection();
        if (!verificarUsuario(u.getUsername()) || !verificarPassword(u.getPassword())) {
            resultado = -1;
        } else {
            sqlStatement = "UPDATE cliente SET username = ?, password = ?, enabled = ? WHERE ID = ?";
            try {

                instrucciones = conexion.prepareStatement(sqlStatement);

                instrucciones.setString(1, u.getUsername());
                instrucciones.setString(2, u.getPassword());
                instrucciones.setBoolean(3, u.isEnabled());
                instrucciones.setInt(4, u.getID());

                resultado = instrucciones.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.toString());
            } finally {
                try {
                    if (instrucciones != null) instrucciones.close();
                    if (conexion != null) {
                        conexion.close();
                        Conexion.closeConnection();
                    }
                } catch (SQLException ex) {
                    // Do something ...
                }
            }
        }

        return resultado;
    }

    public int borrarUsuario(int id) {
        Connection conexion = null;
        PreparedStatement instrucciones = null;
        int resultado = 0;
        String sqlStatement;

        try {
            conexion = Conexion.startConnection();
            sqlStatement = "DELETE FROM usuario WHERE ID = ?";
            instrucciones = conexion.prepareStatement(sqlStatement);

            instrucciones.setInt(1, id);
            resultado = instrucciones.executeUpdate();
        } catch (SQLException e) {
            // Do something ...
        } finally {
            try {
                if (instrucciones != null) instrucciones.close();
                if (conexion != null) {
                    conexion.close();
                    Conexion.closeConnection();
                }
            } catch (SQLException ex) {
                // Do something ...
            }
        }
        return resultado;
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
    private boolean verificarUsuario(String user) {
        char F[] = { '.', ',', '\'', '\"', '*', '=', '+', '-', '_', '!' };
        for (int i = 0; i < user.length(); ++i)
            for (int j = 0; j < F.length; ++j)
                if (user.charAt(i) == F[j]) return false;
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
    private boolean verificarPassword(String pass) {
        boolean valid = false;
        if (pass.trim().equals("") || !pass.trim().equals(pass)) return valid;
        char F[] = { '.', ',', '\'', '\"', '+', '-', '_', '!' };
        for (int i = 0; i < pass.length(); ++i)
            for (int j = 0; j < F.length; ++j)
                if (pass.charAt(i) == F[j]) return valid;
        valid = true;
        return valid;
    }
}
