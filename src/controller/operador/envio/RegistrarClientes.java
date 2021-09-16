package controller.operador.envio;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import model.Entities.Cliente;
import model.Entities.Empleado;
import model.RegistrarEnvio.TipoCliente;
import utilities.Globals;
import utilities.TextFieldRestrictions;

/**
 * Controlador para la vista operador.cliente
 * @author Julián Orejuela
 * @version 1.2, 13/9/2021
 */
public class RegistrarClientes {
  // Remitente
  @FXML private TextField RCedula;
  @FXML private TextField RNombre;
  @FXML private TextField RDireccion;
  @FXML private TextField RTelefono;
  @FXML private TextField RCiudad;
  // Destinatario
  @FXML private TextField DCedula;
  @FXML private TextField DNombre;
  @FXML private TextField DDireccion;
  @FXML private TextField DTelefono;
  @FXML private TextField DCiudad;

  private model.RegistrarEnvio envio;
  private Empleado operador;

  public RegistrarClientes(Empleado operador) {
    envio = new model.RegistrarEnvio();
    this.operador = operador;
  }

  public void initialize() {
    TextFieldRestrictions.textFieldNumeric(RCedula);
    TextFieldRestrictions.textFieldNumeric(DCedula);
    TextFieldRestrictions.textFieldNumeric(RTelefono);
    TextFieldRestrictions.textFieldNumeric(DTelefono);
  }

  /**
   * Manejador del evento onKeyPressed del TextField #DCedula
   */
  @FXML void onKeyPressedDestinatario(KeyEvent event) {
    onChangeCedula(DCedula, DNombre, DCiudad, DDireccion, DTelefono, TipoCliente.Destinatario);
  }

  /**
   * Manejador del evento onKeyPressed del TextField #RCedula
   */
  @FXML void onKeyPressedRemitente(KeyEvent event) {
    onChangeCedula(RCedula, RNombre, RCiudad, RDireccion, RTelefono, TipoCliente.Remitente);
  }

  private synchronized void onChangeCedula(TextField Cedula, TextField Nombre, TextField Ciudad, TextField Direccion, TextField Telefono, TipoCliente tipo) {
    if (Cedula.getText() == "") // Cedula.getText().trim().equals("")
      return;
    Runnable r = () -> {
      Cliente cliente = envio.buscarCliente(Cedula.getText(), tipo);
      if (cliente != null) {
        Nombre.setText(cliente.nombre);
        Ciudad.setText(cliente.ciudad);
        Direccion.setText(cliente.direccion);
        Telefono.setText(cliente.telefono);
      }
    };
    new Thread(r).start();
  }

  @FXML void registrarPaquetes(ActionEvent event) {
    envio.setCliente(RCedula.getText(), RNombre.getText(), RCiudad.getText(), RDireccion.getText(), RTelefono.getText(), TipoCliente.Remitente);
    envio.setCliente(DCedula.getText(), DNombre.getText(), DCiudad.getText(), DDireccion.getText(), DTelefono.getText(), TipoCliente.Destinatario);

    if (!envio.checkClientes())
      // no se puede proceder con los datos que se han ingresado hasta el momento
      JOptionPane.showMessageDialog(null, "Debe ingresar los datos de los clientes antes de continuar.");
    else
      Globals.cambiarVista("operador.paquetes", new RegistrarPaquete(envio, operador));
  }
}
