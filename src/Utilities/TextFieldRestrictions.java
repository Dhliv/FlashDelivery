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
    Pattern pattern = Pattern.compile(".{0," + Integer.toString(size) + "}");
    TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
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
    TextFormatter formatterNOnly = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
      String aux = change.getText();
      if (change.isDeleted()) return change;
      if (aux.equals("1") || aux.equals("2") || aux.equals("3") || aux.equals("4") || aux.equals("5") || aux.equals("6") || aux.equals("7") || aux.equals("8") || aux.equals("9") || aux.equals("0")) {
        return change;
      } else
        return null;
    });

    txt.setTextFormatter(formatterNOnly);
  }
}
