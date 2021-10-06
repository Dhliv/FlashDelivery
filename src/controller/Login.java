package controller;

import model.Entities.Empleado;
import model.Entities.Usuario;
import model.Entities.Usuario.UsuarioInhabilitado;
import utilities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;
import controller.gerente.Admin;
import controller.operador.OperadorOficina;

public class Login {
  private String user;

  @FXML
  private Button btIngresar;
  @FXML
  private PasswordField txtPass;
  @FXML
  private TextField txtUsuario;

  /**
   * Metodo para ejecutar el login de forma alternativa; en lugar de usar el
   * botón, se usa la tecla Enter
   * 
   * @param event
   * @throws Exception
   */
  @FXML
  void loginKeyboard(KeyEvent event) throws Exception {
    // System.out.println(event);
    KeyCode key = event.getCode();
    if (key.equals(KeyCode.ENTER)) {
      login();
    }
  }

  /**
   * Método del botón login
   * 
   * @param event evento causado por el botón login
   * @throws Exception
   */
  @FXML
  void clicksoide(ActionEvent event) throws Exception {
    login();
  }

  /**
   * Método para iniciar sesión en los distintos roles dado un usuario y una
   * contraseña.
   * 
   * @throws Exception
   */
  void login() throws Exception {

    user = txtUsuario.getText();
    String pass = txtPass.getText();
    int acc = -2;
    try {
      acc = Usuario.entradaUsuario(user, pass);
      // siguientes codigos
    } catch (UsuarioInhabilitado e) {
      JOptionPane.showMessageDialog(null, "Ud. no se encuentra habilitado en el sistema");
    }

    if (acc == -2)
      JOptionPane.showMessageDialog(null, "Ud. no se encuentra habilitado en el sistema");
    else if (acc == -1)
      SpecificAlerts.showBadLogin();
    else if (acc == 0) {
      System.out.println("sql error");
    } else {
      // JOptionPane.showMessageDialog(null, "Entraste!");
      // System.out.println("entre");
      Empleado userActual = Empleado.cargarEmpleado(acc + "");
      var rolAcc = userActual.getRol();

      Globals.pantalla.close();
      Ventana vent;

      if (rolAcc.equals(Roles.rol[Roles.ADMIN])) {
        vent = new Ventana("admin", new Admin(user));
        vent.start(Globals.pantalla);
      } else if (rolAcc.equals(Roles.rol[Roles.AUXILIAR])) {
        vent = new Ventana("auxiliar", new Auxiliar(userActual));
        vent.start(Globals.pantalla);
      } else if (rolAcc.equals(Roles.rol[Roles.CONTADOR])) {
        // vent = new Ventana("operadorOficina", new OperadorOficina());
        // vent.start(Globals.pantalla);
        JOptionPane.showMessageDialog(null, "NO HA SIDO IMPLEMENTADO");
      } else if (rolAcc.equals(Roles.rol[Roles.OPERADOR])) {
        vent = new Ventana("operadorOficina", new OperadorOficina(userActual));
        vent.start(Globals.pantalla);
      } else if (rolAcc.equals(Roles.rol[Roles.SECRETARIO])) {
        vent = new Ventana("contador", new Admin(user));
      }
    }
  }

}
