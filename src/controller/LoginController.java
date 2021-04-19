package controller;

import model.*;
import view.Ventana;
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
    void clicksoide(ActionEvent event) throws Exception {
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

            //Cerrar ventana actual
            pantalla.close();


            //Abrir nueva ventana
            Ventana vent = new Ventana(null, null);
            if (rolAcc.equals("Gerente")) {
                vent = new Ventana("main", new AddContent());
                vent.start(pantalla);
            } else if (rolAcc.equals("Secretaria")) {
                vent = new Ventana("main", new AddContent());
            } else if (rolAcc.equals("Operador")) {
                vent = new Ventana("operadorOficina", new AddContent());
            } else if (rolAcc.equals("Auxiliar")) {
                vent = new Ventana("operadorAuxiliar", new AddContent());
            } else if (rolAcc.equals("Contador")) {
                vent = new Ventana("contador", new AddContent());
            }
        }
    }

}
