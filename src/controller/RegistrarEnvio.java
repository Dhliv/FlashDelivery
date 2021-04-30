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
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML private TextField RCiudad;
    @FXML private TextField RDireccion;
    @FXML private TextField RTelefono;
    @FXML private TextField DCedula;
    @FXML private TextField DNombre;
    @FXML private TextField DCiudad;
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
    @FXML private TableColumn<PaqueteT, Integer> tcPeso;
    @FXML private TableColumn<PaqueteT, Integer> tcValor;
    @FXML private TableColumn<PaqueteT, String> tcDescripcion;
    @FXML private TableColumn<PaqueteT, Integer> tcVolumen;
    @FXML private TableColumn<PaqueteT, Integer> tcValorEnvio;
    @FXML private TableColumn<PaqueteT, Integer> tcTotal;
    private ObservableList<PaqueteT> list;
    // #------------------------------------
    private model.RegistrarEnvio envio;

    public void initialize() {
        envio = new model.RegistrarEnvio();
        if (RCedula != null) RCedula.focusedProperty().addListener(onRemitenteFocusOut);
        if (DCedula != null) DCedula.focusedProperty().addListener(onDestinatarioFocusOut);
        if (tbPaquetes != null) {
            // Cargar cosas en la tabla

            tcPeso.setCellValueFactory(new PropertyValueFactory<PaqueteT, Integer>("peso"));
            tcValor.setCellValueFactory(new PropertyValueFactory<PaqueteT, Integer>("valor"));
            tcDescripcion.setCellValueFactory(new PropertyValueFactory<PaqueteT, String>("descripcion"));
            tcVolumen.setCellValueFactory(new PropertyValueFactory<PaqueteT, Integer>("volumen"));
            tcValorEnvio.setCellValueFactory(new PropertyValueFactory<PaqueteT, Integer>("valorenvio"));
            tcTotal.setCellValueFactory(new PropertyValueFactory<PaqueteT, Integer>("total"));

            list = FXCollections.observableArrayList();
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

            PaqueteT pt = new PaqueteT(p);
            list.add(pt);
            list.add(pt);
            list.add(pt);
            tbPaquetes.setItems(list);

        }
    }

    @FXML void atras(ActionEvent event) {
        if (event.getSource() == atrasPaquete) Globals.cambiarVista("operador.cliente");
    }

    @FXML void registrarPaquetes(ActionEvent event) {
        envio.setCliente(RCedula.getText(), RNombre.getText(), RCiudad.getText(), RDireccion.getText(), RTelefono.getText(), TipoCliente.Remitente);
        envio.setCliente(DCedula.getText(), DNombre.getText(), DCiudad.getText(), DDireccion.getText(), DTelefono.getText(), TipoCliente.Destinatario);
        Globals.cambiarVista("operador.paquetes", this);
    }

    @FXML void onActionRemitente() {
        onChangeCedula(RCedula, RNombre, RCiudad, RDireccion, RTelefono, TipoCliente.Remitente);
    }

    ChangeListener<Boolean> onRemitenteFocusOut = (ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
        if (!arg2) onActionRemitente();
    };

    @FXML void onActionDestinatario() {
        onChangeCedula(DCedula, DNombre, DCiudad, DDireccion, DTelefono, TipoCliente.Destinatario);
    }

    ChangeListener<Boolean> onDestinatarioFocusOut = (ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
        if (!arg2) onActionDestinatario();
    };

    private void onChangeCedula(TextField Cedula, TextField Nombre, TextField Ciudad, TextField Direccion, TextField Telefono, TipoCliente tipo) {
        if (Cedula.getText() == "") return;
        Cliente cliente = envio.buscarCliente(Cedula.getText(), tipo);
        if (cliente != null) {
            Nombre.setText(cliente.nombre);
            Ciudad.setText(cliente.ciudad);
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

            Paquete p = envio.agregarPaquete(peso, valor, descripcion, ancho, largo, alto, seguro);
            list.add(new PaqueteT(p));
        } catch (NumberFormatException e) {
            // Colocar un joptionPane o alert
            System.out.println("Ingrese correctamente los datos");
        }

    }

    @FXML void editarPaquete(ActionEvent event) {
        // Obtener el index seleccionado?
        int index = tbPaquetes.getSelectionModel().getFocusedIndex();
        System.out.println(index);
        envio.eliminarPaquete(index);
    }

    @FXML void eliminarPaquete(ActionEvent event) {
        int index = tbPaquetes.getSelectionModel().getFocusedIndex();
        list.remove(index);

    }

    private void updatePaquetesTable() {
        // tbPaquetes.getItems().addAll(enviar.getPaquetes());

    }

    public static class PaqueteT {
        private int peso, valor;
        private String descripcion;
        private int volumen, valorenvio, total;

        public PaqueteT(Paquete p) {
            peso = p.peso;
            valor = p.valor_declarado;
            descripcion = p.descripcion;
            volumen = p.volumen.volumen();
            valorenvio = 10;
            total = p.total;
        }

        public int getPeso() {
            return peso;
        }

        public int getValor() {
            return valor;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public int getVolumen() {
            return volumen;
        }

        public int getValorenvio() {
            return valorenvio;
        }

        public int getTotal() {
            return total;
        }
    }

}
