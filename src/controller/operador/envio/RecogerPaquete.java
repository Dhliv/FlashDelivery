package controller.operador.envio;

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

import utilities.TextFieldRestrictions;
import utilities.View;

/**
 * Controlador de la vista operador.recoger
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
    auxiliar = Empleado.getEmpleadosHabilitados();
    for(int i=0; i<auxiliar.size(); i++){
      if(Roles.rol[Roles.AUXILIAR] == auxiliar.get(i).getRol())  //Revisa si es auxiliar.
        auxiliares.add(auxiliar.get(i).getNombres() + " " + auxiliar.get(i).getApellidos());
      else{
        auxiliar.remove(i);
        --i;
      }
    }
    choiceAuxiliar.getItems().addAll(auxiliares);
  }

  @FXML
  void selectSede(ActionEvent event){
    var idSede = Sede.getIdSede(choiceSede.getValue());
    ObservableList<String> auxiliares = FXCollections.observableArrayList();
    auxiliares.removeAll(auxiliares);
    for(int i=0; i<auxiliar.size(); i++){
      if(idSede == auxiliar.get(i).getSede())  //Revisa si es auxiliar.
        auxiliares.add(auxiliar.get(i).getNombres() + " " + auxiliar.get(i).getApellidos());
    }
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

  //TODO INSERTAR DATOS EN BD
  public void insertData(){
    txtAreaDescripcion.getText();
    choiceAuxiliar.getValue();
    choiceSede.getValue();
    envio.getDestinatario();
    envio.getRemitente();
  }

  private void goBack() {
    View.clearViews();
    View.cambiar("operador.cliente", new RegistrarClientes(operador));
  }
}
