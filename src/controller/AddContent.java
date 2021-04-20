package controller;

import java.io.IOException;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.AreaChart;
import javafx.scene.layout.AnchorPane;

public class AddContent {

    @FXML
    private AnchorPane content;

    private Parent sedeConsulta;
    private Parent userConsulta;
    private Parent reportes;
    private Parent userRegister;

    public AddContent() {
        sedeConsulta = loadView("sede.consulta");
        userConsulta = loadView("user.consulta");
        reportes = loadView("reportes");
    }

    private Parent loadView(String name) {
        var loader = new FXMLLoader(getClass().getResource("../view/" + name + ".fxml"));
        loader.setController(this);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    private Parent aRegistrar() {
        var loader = new FXMLLoader(getClass().getResource("../view/user.register.fxml"));
        loader.setController(new UserRegister(content, this));
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
        userRegister = aRegistrar();
        content.getChildren().add(userRegister);
    }

    @FXML
    void clicksoide(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "Does it works?", "Test", 0);
    }

}
