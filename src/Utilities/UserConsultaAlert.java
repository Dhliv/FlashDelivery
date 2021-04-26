package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

//Esta clase guarda todas las alertas destinadas para el espacio de Consulta de usuario.
public class UserConsultaAlert {

  //userNull = Mensaje que dice si no se ha seleccionado un usuario en consulta.
  private Alert userNull;
  public UserConsultaAlert(){
    userNull = new Alert(AlertType.NONE);
    userNull.setAlertType(AlertType.WARNING);
    userNull.setContentText("Por favor, seleccione el usuario que desea modificar.");
    userNull.setTitle("Usuario no seleccionado");
    userNull.setHeaderText("No ha sido seleccionado un usuario");
  }

  public void showUserNullAlert(){
    userNull.show();
  }
}
