package utilities;

public class GeneralChecker {

  /**
   * La función revisa si en un conjunto de strings existe algún caracter
   * prohibido.
   * 
   * @param campo conjunto de Strings a revisar.
   * @return True si existe un caracter prohibido.
   */
  public static boolean checkChar(String campo[]) {
    boolean ch = false;
    char F[] = { ',', '\'', '\"', '*', '=', '+', '!' };

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

  /**
   * La función revisa que un conjunto de strings y un conjunto de Objects no sean
   * vacíos o nulos.
   * 
   * @param campo   conjunto de Strings a revisar.
   * @param objetos conjunto de objetos a revisar.
   * @return True si se encontró algún string u objeto vacío.
   */
  public static boolean checkEmpty(String campo[], Object objetos[]) {
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
