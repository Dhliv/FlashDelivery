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

public class RegistrarClientes {
  @FXML
  private Button atrasCliente;
  @FXML
  private TextField RCedula;
  @FXML
  private TextField RNombre;
  @FXML
  private TextField RDireccion;
  @FXML
  private TextField RTelefono;
  @FXML
  private TextField RCiudad;
  @FXML
  private TextField DCedula;
  @FXML
  private TextField DNombre;
  @FXML
  private TextField DDireccion;
  @FXML
  private TextField DTelefono;
  @FXML
  private TextField DCiudad;

  private model.RegistrarEnvio envio;
  private Empleado operador;

  public RegistrarClientes(Empleado operador) {
    this.operador = operador;
  }

  public void initialize() {
    if (envio == null)
      envio = new model.RegistrarEnvio();
    TextFieldRestrictions.textFieldNumeric(RCedula);
    TextFieldRestrictions.textFieldNumeric(DCedula);
    TextFieldRestrictions.textFieldNumeric(RTelefono);
    TextFieldRestrictions.textFieldNumeric(DTelefono);

  }

  @FXML
  void atras(ActionEvent event) {

  }

  @FXML
  void onActionDestinatario(KeyEvent event) {
    // Se crea un hilo sobreescribiendo el método run que ejecute la búsqueda en la
    // base de datos
    // Para no sobrecargar la interfaz gráfica
    Runnable r = () -> {
      onChangeCedula(DCedula, DNombre, DCiudad, DDireccion, DTelefono, TipoCliente.Destinatario);
    };
    new Thread(r).start();
  }

  @FXML
  void onActionRemitente(KeyEvent event) {
    // Se crea un hilo sobreescribiendo el método run que ejecute la búsqueda en la
    // base de datos
    // Para no sobrecargar la interfaz gráfica
    Runnable r = () -> {
      onChangeCedula(RCedula, RNombre, RCiudad, RDireccion, RTelefono, TipoCliente.Remitente);
    };
    new Thread(r).start();
  }

  private synchronized void onChangeCedula(TextField Cedula, TextField Nombre, TextField Ciudad, TextField Direccion,
      TextField Telefono, TipoCliente tipo) {
    if (Cedula.getText().trim().equals(""))
      return;
    Cliente cliente = envio.buscarCliente(Cedula.getText(), tipo);
    Nombre.setText(cliente != null ? cliente.nombre : "");
    Ciudad.setText(cliente != null ? cliente.ciudad : "");
    Direccion.setText(cliente != null ? cliente.direccion : "");
    Telefono.setText(cliente != null ? cliente.telefono : "");
    Nombre.setEditable(cliente == null);
    Ciudad.setEditable(cliente == null);
    Direccion.setEditable(cliente == null);
    Telefono.setEditable(cliente == null);
  }

  @FXML
  void registrarPaquetes(ActionEvent event) {
    envio.setCliente(RCedula.getText(), RNombre.getText(), RCiudad.getText(), RDireccion.getText(), RTelefono.getText(),
        TipoCliente.Remitente);
    envio.setCliente(DCedula.getText(), DNombre.getText(), DCiudad.getText(), DDireccion.getText(), DTelefono.getText(),
        TipoCliente.Destinatario);
    if (envio.getRemitente() == null || envio.getDestinatario() == null) {
      JOptionPane.showMessageDialog(null, "Debe ingresar el remitente y el destinatario completamente");
    } else {
      Globals.cambiarVista("operador.paquetes", new RegistrarPaquete(envio, operador));
    }

  }
}
