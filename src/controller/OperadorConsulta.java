package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class OperadorConsulta implements Initializable {

  @FXML private TableView<?> tablePaquetes;
  @FXML private TableColumn<?, ?> columnDestinatario;
  @FXML private TableColumn<?, ?> columnIdPaquete;
  @FXML private TableColumn<?, ?> columnPesoPaquete;
  @FXML private TableColumn<?, ?> columnEstado;

  @Override public void initialize(URL location, ResourceBundle resources) {

  }

}