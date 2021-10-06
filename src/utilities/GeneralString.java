package utilities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javafx.util.Pair;
import java.net.URL;

import org.apache.commons.io.FilenameUtils;

/**
 * Clase GeneralString. Contiene métodos de (posible) uso general con strings.
 * 
 * @author David Henao
 * @author Alejandro Pergueza Amaya
 * @version 1.0
 * @since 24/09/2021
 */
public class GeneralString {

  /**
   * Remueve los NewLine de un String.
   * 
   * @param s Cadena de texto.
   * @return Retorna la cadena en la que cada caracter '\n' es cambiado por ". ".
   */
  public static String removeNewLine(String s) {
    String res = "";
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '\n')
        res += ". ";
      else
        res += s.charAt(i);
    }

    return res;
  }

  /**
   * Corta el String a una longitud fija. En caso de superar cierta cantidad de
   * caracteres se eliminarán y serán reemplazados por la cadena "...".
   *
   * @param s         Cadena de caracteres
   * @param maxLength Maxima longitud esperada en dicha cadena
   * @return La cadena s Con un numero maximo de "maxLength" caracteres.
   */
  public static String cutString(String s, int maxLength) {
    if (s.length() >= maxLength - 3)
      s = s.substring(0, maxLength) + "...";
    return s;
  }

  /**
   * Añade espacios al inicio del string para que todos tengan el mismo tamaño
   * 
   * @param s Array de strings
   * @return Array con todos los strings del mismo tamaño
   */
  public static String[] textToRight(String[] s) {
    Integer maxi = 0;

    // Encontrar la longitud maxima
    for (int i = 0; i < s.length; i++) {
      if (s[i].length() > maxi)
        maxi = s[i].length();
    }

    for (int i = 0; i < s.length; i++) {
      String aux = "";
      for (int j = 0; j < maxi - s[i].length(); j++)
        aux += " ";
      s[i] = aux + s[i];
    }

    return s;
  }

  public static String capitalizeFirstLetter(String original) {
    if (original == null || original.length() == 0) {
      return original;
    }
    return original.substring(0, 1).toUpperCase() + original.substring(1);
  }

  /**
   * Se separa el string en palabras.
   * 
   * @param s String que se separa
   * @return Par con Queue de palabras y dice si termina o no en salto de linea
   */
  public static Queue<Pair<String, Boolean>> splitInWords(String s) {

    String palabra; // Palabra que será almacenada en el Queue
    Queue<Pair<String, Boolean>> words = new LinkedList<>(); // Almacena las palabras
    int pos = 0; // Posición inicial en donde inicia una nueva palabra
    s += "\n";

    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '\n' || Character.isWhitespace(s.charAt(i))) {
        palabra = s.substring(pos, i);
        pos = i + 1;
        if (palabra.length() > 0)
          words.add(new Pair<String, Boolean>(palabra, (s.charAt(i) == '\n' ? Boolean.TRUE : Boolean.FALSE)));
      }
    }

    return words;
  }

  /**
   * Formatea un String para que pueda ser dibujado en el PDF. Se usa en la
   * descripción del producto.
   * 
   * @param s               String que se va a formatear
   * @param MAXLENGTHSTRING Maxima cantidad de caracteres permitida
   * @return String formateado
   */
  public static String[] parseText(String s, Integer MAXLENGTHSTRING) {
    // Mayor información preguntenle a Winja

    ArrayList<String> sFormat = new ArrayList<String>(); // String troceado y listo para ser usado en el PDF.
    Queue<Pair<String, Boolean>> words = splitInWords(s);
    Pair<String, Boolean> par;
    String aux = "";
    int sum = 1;

    while (!words.isEmpty()) {
      par = words.poll();
      sum += par.getKey().length();
      if (sum > MAXLENGTHSTRING) {
        sFormat.add(aux);
        sum = par.getKey().length();
        aux = par.getKey();
      } else if (par.getValue()) {
        sum = 1;
        aux += " " + par.getKey();
        sFormat.add(aux);
        aux = "";
      } else {
        if (aux.length() > 0) {
          aux += " ";
          sum++;
        }
        aux += par.getKey();
      }
    }
    if (aux.length() > 1)
      sFormat.add(aux);

    String[] array = sFormat.toArray(new String[0]);
    // sFormat.subList(fromIndex, toIndex)
    return array;
  }

  public static String getName(URL location) {
    return FilenameUtils.getBaseName(location.getPath());
  }

}
