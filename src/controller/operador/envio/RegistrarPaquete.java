package controller.operador.envio;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.Entities.Empleado;
import utilities.Globals;
import utilities.SpecificAlerts;
import utilities.TextFieldRestrictions;
import utilities.View;

public class RegistrarPaquete {

  @FXML
  private JFXButton atrasPaquete;
  @FXML
  private TextArea txtDescripcion;
  @FXML
  private JFXTextField txtAncho;
  @FXML
  private JFXTextField txtAlto;
  @FXML
  private JFXTextField txtLargo;
  @FXML
  private JFXTextField txtPeso;
  @FXML
  private JFXTextField txtValor;
  @FXML
  private JFXCheckBox checkSeguro;

  private model.RegistrarEnvio envio;
  private Empleado operador;

  public RegistrarPaquete(model.RegistrarEnvio envio, Empleado operador) {
    this.envio = envio;
    this.operador = operador;
    System.out.println("constructor Registrar Paquete: " + envio.getDestinatario().cedula);
  }

  public void initialize() {
    Globals.style.setParent(txtDescripcion);
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

  boolean agregarPaquete() {
    try { // TODO Faltan validaciones
      Double peso = Double.parseDouble(txtPeso.getText());
      Double valor = Double.parseDouble(txtValor.getText());
      String descripcion = txtDescripcion.getText();
      if (descripcion.trim().equals(""))
        return false;
      Double ancho = Double.parseDouble(txtAncho.getText());
      Double largo = Double.parseDouble(txtLargo.getText());
      Double alto = Double.parseDouble(txtAlto.getText());
      Boolean seguro = checkSeguro.isSelected();
      envio.agregarPaqueteP(peso, valor, descripcion, ancho, largo, alto, seguro);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Verifica que todos los campos num??ricos/decimales cumplan el formato
   * esperado.
   * 
   * @return True de no presentar problemas, false si existe un campo con formato
   *         picho.
   */
  private boolean checkFormatosEnCampos() {
    String campos[] = { txtPeso.getText(), txtValor.getText(), txtAncho.getText(), txtLargo.getText(),
        txtAlto.getText() };
    return TextFieldRestrictions.checkDecimalExpression(campos);
  }

  @FXML
  void resumenEnvio(ActionEvent event) {

    if (!agregarPaquete() || !checkFormatosEnCampos()) {
      if (!agregarPaquete())
        JOptionPane.showMessageDialog(null, "No ha ingresado ning??n paquete");
      if (!checkFormatosEnCampos())
        SpecificAlerts.showDecimalFormat();
    } else {
      System.out.println("Registrar paquete: " + envio.getDestinatario().cedula);
      View.newView("operador.resumen", new OperadorResumen(envio, operador));
    }

  }

  @FXML
  void limpiarCampos(ActionEvent event) {
    clearFieldsPaquetes();
  }

  @FXML
  void superPrueba(KeyEvent event) {
    System.out.println("Ser?? que esto s?? funciona as???");
  }
}
