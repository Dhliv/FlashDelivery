package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginController {

    @FXML
    private Button btIngresar;

    @FXML
    private PasswordField txtPass;

    @FXML
    private TextField txtUsuario;

    @FXML
    void clicksoide(ActionEvent event) {
        System.out.println("XD");
    }

}
