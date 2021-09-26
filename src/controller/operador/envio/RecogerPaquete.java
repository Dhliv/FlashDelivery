package controller.operador.envio;

import model.Entities.Cliente;
import java.util.List;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Entities.Sede;

import utilities.Roles;
import utilities.GeneralChecker;
import utilities.TextFieldRestrictions;
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
  List<Empleado> auxiliar;

  public RecogerPaquete(model.RegistrarEnvio envio, Empleado operador) {
    this.envio = envio;
    this.operador = operador;
  }

  public void initialize() {
    ObservableList<String> sedes = FXCollections.observableArrayList();
    ObservableList<String> auxiliares = FXCollections.observableArrayList();

    sedes.removeAll(sedes);
    sedes.addAll(Sede.getSedesParsed());

    choiceSede.getItems().addAll(sedes);
    choiceSede.setValue(sedes.get(0).toString());

    auxiliares.removeAll(auxiliares);
    auxiliares.addAll(Empleado.getAuxiliaresByIdParsed(Sede.getSedes().get(0).getId()));
    choiceAuxiliar.getItems().addAll(auxiliares);
  }

  @FXML
  void selectSede(ActionEvent event) {
    var idSede = Sede.getIdSede(choiceSede.getValue());

    ObservableList<String> auxiliares = FXCollections.observableArrayList();
    auxiliares.removeAll(auxiliares);
    auxiliares.addAll(Empleado.getAuxiliaresByIdParsed(idSede));
    choiceAuxiliar.getItems().clear();
    choiceAuxiliar.getItems().addAll(auxiliares);
  }

  @FXML
  void atras(ActionEvent event) {
    if (event.getSource() == atrasPaquete)
      View.cambiar("operador.cliente", new OperadorRecoger(operador));
  }

  @FXML
  void finalizarEntrega(ActionEvent event) {
    insertData();
    goBack();
  }

  /**
   * Valida los datos obtenidos de los campos rellenables verificando que no haya campos sin llenar o que existan caracteres vacíos.
   * @return True si cumple las validaciones y no hay problemas, False de lo contrario.
   */
  private Boolean validateData(){
    String desc = txtAreaDescripcion.getText();
    String cedula = choiceAuxiliar.getValue() == null ? "" : Empleado.getCedulaAuxiliar(choiceAuxiliar.getValue());
    String idSede = choiceSede.getValue() == null ? "" : Integer.toString(Sede.getIdSede(choiceSede.getValue()));

    String[] campos = {desc, cedula, idSede};
    boolean camposVacios = GeneralChecker.checkEmpty(campos, new Object[0]);
    boolean forbiddenChar = GeneralChecker.checkChar(campos);

    if(camposVacios || forbiddenChar){
      if(camposVacios)
        SpecificAlerts.showEmptyFieldAlert();
      if(forbiddenChar)
        SpecificAlerts.showCharForbidenAlert();
      return Boolean.FALSE;
    }else return Boolean.TRUE;
  }

  public void insertData() {
    if(!validateData())
      return;
    
    String descripcion = txtAreaDescripcion.getText();
    // String cedulaAuxiliar = Empleado.getCedulaAuxiliar(choiceAuxiliar.getValue());
    // int idSede = Sede.getIdSede(choiceSede.getValue());

    Cliente.createCliente(envio.getRemitente());
    Cliente.createCliente(envio.getDestinatario());
    model.Peticion.createPeticion(descripcion, operador.getCedula(), envio.getRemitente().cedula, envio.getDestinatario().cedula);
  
  }

  private void goBack() {
    View.clearViews();
    View.cambiar("operador.cliente", new RegistrarClientes(operador));
  }
}
