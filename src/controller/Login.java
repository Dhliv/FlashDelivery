package controller;

import model.*;
import utilities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Login {
    private Stage pantalla;
    private Roles roles;
    private ArrayList<String> rol;

    public Login(Stage lgin) {
        this.pantalla = lgin;
        roles = new Roles();
        this.rol = roles.rol;
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
    void loginKeyboard(KeyEvent event) throws Exception {
        System.out.println(event);
        KeyCode key = event.getCode();
        if  (key.equals(KeyCode.ENTER)){
            login();
        }
    }

    @FXML
    void clicksoide(ActionEvent event) throws Exception {
        login();
    }

    void login() throws Exception{
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

            // Cerrar ventana actual
            pantalla.close();

            // Abrir nueva ventana
            Ventana vent = new Ventana(null, null);

            if (rolAcc.equals(rol.get(0))) {
                vent = new Ventana("admin", new Admin());
                vent.start(pantalla);
            } else if (rolAcc.equals(rol.get(1))) {
                vent = new Ventana("admin", new Admin());
            } else if (rolAcc.equals(rol.get(2))) {
                vent = new Ventana("operadorOficina", new Admin());
            } else if (rolAcc.equals(rol.get(3))) {
                vent = new Ventana("operadorAuxiliar", new Admin());
            } else if (rolAcc.equals(rol.get(4))) {
                vent = new Ventana("contador", new Admin());
            }
        }
    }

}
