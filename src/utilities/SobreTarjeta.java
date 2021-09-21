package utilities;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


//TODO Documentar la clase (Probablemente Winja o Pergüeza)
public class SobreTarjeta {

  /**
   * NUMERO MAXIMO DE CARACTERES DEL CAMPO txtNumeroTarjeta
   */
  public static final Integer tNTM() {
    return 16;
  }
  /**
   * Método para comprobar si la tecla presionada corresponde a una tecla numerica
   * 
   * @param key Objeto representando la tecla presionada.
   * @return un numero o string vacio.
   */
  public static String keyEqualNumber(KeyCode key) {
    return key.isDigitKey() ? key.getChar() : "";
  }

  /**
   * Borra un número de caracteres al final de un string.
   * 
   * @param s string al que borrarle caracteres.
   * @param n numero de caracteres a borrar.
   * @return string con caracteres borrados.
   */
  public static String eraseFrom(String s, Integer n) {
    return s.substring(0, s.length() - n);
  }

  /**
   * Concatena dos strings.
   * 
   * @param s   String a
   * @param add String b
   * @return a concatenado b
   */
  public static String addTo(String s, String add) {
    return (s + add);
  }

  /**
   * Agrega un numero a la parte visual de la tarjeta.
   *
   * @param n          el numero a agregar.
   * @param counter    el numero de digitos que se encuentran visibles en la
   *                   tarjeta.
   * @param lblNumero1 primer cuarteto de numeros en la tarjeta.
   * @param lblNumero2 segundo cuarteto de numeros en la tarjeta.
   * @param lblNumero3 tercer cuarteto de numeros en la tarjeta.
   * @param lblNumero4 cuarteto final de numeros en la tarjeta.
   */
  public static void addNumber(String n, Integer counter, Label lblNumero1, Label lblNumero2, Label lblNumero3, Label lblNumero4) {
    if (counter < 4) {
      lblNumero1.setText(SobreTarjeta.addTo(lblNumero1.getText(), n + " "));
    } else if (counter < 8) {
      lblNumero2.setText(SobreTarjeta.addTo(lblNumero2.getText(), n + " "));
    } else if (counter < 12) {
      lblNumero3.setText(SobreTarjeta.addTo(lblNumero3.getText(), n + " "));
    } else {
      lblNumero4.setText(SobreTarjeta.addTo(lblNumero4.getText(), n + " "));
    }
  }

  /**
   * Borra numeros de la parte visual de la tarjeta.
   * 
   * @param counter    el numero de digitos que se encuentran visibles en la
   *                   tarjeta.
   * @param lblNumero1 primer cuarteto de numeros en la tarjeta.
   * @param lblNumero2 segundo cuarteto de numeros en la tarjeta.
   * @param lblNumero3 tercer cuarteto de numeros en la tarjeta.
   * @param lblNumero4 cuarteto final de numeros en la tarjeta.
   */
  public static void eraseNumber(Integer counter, Label lblNumero1, Label lblNumero2, Label lblNumero3, Label lblNumero4) {
    if (counter > 12) {
      lblNumero4.setText(SobreTarjeta.eraseFrom(lblNumero4.getText(), 2));
    } else if (counter > 8) {
      lblNumero3.setText(SobreTarjeta.eraseFrom(lblNumero3.getText(), 2));
    } else if (counter > 4) {
      lblNumero2.setText(SobreTarjeta.eraseFrom(lblNumero2.getText(), 2));
    } else {
      lblNumero1.setText(SobreTarjeta.eraseFrom(lblNumero1.getText(), 2));
    }
  }

  /**
   * Verifica si es necesario borrar o agregar la tecla presionada.
   * 
   * @param event la tecla presionada.
   * @param numTarjeta si es necesario identifcar si es un numero.
   * @return una tripleta de datos: si se debe borrar datos, si se debe agregar
   * datos y el dato a agregar.
   */
  public static Object[] checkErase(KeyEvent event, Boolean numTarjeta) {
    Boolean borrar = false;
    Boolean agregar = false;
    String addToText = "";
    KeyCode key = event.getCode();
    Object[] x = new Object[3];

    if (key.equals(KeyCode.BACK_SPACE)) {
      x[0] = true;
      x[1] = agregar;
      x[2] = addToText;
      return x;
    }

    if (numTarjeta) {
      addToText = SobreTarjeta.keyEqualNumber(key);
      if (addToText.length() != 0) agregar = true;
    } else
      agregar = true;

    x[0] = borrar;
    x[1] = agregar;
    x[2] = addToText;
    return x;
  }

}
