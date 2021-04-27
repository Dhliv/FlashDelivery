package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utilities.Globals;
import utilities.Ventana;

public class OperadorRegister implements Initializable {
  private Ventana ventana;
  @FXML
  private TextField cedulaClienteT;
  @FXML
  private TextField cedulaDestinoT;
  @FXML
  private TextField direccionEntregaT;
  @FXML
  private ChoiceBox<String> sedeEnvioT;
  @FXML
  private TextField pesoPaqueteT;
  @FXML
  private ChoiceBox<String> metodoPagoT;
  @FXML
  private TextField valorPaqueteT;
  @FXML
  private Label labelImpuesto;
  @FXML
  private Label labelTotalCosto;
  @FXML
  private TextArea descripcionT;
  @FXML
  private CheckBox seguroChoice;
  @FXML
  private Button btnRegistrar;
  @FXML
  private Button btnRegresar;

  /**
   * Vuelve a la pantalla principal de Operador de Oficina.
   * 
   * @param event not used.
   */
  @FXML
  void goToOperadorOficina(ActionEvent event) {
    Globals.pantalla.close();
    ventana = new Ventana("operadorOficina", new OperadorOficina());
    try {
      ventana.start(Globals.pantalla);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  void registrarEnvio(ActionEvent event) {

  }

  /**
   * Inicializa algunos componentes gráficos.
   * 
   * @param location  not used.
   * @param resources not used.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ObservableList<String> sedes = FXCollections.observableArrayList();
    ObservableList<String> metodosPago = FXCollections.observableArrayList();
    ArrayList<String> mp = new ArrayList<>();
    mp.add("Efectivo");
    mp.add("Tarjeta de Crédito");
    mp.add("Tarjeda Débito");

    sedes.removeAll(sedes);
    sedes.addAll(Globals.getSedes());
    sedeEnvioT.getItems().addAll(sedes);

    metodosPago.removeAll(metodosPago);
    metodosPago.addAll(mp);
    metodoPagoT.getItems().addAll(metodosPago);
  }

}
