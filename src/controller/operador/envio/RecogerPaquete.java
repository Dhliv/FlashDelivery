package controller.operador.envio;

import model.Entities.Cliente;
import java.util.List;

import controller.operador.verPaquetes.OperadorConsulta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Entities.Empleado;
import model.Entities.Sede;
import utilities.GeneralChecker;
import utilities.View;
import utilities.SpecificAlerts;

/**
 * Controlador de la vista operador.recoger
 * 
 * @author Alejandro pergueza
 * @version 0.1
 * @since 25/09/2021
 */
public class RecogerPaquete {
  @FXML private Button atrasPaquete;

  @FXML private TextArea txtAreaDescripcion;

  @FXML private Label txtAuxiliar;

  @FXML private ComboBox<String> choiceAuxiliar;

  @FXML private Label txtSede;

  @FXML private ComboBox<String> choiceSede;

  private model.RegistrarEnvio envio;
  private Empleado operador;
  List<Empleado> auxiliar;

  public RecogerPaquete(model.RegistrarEnvio envio, Empleado operador) {
    this.envio = envio;
    this.operador = operador;
  }

  public void initialize() {
    ObservableList<String> sedes = FXCollections.observableArrayList();
    ObservableList<String> auxiliares = FXCollections.observableArrayList();

    // Se añaden los datos pertinentes a las colecciones
    sedes.addAll(Sede.getSedesParsed());
    auxiliares.addAll(Empleado.getAuxiliaresBySedeParsed(Sede.getSedes().get(0).getId()));

    // Se insertan las colecciones en los choiceBox
    choiceSede.getItems().addAll(sedes);
    choiceAuxiliar.getItems().addAll(auxiliares);

    // Se selecciona por defecto la sede 1.
    choiceSede.setValue(sedes.get(0).toString());
  }

  /**
   * El nombre no es muy representativo. Se cambia la información del choiceBox de Auxiliares Por uno
   * que sea acorde a la nueva sede seleccionada
   * @param event Seleccionar una sede diferente.
   */
  @FXML void selectSede(ActionEvent event) {
    ObservableList<String> auxiliares = FXCollections.observableArrayList();

    choiceAuxiliar.getItems().clear();
    auxiliares.addAll(Empleado.getAuxiliaresBySedeParsed(Sede.getIdSede(choiceSede.getValue())));
    choiceAuxiliar.getItems().addAll(auxiliares);
  }

  @FXML void atras(ActionEvent event) {
    if (event.getSource() == atrasPaquete) View.cambiar("operador.cliente", new OperadorRecoger(operador));
  }

  /**
   * Inserta la información registrada por el usuario en la BD.
   */
  @FXML void finalizarEntrega(ActionEvent event) {
    if (!validateData()) // Comprueba si hay errores y lanza alertas independientemente del caso
      return;

    Cliente.createCliente(envio.getRemitente());
    Cliente.createCliente(envio.getDestinatario());

    model.Peticion.createPeticion(txtAreaDescripcion.getText(), // descripcion
        operador.getCedula(), // Cedula operador
        envio.getRemitente().cedula, // cedula remitente
        envio.getDestinatario().cedula); // Cedula destinatario

    goBack();
  }

  /**
   * Valida los datos obtenidos de los campos rellenables verificando que no haya campos sin llenar o
   * que existan caracteres vacíos. Además, muestra en pantalla las alertas correspondientes en cada
   * caso.
   * 
   * @return True si cumple las validaciones y no hay problemas, False de lo contrario.
   */
  private Boolean validateData() {

    String[] campos = { txtAreaDescripcion.getText(), // Descripción
        choiceAuxiliar.getValue(), // Cedula Auxiliar
        Integer.toString(Sede.getIdSede(choiceSede.getValue())) // Sede que recoge
    };

    // Se revisa que los campos cumplan con las restricciones.
    boolean camposVacios = GeneralChecker.checkEmpty(campos, new Object[0]);
    boolean forbiddenChar = GeneralChecker.checkChar(campos);

    // Popean los errores existentes
    if (camposVacios || forbiddenChar) {
      if (camposVacios) SpecificAlerts.showEmptyFieldAlert();
      if (forbiddenChar) SpecificAlerts.showCharForbidenAlert();
      return false;
    }

    SpecificAlerts.showPagoExitoso();
    return true;
  }

  private void goBack() {
    View.clearViews();
    View.newView("operadorOficinaTabla", new OperadorConsulta(operador));
  }
}
