package controller.operador.envio;

import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.Entities.Empleado;

import utilities.TextFieldRestrictions;
import utilities.View;

//TODO TERMINAR ESTA CLASE Y SUS FUNCIONALIDADES.
public class RecogerPaquete {
  @FXML
  private Button atrasPaquete;

  @FXML
  private TextArea txtAreaDescripcion;

  @FXML
  private Label txtAuxiliar;

  @FXML
  private ComboBox<String> choiceAuxiliar;

  @FXML
  private Label txtSede;

  @FXML
  private ComboBox<String> choiceSede;


  private model.RegistrarEnvio envio;
  private Empleado operador;

  public RecogerPaquete(model.RegistrarEnvio envio, Empleado operador) {
    this.envio = envio;
    this.operador = operador;
    System.out.println("constructor Recoger Paquete: " + envio.getDestinatario().cedula);
  }

  public void initialize() {
    
  }

  @FXML
  void atras(ActionEvent event) {
    if (event.getSource() == atrasPaquete)
      View.cambiar("operador.cliente");
  }

  @FXML
  void finalizarEntrega(ActionEvent event) {
    goBack();
  }

  private void goBack() {
    View.clearViews();
    View.cambiar("operador.cliente", new RegistrarClientes(operador));
  }
}
