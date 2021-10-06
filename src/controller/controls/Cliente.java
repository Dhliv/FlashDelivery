package controller.controls;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import model.RegistrarEnvio;
import model.RegistrarEnvio.TipoCliente;
import utilities.GeneralChecker;
import utilities.GeneralString;
import utilities.TextFieldRestrictions;
import utilities.View;

import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.Timer;

import javafx.fxml.FXML;

/**
 * Controlador y constructor para el control Cliente
 * 
 * @author Julián Orejuela
 * @version 1.0, 21/09/2021
 */
public class Cliente extends Pane {
  @FXML private Label cedulaLabel;
  @FXML private TextField cedula;
  @FXML private TextField nombre;
  @FXML private TextField direccion;
  @FXML private TextField telefono;
  @FXML private TextField ciudad;

  private RegistrarEnvio envio;
  private TipoCliente tipo;
  private TextField[] textFields;
  public SuperTimer st;
  public Queue<String> cola;

  public Cliente() {
    View.loadControlView("controls.cliente", this);
  }

  public void initialize(RegistrarEnvio envio) {
    this.st = new SuperTimer(100, e -> accionTimer());
    this.cola = new LinkedList<>();
    this.envio = envio;
    this.tipo = TipoCliente.valueOf(GeneralString.capitalizeFirstLetter(this.getId()));
    cedulaLabel.setText("Ingrese la cedula del " + this.getId() + ":");
    TextFieldRestrictions.textFieldNumeric(cedula);
    TextFieldRestrictions.textFieldNumeric(telefono);

    textFields = new TextField[] { cedula, nombre, direccion, telefono, ciudad };
    for (TextField textField : textFields)
      textField.setId(textField.getId() + " del " + this.getId());
    st.start();
  }

  /**
   * Método encargado de buscar un cliente en la DB y mostrarlo en pantalla si existe
   */
  @FXML void onCedulaTextChanged(KeyEvent event) {
    if (cedula.getText().trim().equals("")) return;
    System.out.println(cedula.getText());

    if (cola.isEmpty()) {
      cola.add(cedula.getText());
    } else if (!cola.peek().equals(cedula.getText())) {
      cola.add(cedula.getText());
    }

  }

  public void accionTimer() {
    if (st.inSearch) {
      System.out.println("Ya hay una búsqueda en ejecución");
      return;
    }
    if (cola.isEmpty()) {
      st.inExecution = false;
      return;
    }
    st.inExecution = true;
    System.out.println("Buscando a " + cola.peek());

    st.inSearch = true;
    model.Entities.Cliente cliente = null;
    String cedula = cola.poll();
    cliente = envio.buscarCliente(cedula, tipo);
    if (cliente != null) {
      nombre.setText(cliente.nombre);
      ciudad.setText(cliente.ciudad);
      direccion.setText(cliente.direccion);
      telefono.setText(cliente.telefono);
    }
    st.inSearch = false;
  }

  /**
   * Validar los campos y actualizar los datos del cliente en el Envio
   * 
   * @return true si todas las validaciones son correctas
   */
  public Boolean checkAndUpdateEnvio() {
    if (!GeneralChecker.checkTextFieldEmptyAndFC(textFields)) return false;
    envio.setCliente(cedula.getText(), nombre.getText(), ciudad.getText(), direccion.getText(), telefono.getText(), tipo);
    return true;
  }

  public void stopBusqueda() {
    st.stop();
  }

  /**
   * !Este timer va a arreglar todos nuestros problemas
   */
  public class SuperTimer extends Timer {
    public boolean inSearch;
    public boolean inExecution;

    public SuperTimer(int delay, ActionListener listener) {
      super(delay, listener);
      inSearch = false;
      inExecution = false;
    }

    @Override public void stop() {
      Thread t = new Thread(() -> {
        while (inExecution) {
          try {
            Thread.sleep(50);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        super.stop();
        System.out.println(isRunning());
      });
      t.start();
    }

  }

  public void restart() {
    st.start();
  }
}
