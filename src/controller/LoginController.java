package controller;

import model.*;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;



public class LoginController {
    private UsuarioDAO ud;
    private EmpleadoDAO ed;
    @FXML
    private Button btIngresar;

    @FXML
    private PasswordField txtPass;

    @FXML
    private TextField txtUsuario;

    @FXML
    void clicksoide(ActionEvent event) {
        ed = new EmpleadoDAO();
        ud = new UsuarioDAO();
        String user = txtUsuario.getText();
        String pass = txtPass.getText();
        int acc = ud.entradaUsuario(user, pass);
        if(acc == -2){
            JOptionPane.showMessageDialog(null, "Ud. no se encuentra habilitado en el sistema");
        }else if(acc == -1){
            JOptionPane.showMessageDialog(null, "No se ha encontrado su usuario");
        }else{
            JOptionPane.showMessageDialog(null, "Entraste!");
        }
    }

}
