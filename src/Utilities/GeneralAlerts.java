package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Esta clase está diseñada para construir y mostrar los mensajes de alerta
 * pertinentes a diversas pantallas.
 */
public class GeneralAlerts {
    private static Alert cEmpty; // Alerta para campos vacios
    private static Alert cForbi; // Alerta para caracteres prohibidos
    private static Alert userExist; // Alerta para usuarios ya existentes
    private static Alert regSuccess; // Alerta para registro exitoso
    private static Alert errorUnexpt; // Alerta para un error fuera de los previsto
    private static Alert userNull; // Alerta para usuarios nulos(no seleccionados).
    private static Alert pagoExitoso; // Alerta para pago exitoso.
    private static Boolean inicializado = false; // Indica si se han inicializado las alertas.

    private static void init() {
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
        userNull.setContentText("Por favor, seleccione el empleado que desea modificar.");
        userNull.setTitle("Empleado no seleccionado");
        userNull.setHeaderText("No ha sido seleccionado un empleado");

        errorUnexpt = new Alert(AlertType.NONE);
        errorUnexpt.setAlertType(AlertType.INFORMATION);
        errorUnexpt.setContentText("No se ha podido registrar el usuario por un error inesperado");
        errorUnexpt.setTitle("Error inesperado");
        errorUnexpt.setHeaderText("Error inesperado");

        pagoExitoso = new Alert(AlertType.NONE);
        pagoExitoso.setAlertType(AlertType.INFORMATION);
        pagoExitoso.setContentText("El pago se ha efectuado con exito.");
        pagoExitoso.setTitle("Pago Exitoso");
        pagoExitoso.setHeaderText("Pago Exitoso");

        inicializado = true;
    }

    // Panel con información de que hay un campo vacio
    public static void showEmptyFieldAlert() {
        if (!inicializado) init();
        cEmpty.show();
    }

    // Panel con información de que se usan pasos prohibidos
    public static void showCharForbidenAlert() {
        if (!inicializado) init();
        cForbi.show();
    }

    // Panel cuando existe un empleado
    public static void showUserExistAlert() {
        if (!inicializado) init();
        userExist.show();
    }

    // Panel de error inesperado
    public static void showErrorUnexpt() {
        if (!inicializado) init();
        errorUnexpt.show();
    }

    public static void showRegSuccess() {
        if (!inicializado) init();
        regSuccess.show();
    }

    public static void showUserNullAlert() {
        if (!inicializado) init();
        userNull.show();
    }

    public static void showPagoExitoso() {
        if (!inicializado) init();
        pagoExitoso.show();
    }
}
