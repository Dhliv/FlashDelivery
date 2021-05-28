package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class OperadorConsulta implements Initializable {

  @FXML private TableView<model.Paquetes.Paquete> tPaquetes;
  @FXML private TableColumn<model.Paquetes.Paquete, String> tcDestinatario;
  @FXML private TableColumn<model.Paquetes.Paquete, Integer> tcIdPaquete;
  @FXML private TableColumn<model.Paquetes.Paquete, Integer> tcPesoPaquete;
  @FXML private TableColumn<model.Paquetes.Paquete, Boolean> tcEstado;

  @Override public void initialize(URL location, ResourceBundle resources) {

  }

}