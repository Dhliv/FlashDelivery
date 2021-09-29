package controller.operador.verPaquetes;

import java.util.*;

import javax.swing.JOptionPane;

import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Envio;
import model.Entities.*;
import utilities.View;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.collections.*;

public class OperadorConsulta implements Initializable {

  @FXML
  private TableView<Envio> tPaquetes;
  @FXML
  private TableColumn<Envio, Date> tcFechaRegistro;
  @FXML
  private TableColumn<Envio, String> tcMetodoPago;
  @FXML
  private TableColumn<Envio, String> tcDireccionEntrega;
  @FXML
  private TableColumn<Envio, String> tcRemitente;
  @FXML
  private TableColumn<Envio, String> tcDestinatario;
  @FXML
  private TableColumn<Envio, Boolean> tcEstado;
  private Empleado e;
  private Envio en;

  public OperadorConsulta(Empleado e) {
    this.e = e;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tcFechaRegistro.setCellValueFactory(new PropertyValueFactory<Envio, Date>("fecha_registro"));
    tcMetodoPago.setCellValueFactory(new PropertyValueFactory<Envio, String>("metodo_pago"));
    tcDireccionEntrega.setCellValueFactory(new PropertyValueFactory<Envio, String>("direccion_entrega"));
    tcRemitente.setCellValueFactory(new PropertyValueFactory<Envio, String>("cliente_envio"));
    tcDestinatario.setCellValueFactory(new PropertyValueFactory<Envio, String>("cliente_entrega"));
    tcEstado.setCellValueFactory(new PropertyValueFactory<Envio, Boolean>("delivered"));

    ArrayList<Envio> enviosAux = (ArrayList<Envio>) Envio.getEnviosBySede(e.id_sede);
    ObservableList<Envio> envios = FXCollections.observableArrayList(enviosAux);
    tPaquetes.setItems(envios);
  }

  @FXML
  void seleccionarEnvio(MouseEvent event) {
    
    int index = tPaquetes.getSelectionModel().getSelectedIndex();
    if (index != -1) {
      en = tPaquetes.getSelectionModel().getSelectedItem();
      int op = JOptionPane.showConfirmDialog(null, "¿Desea ver los datos de este paquete?");
      if(op == JOptionPane.YES_OPTION){
        View.newView("operador.ver.paquete", new operadorVerInfo(en));
        
      }
      
    }
    tPaquetes.getSelectionModel().clearSelection();
  }

}