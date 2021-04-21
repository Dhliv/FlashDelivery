package utilities;

public class UserRegisterChecker {

  public UserRegisterChecker(){
    
  }

  //Revisa si hay un caracter prohibido
  public boolean checkChar(String campo[]) {
    boolean ch = false;
    char F[] = { '.', ',', '\'', '\"', '*', '=', '+', '-', '_', '!' };

    for (int i = 0; i < campo.length; i++) {
      for (int j = 0; j < campo[i].length(); j++) {
        for (int k = 0; k < F.length; k++) {
          if (campo[i].charAt(j) == F[k]) {
            ch = true;
          }
        }
      }
    }

    return ch;
  }

  //Revisa si existe algun campo vacio
  public boolean checkEmpty(String campo[], Object fecha, Object idSede) {
    boolean ch = false;

    for (int i = 0; i < campo.length; i++) {
      if (campo[i] == null || campo[i].equals("")) {
        ch = true;
        if (campo[i] == null)
          campo[i] = "";
      }
    }

    if (fecha == null || idSede == null)
      ch = true;

    return ch;
  }
}
