package model;

import Utilities.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
public class EmpleadoDAO {
    public int crearEmpleado(Empleado emp) {
        Connection conexion = null;
        PreparedStatement ins = null;
        int resultado = 0;
        String sqlStatement;
        
        try {
            conexion = Conexion.startConnection();
            sqlStatement = "INSERT INTO empleado VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ins = conexion.prepareStatement(sqlStatement);
            ins.setInt(1, emp.getID());
            ins.setString(2, emp.getNombres());     
            ins.setString(3, emp.getApellidos());
            ins.setString(4, emp.getRol());
            ins.setString(5, emp.getDireccion());
            ins.setString(6, emp.getTelefono());
            ins.setDate(7, Date.valueOf(emp.getBirthdate()));
            ins.setInt(8, emp.getSede());
            resultado = ins.executeUpdate();    
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(ins != null)
                    ins.close();
                if(conexion != null){
                    conexion.close();
                    Conexion.closeConnection();
                }
            }
            catch (SQLException ex) {
                
            }
        }
        return resultado;
    }

    public Empleado cargarEmpleado(int id){
        Empleado em = null;
        Connection conexion = null;
        PreparedStatement instruccion = null;
        ResultSet rs = null;
        String sqlStatement;

        try {
            conexion = Conexion.startConnection();
            sqlStatement = "SELECT * FROM empleado WHERE \"ID\" = ?";
            instruccion = conexion.prepareStatement(sqlStatement);
            instruccion.setInt(1, id);
            rs = instruccion.executeQuery();

            if(rs.next()) {
                em = new Empleado();
                Object[]info = new Object[8];
                for(int i = 0 ; i < 8 ; ++i)
                    info[i] = rs.getObject(i+1);
                
                em.Charge(info);
            }
        }
        catch(SQLException e) {
            System.out.println(e.toString());
        }
        finally {
            try {
                if(instruccion != null)
                    instruccion.close();
                if(conexion != null){
                    conexion.close();
                    Conexion.closeConnection();
                }
            }
            catch(SQLException ex) {
                
            }
        }
        return em;
    } 

    
    public int modificarUsuario(Usuario u) {
        Connection conexion = null;
        PreparedStatement instrucciones = null;
        int resultado = 0;
        String sqlStatement;
        conexion = Conexion.startConnection();
        sqlStatement = "UPDATE cliente SET username = ?, password = ? WHERE ID = ?";
        
        try {
            
            
            instrucciones = conexion.prepareStatement(sqlStatement);

            instrucciones.setString(1, u.getUsername());
            instrucciones.setString(2, u.getPassword());
            instrucciones.setInt(3, u.getID());

            resultado = instrucciones.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e.toString());
        }
        finally {
            try {
                if(instrucciones != null)
                    instrucciones.close();
                if(conexion != null){
                    conexion.close();
                    Conexion.closeConnection();
                }
            }
            catch(SQLException ex) {
                // Do something ...
            }
        }
        return resultado;
    }


    public int borrarUsuario(int id) {
        Connection conexion = null;
        PreparedStatement instrucciones = null;
        int resultado = 0;
        String sqlStatement;

        try{
            conexion = Conexion.startConnection();
            sqlStatement = "DELETE FROM usuario WHERE ID = ?";
            instrucciones = conexion.prepareStatement(sqlStatement);

            instrucciones.setInt(1, id);
            resultado = instrucciones.executeUpdate();
        }
        catch(SQLException e) {
            // Do something ...
        }
        finally {
            try {
                if(instrucciones != null)
                    instrucciones.close();
                if(conexion != null){
                    conexion.close();
                    Conexion.closeConnection();
                }
            }
            catch(SQLException ex) {
                // Do something ...
            }
        }
        return resultado;
    }

}