package controller.controls;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.RegistrarEnvio;
import model.RegistrarEnvio.TipoCliente;
import utilities.GeneralChecker;
import utilities.GeneralString;
import utilities.TextFieldRestrictions;
import utilities.View;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Controlador y constructor para el control Cliente
 * 
 * @author Julián Orejuela
 * @version 1.0, 21/09/2021
 */
public class Cliente extends Pane {
  @FXML
  private Label cedulaLabel;
  @FXML
  private TextField cedula;
  @FXML
  private TextField nombre;
  @FXML
  private TextField direccion;
  @FXML
  private TextField telefono;
  @FXML
  private TextField ciudad;

  private RegistrarEnvio envio;
  private TipoCliente tipo;
  private TextField[] textFields;
  public Thread t;

  public Cliente() {
    View.loadControlView("controls.cliente", this);
  }

  public void initialize(RegistrarEnvio envio, Thread t) {
    this.t = t;
    this.envio = envio;
    this.tipo = TipoCliente.valueOf(GeneralString.capitalizeFirstLetter(this.getId()));
    cedulaLabel.setText("Ingrese la cedula del " + this.getId() + ":");
    TextFieldRestrictions.textFieldNumeric(cedula);
    TextFieldRestrictions.textFieldNumeric(telefono);

    textFields = new TextField[] { cedula, nombre, direccion, telefono, ciudad };
    for (TextField textField : textFields)
      textField.setId(textField.getId() + " del " + this.getId());
  }

  /**
   * Método encargado de buscar un cliente en la DB y mostrarlo en pantalla si
   * existe
   */
  @FXML
  void onCedulaKeyPressed(KeyEvent event) {
    if (cedula.getText().trim().equals(""))
      return;

    Runnable r = () -> {
      model.Entities.Cliente cliente = null;
      cliente = envio.buscarCliente(cedula.getText(), tipo);
      if (cliente != null) {
        nombre.setText(cliente.nombre);
        ciudad.setText(cliente.ciudad);
        direccion.setText(cliente.direccion);
        telefono.setText(cliente.telefono);
      }
    };
    t = new Thread(r);
    t.start();
  }

  /**
   * Validar los campos y actualizar los datos del cliente en el Envio
   * 
   * @return true si todas las validaciones son correctas
   */
  public Boolean checkAndUpdateEnvio() {
    if (!GeneralChecker.checkTextFieldEmptyAndFC(textFields))
      return false;
    envio.setCliente(cedula.getText(), nombre.getText(), ciudad.getText(), direccion.getText(), telefono.getText(),
        tipo);
    return true;
  }

}
