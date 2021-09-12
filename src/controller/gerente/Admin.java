package controller.gerente;

import javax.swing.JOptionPane;

import controller.gerente.usuarios.UserConsulta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import utilities.Globals;

public class Admin {

    private Parent sedeConsulta;
    private Parent userConsulta;
    private Parent reportes;
    private String userName;

    public Admin(String userName) {
        this.userName = userName;
    }

    // #---------------------------------------------------------------------------
    // # FXML: ARCHIVOS DE JAVA FXML
    // #---------------------------------------------------------------------------

    @FXML
    private Label labelNameUser;

    @FXML
    private AnchorPane content;

    @FXML
    private void initialize() {
        Globals.viewPane = content;
        labelNameUser.setText("Bienvenido " + userName);
    }

    @FXML
    void goToSedeConsulta(ActionEvent event) {
        content.getChildren().clear();
        sedeConsulta = Globals.loadView("sede.consulta");
        content.getChildren().add(sedeConsulta);
    }

    @FXML
    void goToUsuariosConsulta(ActionEvent event) {
        content.getChildren().clear();
        userConsulta = Globals.loadView("user.consulta", new UserConsulta());
        content.getChildren().add(userConsulta);
    }

    @FXML
    void goToAdminReportes(ActionEvent event) {
        content.getChildren().clear();
        reportes = Globals.loadView("reportes", this);
        content.getChildren().add(reportes);
    }

    @FXML
    void borrar(ActionEvent event) {
        String user = "juanito";
        JOptionPane.showOptionDialog(null, "Desea borrar a " + user + "?", "Eliminar registro", 0, 0, null, null, user);
    }

    @FXML
    void logOut(ActionEvent event) {
        Globals.logOut();
    }
}
