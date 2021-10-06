package utilities;

import java.util.ArrayList;
import java.util.Arrays;

public class Roles {
  static public String[] rol = { "Gerente", "Secretaria", "Operador", "Auxiliar", "Contador" };
  static public ArrayList<String> roles = new ArrayList<>(Arrays.asList(rol));
  static public int ADMIN = 0;
  static public int SECRETARIO = 1;
  static public int OPERADOR = 2;
  static public int AUXILIAR = 3;
  static public int CONTADOR = 4;
}