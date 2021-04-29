package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Clientes.Cliente;
import model.RegistrarEnvio.TipoCliente;

/**
 * Controlador para las vistar operador.cliente y operador.paquetes
 * @author Julián Orejuela
 * @version 1.0, 29/4/2021
 */
public class RegistrarEnvio {
    @FXML private TextField RCedula;
    @FXML private TextField RNombre;
    @FXML private TextField RDireccion;
    @FXML private TextField RTelefono;
    @FXML private TextField DCedula;
    @FXML private TextField DNombre;
    @FXML private TextField DDireccion;
    @FXML private TextField DTelefono;
    private model.RegistrarEnvio envio;

    public void initialize() {
        envio = new model.RegistrarEnvio();
        RCedula.focusedProperty().addListener(onRemitenteFocusOut);
        DCedula.focusedProperty().addListener(onDestinatarioFocusOut);
    }

    @FXML void atras(ActionEvent event) {

    }

    @FXML void registrarPaquetes(ActionEvent event) {
        envio.setCliente(RCedula.getText(), RNombre.getText(), RDireccion.getText(), RTelefono.getText(), TipoCliente.Remitente);
        envio.setCliente(DCedula.getText(), DNombre.getText(), DDireccion.getText(), DTelefono.getText(), TipoCliente.Destinatario);
    }

    @FXML void onActionRemitente() {
        onChangeCedula(RCedula, RNombre, RDireccion, RTelefono, TipoCliente.Remitente);
    }

    ChangeListener<Boolean> onRemitenteFocusOut = (ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
        if (!arg2) onActionRemitente();
    };

    @FXML void onActionDestinatario() {
        onChangeCedula(DCedula, DNombre, DDireccion, DTelefono, TipoCliente.Destinatario);
    }

    ChangeListener<Boolean> onDestinatarioFocusOut = (ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
        if (!arg2) onActionDestinatario();
    };

    private void onChangeCedula(TextField Cedula, TextField Nombre, TextField Direccion, TextField Telefono, TipoCliente tipo) {
        if (Cedula.getText() == "") return;
        Cliente cliente = envio.buscarCliente(Cedula.getText(), tipo);
        if (cliente != null) {
            Nombre.setText(cliente.nombre);
            Direccion.setText(cliente.direccion);
            Telefono.setText(cliente.telefono);
        }
    }

}
