package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class OperadorOficina {

  @FXML
  private TableView<?> tablePaquetes;
  @FXML
  private TableColumn<?, ?> columnDestinatario;
  @FXML
  private TableColumn<?, ?> columnIdPaquete;
  @FXML
  private TableColumn<?, ?> columnPesoPaquete;
  @FXML
  private TableColumn<?, ?> columnEstado;
  @FXML
  private Label labelPOS;
  @FXML
  private Button btnSolicitudRecogida;
  @FXML
  private Button btnRegistrarPaquete;
  @FXML
  private Label labelUsuario;

  public OperadorOficina() {

  }

  @FXML
  void registrarPaquete(ActionEvent event) {

  }

  @FXML
  void registrarRecogida(ActionEvent event) {

  }

}
