package controller.operador;

import java.util.*;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Entities.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.*;

public class OperadorConsulta implements Initializable {

  @FXML
  private TableView<Paquete> tPaquetes;
  @FXML
  private TableColumn<Paquete, String> tcDestinatario;
  @FXML
  private TableColumn<Paquete, Integer> tcIdPaquete;
  @FXML
  private TableColumn<Paquete, Integer> tcPesoPaquete;
  @FXML
  private TableColumn<Paquete, Boolean> tcEstado;
  private Empleado e;

  public OperadorConsulta(Empleado e) {
    this.e = e;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    System.out.println(tPaquetes);
    ArrayList<Paquete> pq = (ArrayList<Paquete>) Paquete.queryPaquetesSede(e.getSede());

    tcDestinatario.setCellValueFactory(new PropertyValueFactory<Paquete, String>("destinatario"));
    tcIdPaquete.setCellValueFactory(new PropertyValueFactory<Paquete, Integer>("idpaquete"));
    tcPesoPaquete.setCellValueFactory(new PropertyValueFactory<Paquete, Integer>("peso"));
    tcEstado.setCellValueFactory(new PropertyValueFactory<Paquete, Boolean>("estado"));

    ObservableList<Paquete> list = FXCollections.observableArrayList(pq);

    tPaquetes.setItems(list);
  }

}