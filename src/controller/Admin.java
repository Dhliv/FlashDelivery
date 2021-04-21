package controller;

import java.io.IOException;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class Admin {

    @FXML
    private AnchorPane content;

    private Parent sedeConsulta;
    private Parent userConsulta;
    private Parent reportes;
    private Parent userRegister;

    public Admin() {
        sedeConsulta = loadView("sede.consulta", this);
        userConsulta = loadView("user.consulta", this);
        reportes = loadView("reportes", this);
    }

    private Parent loadView(String name, Object control) {
        var loader = new FXMLLoader(getClass().getResource("../view/" + name + ".fxml"));
        loader.setController(control);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    @FXML
    void goToSedeConsulta(ActionEvent event) {
        content.getChildren().clear();
        content.getChildren().add(sedeConsulta);
    }

    @FXML
    void goToUsuariosConsulta(ActionEvent event) {
        content.getChildren().clear();
        content.getChildren().add(userConsulta);
    }

    @FXML
    void goToAdminReportes(ActionEvent event) {
        content.getChildren().clear();
        content.getChildren().add(reportes);
    }

    @FXML
    void goToUsuariosRegistro(ActionEvent event) {
        content.getChildren().clear();
        userRegister = loadView("user.register", new UserRegister(content, this));
        content.getChildren().add(userRegister);
    }

    @FXML
    void clicksoide(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Does it works?", "Test", 0);
    }

    @FXML
    void borrar(ActionEvent event){
        String user = "juanito";
        JOptionPane.showOptionDialog(null, "Desea borrar a "+user+"?", "Eliminar registro", 0, 0, null, null, user);
    }
}
