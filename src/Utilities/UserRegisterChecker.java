package utilities;

public class UserRegisterChecker {

  public UserRegisterChecker() {

  }

  /*
   * La función revisa si en un conjunto de strings existe algún caracter
   * prohibido. Return true significa que encontró un caracter prohibido. De lo
   * contrario está todo ok.
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
   * La función revisa que un conjunto de strings y un conjunto de Objects no sean
   * vacíos o nulos. Return true significa que encontró un campo vacío o nulo. De
   * lo contrario está todo ok.
   */
  public boolean checkEmpty(String campo[], Object objetos[]) {
    boolean ch = false;

    for (int i = 0; i < campo.length; i++) {
      if (campo[i] == "") {
        ch = true;
        break;
      }
    }

    for (int i = 0; i < objetos.length; i++) {
      if (objetos[i] == null) {
        ch = true;
        break;
      }
    }

    return ch;
  }
}
