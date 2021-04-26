package controller;

import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import utilities.LoadView;

public class Admin {

    @FXML
    private AnchorPane content;

    @FXML
    private Label labelNameUser;
    private Parent sedeConsulta;
    private Parent userConsulta;
    private Parent reportes;
    private LoadView vista;
    private String userName;

    public Admin(String userName) {
        vista = new LoadView();
        sedeConsulta = vista.loadView("sede.consulta", this);
        reportes = vista.loadView("reportes", this);
        this.userName = userName;
    }

    @FXML
    private void initialize() {
        labelNameUser.setText("Bienvenido " + userName);
    }

    @FXML
    void goToSedeConsulta(ActionEvent event) {
        content.getChildren().clear();
        content.getChildren().add(sedeConsulta);
    }

    @FXML
    void goToUsuariosConsulta(ActionEvent event) {
        content.getChildren().clear();
        userConsulta = vista.loadView("user.consulta", new UserConsulta(content));
        content.getChildren().add(userConsulta);
    }

    @FXML
    void goToAdminReportes(ActionEvent event) {
        content.getChildren().clear();
        content.getChildren().add(reportes);
    }

    @FXML
    void clicksoide(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Does it works?", "Test", 0);
    }

    @FXML
    void borrar(ActionEvent event) {
        String user = "juanito";
        JOptionPane.showOptionDialog(null, "Desea borrar a " + user + "?", "Eliminar registro", 0, 0, null, null, user);
    }
}