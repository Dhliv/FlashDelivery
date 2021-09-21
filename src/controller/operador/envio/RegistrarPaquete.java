package controller.operador.envio;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Entities.Empleado;
import model.Entities.Paquete;
import utilities.SpecificAlerts;
import utilities.Globals;
import utilities.TextFieldRestrictions;

public class RegistrarPaquete {

  @FXML
  private Button atrasPaquete;

  @FXML
  private Button btRegistrarEnvios;

  @FXML
  private TextArea txtDescripcion;

  @FXML
  private TextField txtAncho;

  @FXML
  private TextField txtAlto;

  @FXML
  private TextField txtLargo;

  @FXML
  private CheckBox checkSeguro;

  @FXML
  private TextField txtPeso;

  @FXML
  private TextField txtValor;

  @FXML
  private Button btBorrar;

  private model.RegistrarEnvio envio;
  private Empleado operador;

  public RegistrarPaquete(model.RegistrarEnvio envio, Empleado operador) {
    this.envio = envio;
    this.operador = operador;
  }

  public void initialize() {
    TextFieldRestrictions.textFieldNumeric(txtPeso);
    TextFieldRestrictions.textFieldNumeric(txtValor);
    TextFieldRestrictions.textFieldNumeric(txtAncho);
    TextFieldRestrictions.textFieldNumeric(txtLargo);
    TextFieldRestrictions.textFieldNumeric(txtAlto);
  }

  @FXML
  void atras(ActionEvent event) {
    if (event.getSource() == atrasPaquete)
      Globals.cambiarVista("operador.cliente", this);
  }

  private void clearFieldsPaquetes() {
    txtPeso.setText("");
    txtValor.setText("");
    txtDescripcion.setText("");
    txtAlto.setText("");
    txtLargo.setText("");
    txtAncho.setText("");
  }



  boolean agregarPaquete() {
    try { // Faltan validaciones
      Integer peso = Integer.parseInt(txtPeso.getText());
      Integer valor = Integer.parseInt(txtValor.getText());
      String descripcion = txtDescripcion.getText();
      if (descripcion.trim().equals(""))
        return false;
      Integer ancho = Integer.parseInt(txtAncho.getText());
      Integer largo = Integer.parseInt(txtLargo.getText());
      Integer alto = Integer.parseInt(txtAlto.getText());
      Boolean seguro = checkSeguro.isSelected();
      envio.agregarPaqueteP(peso, valor, descripcion, ancho, largo, alto, seguro);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  @FXML
  void resumenEnvio(ActionEvent event) {

    if (!agregarPaquete())
      JOptionPane.showMessageDialog(null, "No ha ingresado ningún paquete");
    else
      Globals.cambiarVista("operador.resumen", new OperadorResumen(envio, operador));

  }
  @FXML
  void limpiarCampos(ActionEvent event) {
    clearFieldsPaquetes();
  }
  @FXML
  void superPrueba(KeyEvent event) {
    System.out.println("Será que esto sí funciona así?");
  }
}
