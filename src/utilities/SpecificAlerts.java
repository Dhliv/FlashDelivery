package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;

/**
 * Esta clase está diseñada para construir y mostrar los mensajes de alerta
 * pertinentes a diversas pantallas.
 */
public class SpecificAlerts {
  private static Alert cEmpty; // Alerta para campos vacios
  private static Alert cForbi; // Alerta para caracteres prohibidos
  private static Alert regSuccess; // Alerta para registro exitoso
  private static Alert errorUnexpt; // Alerta para un error fuera de lo previsto.
  private static Alert userNull; // Alerta para usuarios nulos(no seleccionados).
  private static Alert pagoExitoso; // Alerta para pago exitoso.
  private static Alert updSucces; // Alerta para actualización exitosa.
  private static Alert userExists; // Alerta para indicar que el usuario ya existía.
  private static Alert empleadoExists; // Alerta para indicar que el empleado ya existía.
  private static Alert badLogin; // Alerta para indicar que el usuario/contraseña es erroneo.
  private static Alert cardUnexist; //Alerta que indica que una tarjeta es inexistente o está mal escrita.

  public static void init() {

    cEmpty = new Alert(AlertType.WARNING);
    cEmpty.setContentText("Por favor rellene los campos restantes.");
    cEmpty.setTitle("Campos Vacíos");
    cEmpty.setHeaderText("Existen campos vacíos");

    cForbi = new Alert(AlertType.WARNING);
    cForbi.setContentText("No es posible utilizar los siguientes caracteres: . , \' \" * = + - _ !");
    cForbi.setTitle("Caracteres Prohibidos");
    cForbi.setHeaderText("Se detectó el uso de caracteres prohibidos");

    regSuccess = new Alert(AlertType.INFORMATION);
    regSuccess.setContentText("El registro ha sido exitoso.");
    regSuccess.setTitle("Registro Exitoso");
    regSuccess.setHeaderText("");

    userNull = new Alert(AlertType.WARNING);
    userNull.setContentText("Por favor, seleccione el empleado que desea modificar.");
    userNull.setTitle("Empleado no seleccionado");
    userNull.setHeaderText("No ha sido seleccionado un empleado");

    errorUnexpt = new Alert(AlertType.INFORMATION);
    errorUnexpt.setContentText("No se ha podido registrar el usuario por un error inesperado");
    errorUnexpt.setTitle("Error inesperado");
    errorUnexpt.setHeaderText("Error inesperado");

    pagoExitoso = new Alert(AlertType.INFORMATION);
    pagoExitoso.setContentText("El pago se ha efectuado con exito.");
    pagoExitoso.setTitle("Pago Exitoso");
    pagoExitoso.setHeaderText("Pago Exitoso");

    updSucces = new Alert(AlertType.INFORMATION);
    updSucces.setContentText("Los datos se han actualizado con exito.");
    updSucces.setTitle("Actualización Exitosa");
    updSucces.setHeaderText("Actualización Exitosa");

    userExists = new Alert(AlertType.ERROR);
    userExists.setContentText("El usuario que está registrando ya se encuentra registrado, elija otro.");
    userExists.setTitle("Usuario Duplicado");
    userExists.setHeaderText("Usuario Duplicado");

    badLogin = new Alert(AlertType.ERROR);
    badLogin.setContentText("El usuario o contraseña digitado es incorrecto.");
    badLogin.setTitle("Usuario o Contraseña incorrectos");
    badLogin.setHeaderText("Usuario o Contraseña incorrectos");

    empleadoExists = new Alert(AlertType.ERROR);
    empleadoExists.setContentText("El empleado que intenta registrar ya se encuentra registrado en la base de datos.");
    empleadoExists.setTitle("Empleado ya registrado");
    empleadoExists.setHeaderText("Empleado ya registrado");

    cardUnexist = new Alert(AlertType.ERROR);
    cardUnexist.setContentText("Tarjeta de crédito inexistente");
    cardUnexist.setTitle("Comprobar número de tarjeta de crédito");
    cardUnexist.setHeaderText("Tarjeta erronea");
  }

  /**
   * Alerta que indica la existencia de campos vacíos.
   */
  public static void showEmptyFieldAlert() {
    cEmpty.showAndWait();
  }

  /**
   * Alerta que indica que se usaron caracteres prohibidos.
   */
  public static void showCharForbidenAlert() {
    cForbi.showAndWait();
  }

  /**
   * Alerta que indica que ocurrió un error no identificado.
   */
  public static void showErrorUnexpt() {
    errorUnexpt.showAndWait();
  }

  /**
   * Alerta que indica que el registro (de cualquier entidad) fue exitoso.
   */
  public static void showRegSuccess() {
    regSuccess.showAndWait();
  }

  /**
   * Alerta que indica que no se ha seleccionado un usuario para editarlo, o
   * eliminarlo.
   */
  public static void showUserNullAlert() {
    userNull.showAndWait();
  }

  /**
   * Alerta que indica que el pago del envío fue exitoso.
   */
  public static void showPagoExitoso() {
    pagoExitoso.showAndWait();
  }

  /**
   * Alerta que indica la exitosa actualización (de cualquier entidad):
   */
  public static void showUpdSucces() {
    updSucces.showAndWait();
  }

  /**
   * Alerta que indica que el Usuario por registrar ya existe.
   */
  public static void showUserExist() {
    userExists.showAndWait();
  }

  /**
   * Alerta que indica que el usurio o contraseña digitados están errados.
   */
  public static void showBadLogin() {
    badLogin.showAndWait();
  }

  /**
   * Alerta que indica que un empleado ya estaba registrado.
   */
  public static void showEmpleadoExists() {
    empleadoExists.showAndWait();
  }

  public static void showCardUnexist(){
    cardUnexist.showAndWait();
  }
}
