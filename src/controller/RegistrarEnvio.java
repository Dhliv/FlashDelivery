package controller;

import java.util.Observable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Clientes.Cliente;
import model.RegistrarEnvio.Dim;
import model.RegistrarEnvio.Paquete;
import model.RegistrarEnvio.TipoCliente;
import utilities.Globals;

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
    @FXML private Button atrasCliente;
    // #------------------------------------
    @FXML private Button btRegistrarEnvios;
    @FXML private Button atrasPaquete;
    @FXML private TextField Peso;
    @FXML private TextField Valor;
    @FXML private TextArea Descripcion;
    @FXML private CheckBox Seguro;
    @FXML private Button btEliminar;
    @FXML private Button btEditar;
    @FXML private Button btRegistrar;
    @FXML private TextField Alto;
    @FXML private TextField Largo;
    @FXML private TextField Ancho;
    @FXML private TableView<PaqueteT> tbPaquetes;
    // #------------------------------------
    private model.RegistrarEnvio envio;

    public void initialize() {
        envio = new model.RegistrarEnvio();
        if (RCedula != null) RCedula.focusedProperty().addListener(onRemitenteFocusOut);
        if (DCedula != null) DCedula.focusedProperty().addListener(onDestinatarioFocusOut);
        if (tbPaquetes != null) {
            // Cargar cosas en la tabla
            ObservableList<PaqueteT> list = FXCollections.observableArrayList();
            Paquete p = new Paquete();
            p.peso = 1;
            p.costo = 1;
            p.descripcion = "a";
            Dim d = new Dim();
            d.alto = 1;
            d.ancho = 1;
            d.largo = 1;
            p.volumen = d;
            p.costo = 1;
            p.total = 1;
            p.valor_declarado = 1;
            /*list.add(new PaqueteT(p));
            tbPaquetes.setItems(list);*/
            tbPaquetes.getItems().add(new PaqueteT(p));
            tbPaquetes.getItems().add(new PaqueteT(p));
            tbPaquetes.getItems().add(new PaqueteT(p));
        }
    }

    @FXML void atras(ActionEvent event) {
        if(event.getSource()==atrasPaquete) Globals.cambiarVista("operador.cliente");
    }

    @FXML void registrarPaquetes(ActionEvent event) {
        envio.setCliente(RCedula.getText(), RNombre.getText(), RDireccion.getText(), RTelefono.getText(), TipoCliente.Remitente);
        envio.setCliente(DCedula.getText(), DNombre.getText(), DDireccion.getText(), DTelefono.getText(), TipoCliente.Destinatario);
        Globals.cambiarVista("operador.paquetes", this);
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

    // #---------------------------------------------------------------------------
    // # VISTA REGISTRAR PAQUETES
    // #---------------------------------------------------------------------------

    @FXML void registrarPaquete(ActionEvent event) {
        try { // Faltan validaciones
            Integer peso = Integer.parseInt(Peso.getText());
            Integer valor = Integer.parseInt(Valor.getText());
            String descripcion = Descripcion.getText();
            Integer ancho = Integer.parseInt(Ancho.getText());
            Integer largo = Integer.parseInt(Largo.getText());
            Integer alto = Integer.parseInt(Alto.getText());
            Boolean seguro = Seguro.isPressed();
            envio.agregarPaquete(peso, valor, descripcion, ancho, largo, alto, seguro);
        } catch (NumberFormatException e) {
            // Colocar un joptionPane o alert
            System.out.println("Ingrese correctamente los datos");
        }

    }

    @FXML void editarPaquete(ActionEvent event) {
        // Obtener el index seleccionado?
        int index = tbPaquetes.getSelectionModel().getFocusedIndex();
    }

    @FXML void eliminarPaquete(ActionEvent event) {

    }

    private void updatePaquetesTable() {
        // tbPaquetes.getItems().addAll(enviar.getPaquetes());

    }

    public static class PaqueteT {
        SimpleStringProperty peso, valor, descripcion, volumen, valor_envio, total;

        public PaqueteT(Paquete p) {
            peso = new SimpleStringProperty(p.peso + "");
            valor = new SimpleStringProperty(p.valor_declarado + "");
            descripcion = new SimpleStringProperty(p.descripcion);
            volumen = new SimpleStringProperty(p.volumen.volumen() + "");
            valor_envio = new SimpleStringProperty("xd");
            total = new SimpleStringProperty(p.total + "");
        }
    }

}
