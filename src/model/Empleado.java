package model;

import java.time.LocalDate;

public class Empleado implements Entity {
    private int ID;
    private String nombres, apellidos, rol, direccion, telefono;
    private LocalDate birthdate;
    private int sede;

    public Empleado() {
    }

    public Empleado(int ID, String nombres, String apellidos, String rol, String direccion, String telefono,
            LocalDate birthdate, int sede) {
        this.ID = ID;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.rol = rol;
        this.direccion = direccion;
        this.telefono = telefono;
        this.birthdate = birthdate;
        this.sede = sede;
    }

    public int getID() {
        return ID;
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

    public void setID(int ID) {
        this.ID = ID;
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
        this.ID = (int) info[0];
        this.nombres = (String) info[1];
        this.apellidos = (String) info[2];
        this.rol = (String) info[3];
        this.direccion = (String) info[4];
        this.telefono = (String) info[5];
        this.birthdate = ((java.sql.Date) info[6]).toLocalDate();
        this.sede = (int) info[7];
    }

    @Override
    public String toString() {
        return "Empleado{" + "ID=" + ID + ", nombres=" + nombres + ", apellidos=" + apellidos + ", rol=" + rol
                + ", direccion=" + direccion + ", telefono=" + telefono + ", birthdate=" + birthdate + ", sede=" + sede
                + '}';
    }

}
