package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerta {
    
    private Alert cEmpty;
    private Alert cForbi;
    private Alert userExist;
    private Alert regExitoso;
    
    public Alerta(){
        cEmpty = new Alert(AlertType.NONE);
        cEmpty.setAlertType(AlertType.WARNING);
        cEmpty.setContentText("Por favor rellene los campos restantes.");
        cEmpty.setTitle("Campos Vacíos");
        cEmpty.setHeaderText("Existen campos vacíos");

        cForbi = new Alert(AlertType.NONE);
        cForbi.setAlertType(AlertType.WARNING);
        cForbi.setContentText("No es posible utilizar los siguientes caracteres: . , \' \" * = + - _ !");
        cForbi.setTitle("Caracteres Prohibidos");
        cForbi.setHeaderText("Se detectó el uso de caracteres prohibidos");
        
        userExist = new Alert(AlertType.NONE);
        userExist.setAlertType(AlertType.WARNING);
        userExist.setContentText("Por favor rellene los campos para un nuevo empleado.");
        userExist.setTitle("Empleado Repetido");
        userExist.setHeaderText("Ya existe este empleado");

        regExitoso = new Alert(AlertType.NONE);
        regExitoso.setAlertType(AlertType.INFORMATION);
        regExitoso.setContentText("El empleado se ha registrado con exito.");
        regExitoso.setTitle("Registro Exitoso");
        regExitoso.setHeaderText("");
    }

    //Panel con información de que hay un campo vacio
    public void showEmptyFieldAlert(){
        cEmpty.show();
    }
    //Panel con información de que se usan pasos prohibidos
    public void showCharForbidenAlert(){
        cForbi.show();
    }
    //Panel cuando existe un empleado
    public void showUserExistAlert(){
        userExist.show();
    }

    public void showRegSuccess(){
        regExitoso.show();
    }
}
