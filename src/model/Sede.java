package model;

public class Sede {
  private int id_sede;
  private String nombre;
  private String direccion;

  public Sede(int id, String name, String dir) {
    id_sede = id;
    nombre = name;
    direccion = dir;
  }

  public int getId_sede() {
    return id_sede;
  }

  public String getNombre() {
    return nombre;
  }

  public String getDireccion() {
    return direccion;
  }
}
