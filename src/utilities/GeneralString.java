package utilities;

public class GeneralString {
  
  /**
   * Remueve los NewLine de un String.
   * @param s Cadena de texto.
   * @return Retorna la cadena en la que cada caracter '\n' es cambiado por ". ".
   */
  public static String removeNewLine(String s){
    String res = "";
    for(int i=0; i<s.length(); i++){
      if(s.charAt(i) == '\n') res += ". ";
      else res += s.charAt(i);
    }

    return res;
  }


  /**
   * Corta el String a una longitud fija en caso de superar cierta cantidad de caracteres. 
   *
   * @param s Cadena de caracteres
   * @param maxLength Maxima longitud esperada en dicha cadena
   * @return La cadena s Con un numero maximo de "maxLength" caracteres.
   */
  public static String cutString(String s, int maxLength){
    if(s.length() >= maxLength-3) s = s.substring(0,maxLength) + "...";
    return s;
  }

  /**
   * Añade espacios al inicio del string para que todos tengan el mismo tamaño
   * @param s Array de strings
   * @return Array con todos los strings del mismo tamaño
   */
  public static String[] textToRight(String[] s){
    Integer maxi = 0;
    
    //Encontrar la longitud maxima
    for(int i=0; i<s.length; i++){
      if(s[i].length() > maxi) maxi = s[i].length();
    }

    for(int i = 0; i < s.length; i++){
      String aux = "";
      for(int j = 0; j < maxi - s[i].length(); j++) aux += " ";
      s[i] = aux + s[i];
    }

    return s;
  }

}