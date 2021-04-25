package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Usuario;
import utilities.LoadView;

public class UserConsulta implements Initializable {

  private AnchorPane content;
  private Parent userRegister;
  private LoadView vista;

  @FXML
  private TableView<Usuario> tableUsers;
  @FXML
  private TableColumn<Usuario, Integer> cedula;
  @FXML
  private TableColumn<Usuario, String> nombre;
  @FXML
  private TableColumn<Usuario, String> apellido;
  @FXML
  private TableColumn<Usuario, Boolean> sede;

  public UserConsulta(AnchorPane cont) {
    content = cont;
    vista = new LoadView();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    cedula.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("ID"));
    nombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("username"));
    apellido.setCellValueFactory(new PropertyValueFactory<Usuario, String>("password"));
    sede.setCellValueFactory(new PropertyValueFactory<Usuario, Boolean>("enabled"));
    ObservableList<Usuario> s = FXCollections.observableArrayList();

    s.add(new Usuario(1234, "APA", "password", true));

    tableUsers.setItems(s);
  }

  @FXML
  void borrar(ActionEvent event) {

  }

  @FXML
  void goToUsuariosRegistro(ActionEvent event) {
    content.getChildren().clear();
    userRegister = vista.loadView("user.register", new UserRegister(content, this));
    content.getChildren().addAll(userRegister);
  }
}
