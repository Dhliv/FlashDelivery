package controller;

import model.*;
import view.Ventanas;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    private Stage pantalla;

    public LoginController(Stage lgin) {
        this.pantalla = lgin;
    }

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
        if (acc == -2) {
            // JOptionPane.showMessageDialog(null, "Ud. no se encuentra habilitado en el
            // sistema");
            System.out.println("sorry bro");
        } else if (acc == -1) {
            // JOptionPane.showMessageDialog(null, "No se ha encontrado su usuario");
            System.out.println("fake");
        } else {
            // JOptionPane.showMessageDialog(null, "Entraste!");
            System.out.println("entre");
            Empleado userActual = ed.cargarEmpleado(acc);
            var rolAcc = userActual.getRol();
            pantalla.close();

            if (rolAcc.equals("Gerente")) {
                Ventanas vent = new Ventanas("main", new test1());
            } else if (rolAcc.equals("Secretaria")) {
                Ventanas vent = new Ventanas("main", new test1());
            } else if (rolAcc.equals("Operador")) {
                Ventanas vent = new Ventanas("operadorOficina", new test1());
            } else if (rolAcc.equals("Auxiliar")) {
                Ventanas vent = new Ventanas("operadorAuxiliar", new test1());
            } else if (rolAcc.equals("Contador")) {
                Ventanas vent = new Ventanas("contador", new test1());
            }
        }
    }

}
