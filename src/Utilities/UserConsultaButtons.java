package utilities;

import controller.UserEdit;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import model.*;

/*Carga en pantalla la pestaña editar si es posible
  Tabien se hacen las validaciones pertinentes para entrar.
*/
public class UserConsultaButtons {

  private GeneralAlerts userAlert;
  private Parent userModify;

  public void goToUserEdit(Usuario user, AnchorPane content) {
    userAlert = new GeneralAlerts();
    if (user == null)
      userAlert.showUserNullAlert();
    else {
      content.getChildren().clear();
      userModify = Globals.loadView("user.edit", new UserEdit(content, this));
      content.getChildren().addAll(userModify);
    }
  }
}
