package utilities;

import java.util.ArrayList;

public class Roles {
  public ArrayList<String> rol = new ArrayList<>();
  public int ADMIN;
  public int SECRETARIO;
  public int OPERADOR;
  public int AUXILIAR;
  public int CONTADOR;

  public Roles() {
    rol.add("Gerente");
    ADMIN = 0;

    rol.add("Secretario(a)");
    SECRETARIO = 1;

    rol.add("Operador de oficina");
    OPERADOR = 2;

    rol.add("Auxiliar");
    AUXILIAR = 3;

    rol.add("Contador");
    CONTADOR = 4;
  }

}