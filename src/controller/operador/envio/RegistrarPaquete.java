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
import utilities.View;

public class RegistrarPaquete {
  @FXML
  private Button atrasPaquete;
  @FXML
  private Button btRegistrarEnvios;
  @FXML
  private Button btInsertar;
  @FXML
  private Button btBorrar;
  @FXML
  private TextArea txtDescripcion;
  @FXML
  private TextField txtAncho;
  @FXML
  private TextField txtAlto;
  @FXML
  private TextField txtLargo;
  @FXML
  private Button btModificar;
  @FXML
  private CheckBox checkSeguro;
  @FXML
  private TextField txtPeso;
  @FXML
  private TextField txtValor;
  @FXML
  private TextArea txtReporte;

  private boolean modify; // Boolean que indica si se está modificando un paquete o no

  private model.RegistrarEnvio envio;
  private Empleado operador;

  public RegistrarPaquete(model.RegistrarEnvio envio, Empleado operador) {
    this.envio = envio;
    this.operador = operador;
  }

  /**
   * Llena con datos el paquete para no tener que escibirlos cada maldita vez que
   * queramos ensayar cosas en estas ventanas. Buenas noches.
   */
  private void fillDeafult() {
    txtPeso.setText("1");
    txtValor.setText("1");
    txtDescripcion.setText("1");
    txtAlto.setText("1");
    txtLargo.setText("1");
    txtAncho.setText("1");
  }

  public void initialize() {
    TextFieldRestrictions.textFieldNumeric(txtPeso);
    TextFieldRestrictions.textFieldNumeric(txtValor);
    TextFieldRestrictions.textFieldNumeric(txtAncho);
    TextFieldRestrictions.textFieldNumeric(txtLargo);
    TextFieldRestrictions.textFieldNumeric(txtAlto);
    // txtReporte.setText("xdxdxd");
    fillDeafult();
    modify = false;
  }

  @FXML
  void atras(ActionEvent event) {
    if (event.getSource() == atrasPaquete)
      View.cambiar("operador.cliente");
  }

  private void clearFieldsPaquetes() {
    txtPeso.setText("");
    txtValor.setText("");
    txtDescripcion.setText("");
    txtAlto.setText("");
    txtLargo.setText("");
    txtAncho.setText("");
  }

  @FXML
  void borrarPaquete(ActionEvent event) {
    envio.eliminarPaquete();
    clearFieldsPaquetes();
  }

  @FXML
  void insertarPaquete(ActionEvent event) {
    if (agregarPaquete()) {
      JOptionPane.showMessageDialog(null, "El paquete ha sido ingresado");
    } else {
      SpecificAlerts.showErrorUnexpt();
    }
  }

  @FXML
  void modificarPaquete(ActionEvent event) {
    if (modify) {
      if (agregarPaquete()) {
        JOptionPane.showMessageDialog(null, "El paquete ha sido modificado");
      } else {
        modify = false;
        btBorrar.setText("Modificar paquete");
      }
    } else {
      Paquete p = envio.getPaquete();
      txtPeso.setText(p.peso + "");
      txtValor.setText(p.valor + "");
      txtDescripcion.setText(p.descripcion);
      txtAncho.setText(p.ancho + "");
      txtLargo.setText(p.largo + "");
      txtAlto.setText(p.alto + "");
      checkSeguro.setSelected(p.seguro);
      btBorrar.setText("Confirmar modificación");
      modify = true;
    }

  }

  boolean agregarPaquete() {
    try { // Faltan validaciones
      Integer peso = Integer.parseInt(txtPeso.getText());
      Integer valor = Integer.parseInt(txtValor.getText());
      String descripcion = txtDescripcion.getText();
      Integer ancho = Integer.parseInt(txtAncho.getText());
      Integer largo = Integer.parseInt(txtLargo.getText());
      Integer alto = Integer.parseInt(txtAlto.getText());
      Boolean seguro = checkSeguro.isSelected();
      envio.agregarPaqueteP(peso, valor, descripcion, ancho, largo, alto, seguro);
      clearFieldsPaquetes();
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  @FXML
  void resumenEnvio(ActionEvent event) {
    if (envio.getPaquete() == null || modify)
      JOptionPane.showMessageDialog(null, "No ha ingresado ningún paquete");
    else
      View.cambiar("operador.resumen", new OperadorResumen(envio, operador));

  }

  @FXML
  void superPrueba(KeyEvent event) {
    System.out.println("Será que esto sí funciona así?");
  }
}
