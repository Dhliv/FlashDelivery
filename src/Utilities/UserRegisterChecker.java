package utilities;

public class UserRegisterChecker {

  public UserRegisterChecker(){
    
  }

  /*
  Return true significa que encontró un caracter prohibido.
  De lo contrario está todo ok.
  */
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

  /*
  Return true significa que encontró un espacio vacio.
  De lo contrario está todo ok.
  */
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
