package utilities;

import javafx.scene.control.TextField;

public class GeneralChecker {

    /**
     * La función revisa si en un conjunto de strings existe algún caracter
     * prohibido.
     * 
     * @param campo conjunto de Strings a revisar.
     * @return True si existe un caracter prohibido.
     */
    public static boolean checkChar(String campo[]) {
        char F[] = { ',', '\'', '\"', '*', '=', '+', '!' };

        for (int i = 0; i < campo.length; i++) {
            if(campo[i] == null) return true;
            
            for (int j = 0; j < campo[i].length(); j++) {
                for (int k = 0; k < F.length; k++) {
                    if (campo[i].charAt(j) == F[k]) {
                        return true;
                    }
                }
            }
        }

        return false;
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
        
        for (int i = 0; i < objetos.length; i++) {
            if (objetos[i] == null) {
                return true;
            }
        }

        for (int i = 0; i < campo.length; i++) {
            if(campo[i] == null) return true;
            if (campo[i].trim() == "") {
                return true;
            }
        }

        return false;
    }

    /**
     * validar si un array de TextField presenta texto vacio o caracteres prohibidos
     * @param textFields
     * @return true si todas las validaciones son correctas
     */
    public static boolean checkTextFieldEmptyAndFC(TextField textFields[]) {
        for (TextField textField : textFields) {
            if (!checkTextFieldEmpty(textField)) return false;
            if (!checkTextFieldForbiddenCharacters(textField)) return false;
        }
        return true;
    }

    /**
     * validar si un TextField presenta texto vacio
     * @param textField
     * @return true si la validacion es correcta
     */
    public static boolean checkTextFieldEmpty(TextField textField) {
        if (textField.getText().trim() == "") {
            SpecificAlerts.showEmptyFieldAlert(textField.getId());
            return false;
        }
        return true;
    }

    /**
     * validar si un TextField presenta caracteres prohibidos
     * @param textField
     * @return true si la validacion es correcta
     */
    public static boolean checkTextFieldForbiddenCharacters(TextField textField) {
        char forbiddenCharacters[] = { ',', '\'', '\"', '*', '=', '+', '!' };
        for (char c : forbiddenCharacters)
            if (textField.getText().indexOf(c) != -1) {
                SpecificAlerts.showCharForbidenAlert(textField.getId());
                return false;
            }
        return true;
    }

}
