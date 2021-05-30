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

}
