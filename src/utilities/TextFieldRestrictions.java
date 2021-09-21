package utilities;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class TextFieldRestrictions {
  /**
   * Limite el numero maximo de caracteres de un TextField.
   * 
   * @param txt  TextField al que se le desea aplicar la restricción.
   * @param size El tamañano maximo permitido en el TextField.
   */
  public static void textFieldMaxLength(TextField txt, Integer size) {
    TextFormatter prevFormatter = txt.getTextFormatter();
    Pattern pattern = Pattern.compile(".{0," + Integer.toString(size) + "}");
    TextFormatter formatter = 
    new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
      if(!checkPreviousFilter(prevFormatter, change)) return null;

      return pattern.matcher(change.getControlNewText()).matches() ? change : null;
    });
    txt.setTextFormatter(formatter);
  }

  /**
   * Convierte el TextField en uno que solo permite el uso de numeros enteros. (No
   * permite puntos ni comas)
   * 
   * @param txt TextField al que se le desea aplicar la restricción.
   */
  public static void textFieldNumeric(TextField txt) {
    TextFormatter prevFormatter = txt.getTextFormatter();
    TextFormatter formatterNOnly = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
      String aux = change.getText();
      if(!checkPreviousFilter(prevFormatter, change)) return null;
      if (change.isDeleted()) return change;
      if (aux.length() > 0 && Character.isDigit(aux.charAt(0))) return change;
      else return null;
    }
    );

    txt.setTextFormatter(formatterNOnly);
  }
  /**
   * 
   * @param txt
   * @param change
   * @return
   */
  private static boolean checkPreviousFilter(TextFormatter txt, TextFormatter.Change change){
    if(txt != null){
      UnaryOperator<TextFormatter.Change> fil = txt.getFilter();
        TextFormatter.Change g = fil.apply(change);
        if(g == null)
          return false;
    }
    return true;
  }
  /**
   * TODO INCOMPLETO
   * Método para evaluar un filtro de cambios obligatorios (usos del ratón y flechitas (teclas direccionales).
   * @param change
   * @return true si change corresponde a alguno de los ya citados, false en caso contrario.
   */
  private static boolean imperativeFilter(TextFormatter.Change change){
    String aux = change.toString();
    return true;
  }
}
