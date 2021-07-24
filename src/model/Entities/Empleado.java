package model.Entities;

import java.time.LocalDate;
import java.util.List;

import utilities.Conexion;

public class Empleado implements Entity {
    private String cedula;
    private String nombres, apellidos, rol, direccion, telefono;
    private LocalDate birthdate;
    public int sede;

    public Empleado() {
    }

    public Empleado(String cedula, String nombres, String apellidos, String rol, String direccion, String telefono,
            LocalDate birthdate, int sede) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.rol = rol;
        this.direccion = direccion;
        this.telefono = telefono;
        this.birthdate = birthdate;
        this.sede = sede;
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
        return sede;
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
        this.sede = sede;
    }

    @Override
    public void Charge(Object[] info) {
        this.cedula = (String) info[0];
        this.nombres = (String) info[1];
        this.apellidos = (String) info[2];
        this.rol = (String) info[3];
        this.direccion = (String) info[4];
        this.telefono = (String) info[5];
        this.birthdate = (info[6] == null) ? LocalDate.now() : ((java.sql.Date) info[6]).toLocalDate();
        this.sede = (int) info[7];
    }

    @Override
    public String toString() {
        return "Empleado{" + "cedula=" + cedula + ", nombres=" + nombres + ", apellidos=" + apellidos + ", rol=" + rol
                + ", direccion=" + direccion + ", telefono=" + telefono + ", birthdate=" + birthdate + ", sede=" + sede
                + '}';
    }

    /**
     * Obtiene todos los empleados en la base de datos y los retorna.
     * 
     * @return lista de empleados existentes en la BD.
     */
    public static List<Empleado> getSedes() {
        List<Empleado> sedes = Conexion.db().select().from("empleado").fetch().into(Empleado.class); // Ejecuto la query
                                                                                                     // 'sql'.
        Conexion.closeConnection();

        return sedes;
    }

    /**
     * Actualiza en la BD los datos del empleadoa los editados (la cedula no cambia
     * nunca).
     * 
     * @param empleado informacion completa a actualizar (incluso sin cambios).
     */
    public static void updateEmpleado(Empleado empleado) {
        String sql = "update empleado set nombres='" + empleado.getNombres() + "', rol='" + empleado.getRol()
                + "', direccion='" + empleado.getDireccion() + "', telefono='" + empleado.getTelefono()
                + "', birthdate='" + empleado.getBirthdate() + "', sede=" + empleado.getSede() + " where cedula='"
                + empleado.getCedula() + "'";

        Conexion.db().execute(sql);
    }

    public static int crearEmpleado(Empleado e) {
        String sql = "insert into empleado VALUES ('" + e.cedula + "', '" + e.getNombres() + "', '" + e.getApellidos()
                + "', '" + e.getRol() + "', '" + e.getDireccion() + "', '" + e.getTelefono() + "', '"
                + e.getBirthdate().toString() + "', " + e.getSede() + ")";

        return Conexion.db().execute(sql);
    }

    public static Empleado cargarEmpleado(String cedula) {

        List<Empleado> empleado = Conexion.db().select().from("empleado").where("cedula ='" + cedula + "'").fetch()
                .into(Empleado.class);
        Conexion.closeConnection();
        return (!empleado.isEmpty() ? empleado.get(0) : null);
    }

}
