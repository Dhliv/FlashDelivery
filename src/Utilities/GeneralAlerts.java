package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Esta clase está diseñada para construir y mostrar los mensajes de alerta
 * pertinentes a diversas pantallas.
 */
public class GeneralAlerts {
    private Alert cEmpty; // Alerta para campos vacios
    private Alert cForbi; // Alerta para caracteres prohibidos
    private Alert userExist; // Alerta para usuarios ya existentes
    private Alert regSuccess; // Alerta para registro exitoso
    private Alert errorUnexpt; // Alerta para un error fuera de los previsto
    private Alert userNull; // Alerta para usuarios nulos(no seleccionados).

    public GeneralAlerts() {
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

        regSuccess = new Alert(AlertType.NONE);
        regSuccess.setAlertType(AlertType.INFORMATION);
        regSuccess.setContentText("El registro ha sido exitoso.");
        regSuccess.setTitle("Registro Exitoso");
        regSuccess.setHeaderText("");

        userNull = new Alert(AlertType.NONE);
        userNull.setAlertType(AlertType.WARNING);
        userNull.setContentText("Por favor, seleccione el usuario que desea modificar.");
        userNull.setTitle("Usuario no seleccionado");
        userNull.setHeaderText("No ha sido seleccionado un usuario");

        errorUnexpt = new Alert(AlertType.NONE);
        errorUnexpt.setAlertType(AlertType.INFORMATION);
        errorUnexpt.setContentText("No se ha podido registrar el usuario por un error inesperado");
        errorUnexpt.setTitle("Error inesperado");
        errorUnexpt.setHeaderText("Error inesperado");
    }

    // Panel con información de que hay un campo vacio
    public void showEmptyFieldAlert() {
        cEmpty.show();
    }

    // Panel con información de que se usan pasos prohibidos
    public void showCharForbidenAlert() {
        cForbi.show();
    }

    // Panel cuando existe un empleado
    public void showUserExistAlert() {
        userExist.show();
    }

    // Panel de error inesperado
    public void showErrorUnexpt() {
        errorUnexpt.show();
    }

    public void showRegSuccess() {
        regSuccess.show();
    }

    public void showUserNullAlert() {
        userNull.show();
    }
}
