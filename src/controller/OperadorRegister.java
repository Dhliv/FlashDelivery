package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.sql.Date;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.EmpleadoDAO;
import model.RegistrarEnvio;
import utilities.GeneralAlerts;
import utilities.GeneralChecker;
import utilities.Globals;
import utilities.Ventana;

public class OperadorRegister implements Initializable {
  private String cedCliente;
  private String cedDestino;
  private String dirDestino;
  private String pesoPaquete;
  private String valorPaquete;
  private String descripcion;
  private Object sedeEnvio;
  private Object metodoPago;
  private Boolean seguro;
  private Double costo;
  private Double impuesto;
  private Integer id_sede;
  private String[] textos;
  private Object[] multOpcion;
  public String cedulaRemitente; // almacena la cedula del remitente para llenar campos a futuro
  public String cedulaDestinatario; // almacena la cedula del destinatario para llenar campos a futuro.
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

  public OperadorRegister() {
  }

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
    boolean charForbiden = false;
    boolean emptyCamps = false;

    getData();
    charForbiden = GeneralChecker.checkChar(textos);
    emptyCamps = GeneralChecker.checkEmpty(textos, multOpcion);

    if (!(charForbiden || emptyCamps)) {
      transformData();
      ingresarDatos();
      volver();
      GeneralAlerts.showRegSuccess();
    } else {
      if (charForbiden)
        GeneralAlerts.showCharForbidenAlert();
      else
        GeneralAlerts.showEmptyFieldAlert();
    }
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

    cedulaClienteT.setText(cedulaRemitente);
    cedulaDestinoT.setText(cedulaDestinatario);
  }

  /**
   * Obtiene los datos de los campos de registro y los guarda en listas.
   */
  private void getData() {
    cedCliente = cedulaClienteT.getText();
    cedDestino = cedulaDestinoT.getText();
    dirDestino = direccionEntregaT.getText();
    pesoPaquete = pesoPaqueteT.getText();
    valorPaquete = valorPaqueteT.getText();
    descripcion = descripcionT.getText();
    sedeEnvio = sedeEnvioT.getValue();
    metodoPago = metodoPagoT.getValue();
    seguro = seguroChoice.isSelected();

    textos = new String[6];
    multOpcion = new Object[2];

    textos[0] = cedCliente;
    textos[1] = cedDestino;
    textos[2] = dirDestino;
    textos[3] = pesoPaquete;
    textos[4] = valorPaquete;
    textos[5] = descripcion;

    multOpcion[0] = sedeEnvio;
    multOpcion[1] = metodoPago;
  }

  private void transformData() {
    id_sede = Globals.getIdSede(sedeEnvio.toString());
    costo = Double.parseDouble(valorPaquete);
    impuesto = 0.0;
  }

  private void ingresarDatos() {
    /*
    RegistrarEnvio.createEnvio(Date.valueOf(LocalDate.now()), metodoPago.toString(), costo, seguro, impuesto,
        dirDestino, id_sede, Globals.id_usuario, cedulaRemitente, cedulaDestinatario);
        */
  }

  /**
   * Vuelve a la ventana principal de Operador de Oficina.
   */
  private void volver() {
    Globals.pantalla.close();
    ventana = new Ventana("operadorOficina", new OperadorOficina());
    try {
      ventana.start(Globals.pantalla);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
