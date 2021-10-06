package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Esta clase está diseñada para construir y mostrar los mensajes de alerta
 * pertinentes a diversas pantallas.
 * 
 * @author David Henao
 * @version 1.0
 * @since 24/09/2021
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
  private static Alert cardUnexist; // Alerta que indica que una tarjeta es inexistente o está mal escrita.
  private static Alert numericFormat; // Alerta que indica que un campo no tiene formato numérico.
  private static Alert decimalFormat; // Alerta que indica que un campo no tiene formato decimal.
  private static Alert fechaNoValida; // Alerta que indica que la fecha ingresada no es valida.
  private static Alert peticionExitosa; // Alerta que indica que laa petición de recogida se generó con éxito.
  private static Alert usernameEqualPass; // Alerta que indica que la contraseña es igual al username.

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

    numericFormat = new Alert(AlertType.ERROR);
    numericFormat.setContentText("Se ha ingresado texto en un campo que esperaba un número");
    numericFormat.setTitle("Comprobar campos llenados");
    numericFormat.setHeaderText("Formato numérico errado");

    decimalFormat = new Alert(AlertType.ERROR);
    decimalFormat.setContentText(
        "Se ha ingresado texto en un campo que esperaba un número decimal (asegúrese de usar el . y no la ,");
    decimalFormat.setTitle("Comprobar campos llenados");
    decimalFormat.setHeaderText("Formato numérico errado");

    fechaNoValida = new Alert(AlertType.ERROR);
    fechaNoValida.setContentText("La fecha ingresada no es valida.");
    fechaNoValida.setTitle("Comprobar fecha");
    fechaNoValida.setHeaderText("Error en la fecha.");

    peticionExitosa = new Alert(AlertType.INFORMATION);
    peticionExitosa.setContentText("Se ha generado con éxito la petición de recogida.");
    peticionExitosa.setTitle("Petición Generada con Éxito");
    peticionExitosa.setHeaderText("Petición Exitosa");

    usernameEqualPass = new Alert(AlertType.ERROR);
    usernameEqualPass.setContentText("Por razones de seguridad, el username y la contraseña no pueden ser iguales.");
    usernameEqualPass.setTitle("Error de registro");
    usernameEqualPass.setHeaderText("Password = Username");
  }

  /**
   * Alerta que indica la existencia de campos vacíos.
   */
  public static void showEmptyFieldAlert() {
    cEmpty.showAndWait();
  }

  /**
   * Alerta que indica la existencia de un campo vacío, e indica cual campo es.
   * 
   * @param fieldName Campo vacío.
   */
  public static void showEmptyFieldAlert(String fieldName) {
    cEmpty.setHeaderText("Se requiere información en el campo " + fieldName + ".");
    cEmpty.showAndWait();
    cEmpty.setHeaderText("Existen campos vacíos");
  }

  /**
   * Alerta que indica que se usaron caracteres prohibidos.
   */
  public static void showCharForbidenAlert() {
    cForbi.showAndWait();
  }

  /**
   * Alerta que indica que se usaron caracteres prohibidos, adicionalmente indica
   * el primer campo donde se encuentra el uso de caracteres prohibidos.
   * 
   * @param fieldName
   */
  public static void showCharForbidenAlert(String fieldName) {
    cForbi.setHeaderText("Se detectó el uso de caracteres prohibidos en el campo " + fieldName);
    cForbi.showAndWait();
    cForbi.setHeaderText("Se detectó el uso de caracteres prohibidos");
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

  /**
   * Alerta que muestra que existe alguna incongruencia con la tarjeta.
   */
  public static void showCardUnexist() {
    cardUnexist.showAndWait();
  }

  /**
   * Alerta que indica que un campo no tiene formato numérico.
   */
  public static void showNumericFormat() {
    numericFormat.showAndWait();
  }

  /**
   * Alerta que indica que un campo no tiene formato decimal.
   */
  public static void showDecimalFormat() {
    decimalFormat.showAndWait();
  }

  /**
   * Alerta que muestra que la fecha indicada no es válida dentro de algún
   * contexto.
   */
  public static void showFechaNoValida() {
    fechaNoValida.showAndWait();
  }

  /**
   * Alerta que indica que la petición de recogida fue generada con éxito.
   */
  public static void showPeticionExitosa() {
    peticionExitosa.showAndWait();
  }

  /**
   * Alerta que indica que la contraseña es igual al nomobre de usuario.
   */
  public static void showUsernameEqualPass() {
    usernameEqualPass.showAndWait();
  }
}
