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
import javafx.scene.input.MouseEvent;
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

    private int selectedP; // Índice de la fila seleccionada en la tabla de paquetes
    private boolean modify; // Boolean que indica si se está modificando un paquete o no
    // Componentes gráficos de la ventana de registro paquetes
    @FXML private Button btRegistrarEnvios; //
    @FXML private Button atrasPaquete; // Botón para devolverse a la vista de clientes
    @FXML private TextField Peso; // TextField para leer el peso del paquete
    @FXML private TextField Valor; // TextField para leer el valor del paquete
    @FXML private TextArea Descripcion; // TextField para leer la descripción adjunta al paquete
    @FXML private CheckBox Seguro; // CheckBox para marcar si se le aplica o no el seguro al paquete
    @FXML private Button btEliminar; // Botón para eliminar un paquete de la lista
    @FXML private Button btEditar; // Botón para editar un paquete de la lista
    @FXML private Button btRegistrar; // Botón para registrar un paquete de la lista
    @FXML private TextField Alto; // TextField para ingresar el alto del paquete
    @FXML private TextField Largo; // TextField para ingresar el largo del paquete
    @FXML private TextField Ancho; // TextField para ingresar el ancho del paquete

    @FXML private TableView<PaqueteT> tbPaquetes; // Tabla para mostrar la lista de paquetes
    // Columnas de la tabla
    @FXML private TableColumn<PaqueteT, Integer> tcPeso;
    @FXML private TableColumn<PaqueteT, Integer> tcValor;
    @FXML private TableColumn<PaqueteT, String> tcDescripcion;
    @FXML private TableColumn<PaqueteT, Integer> tcVolumen;
    @FXML private TableColumn<PaqueteT, Integer> tcValorEnvio;
    @FXML private TableColumn<PaqueteT, Integer> tcTotal;
    // Lista de elementos de la tabla
    private ObservableList<PaqueteT> list;
    // #------------------------------------

    private model.RegistrarEnvio envio;
    private OperadorResumen operadorResumen;

    public void initialize() {
        selectedP = -1;
        if (envio == null) envio = new model.RegistrarEnvio();
        if(operadorResumen == null) operadorResumen = new OperadorResumen();
        
        if (RCedula != null) RCedula.focusedProperty().addListener(onRemitenteFocusOut);
        if (DCedula != null) DCedula.focusedProperty().addListener(onDestinatarioFocusOut);
        if (tbPaquetes != null) {
            // Cargar cosas en la tabla
            modify = false;
            tcPeso.setCellValueFactory(new PropertyValueFactory<PaqueteT, Integer>("peso"));
            tcValor.setCellValueFactory(new PropertyValueFactory<PaqueteT, Integer>("valor"));
            tcDescripcion.setCellValueFactory(new PropertyValueFactory<PaqueteT, String>("descripcion"));
            tcVolumen.setCellValueFactory(new PropertyValueFactory<PaqueteT, Integer>("volumen"));
            tcValorEnvio.setCellValueFactory(new PropertyValueFactory<PaqueteT, Integer>("valorenvio"));
            tcTotal.setCellValueFactory(new PropertyValueFactory<PaqueteT, Integer>("total"));

            list = FXCollections.observableArrayList();
            tbPaquetes.setItems(list);
        }
    }

    @FXML void atras(ActionEvent event) {
        if (event.getSource() == atrasPaquete) Globals.cambiarVista("operador.cliente", this);
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

            Paquete p = envio.agregarPaquete(peso, valor, descripcion, ancho, largo, alto, seguro, -1);
            list.add(new PaqueteT(p));
        } catch (NumberFormatException e) {
            // Colocar un joptionPane o alert
            System.out.println("Ingrese correctamente los datos");
        }

        clearCampsPaquetes();
    }

    @FXML void editarPaquete(ActionEvent event) {
        int index = tbPaquetes.getSelectionModel().getFocusedIndex();
        if (index == -1) return;
        if (!modify) {
            btRegistrar.setDisable(true);
            selectedP = index;
            PaqueteT pt = list.get(index);
            Peso.setText(pt.peso + "");
            Valor.setText(pt.valor + "");
            Descripcion.setText(pt.descripcion);
            Ancho.setText(pt.d.ancho + "");
            Largo.setText(pt.d.largo + "");
            Alto.setText(pt.d.alto + "");
            tbPaquetes.setDisable(true);
            btEliminar.setDisable(true);
            btEditar.setText("Confirmar");
        } else {
            try {
                Integer peso = Integer.parseInt(Peso.getText());
                Integer valor = Integer.parseInt(Valor.getText());
                String descripcion = Descripcion.getText();
                Integer ancho = Integer.parseInt(Ancho.getText());
                Integer largo = Integer.parseInt(Largo.getText());
                Integer alto = Integer.parseInt(Alto.getText());
                Boolean seguro = Seguro.isPressed();
                envio.eliminarPaquete(index);
                Paquete p = envio.agregarPaquete(peso, valor, descripcion, ancho, largo, alto, seguro, selectedP);
                list.remove(index);
                list.add(index, new PaqueteT(p));
                btRegistrar.setDisable(false);
                btEliminar.setDisable(false);
                tbPaquetes.setDisable(false);
                selectedP = -1;
                btEditar.setText("Editar");
                clearCampsPaquetes();
            } catch (NumberFormatException e) {
                // Colocar un joptionPane o alert
                System.out.println("Ingrese correctamente los datos");
            }
        }
        modify = !modify;
        // Obtener el index seleccionado?

    }

    @FXML void eliminarPaquete(ActionEvent event) {
        int index = tbPaquetes.getSelectionModel().getFocusedIndex();
        if (index == -1) return;
        envio.eliminarPaquete(index);
        list.remove(index);

    }

    private void updatePaquetesTable() {
        // tbPaquetes.getItems().addAll(enviar.getPaquetes());

    }

    private void clearCampsPaquetes() {
        Peso.setText("");
        Valor.setText("");
        Descripcion.setText("");
        Alto.setText("");
        Largo.setText("");
        Ancho.setText("");
    }

    public static class PaqueteT {
        private int peso, valor;
        private String descripcion;
        private int volumen, valorenvio, total;
        private Dim d;

        public PaqueteT(Paquete p) {
            peso = p.peso;
            valor = p.valor_declarado;
            descripcion = p.descripcion;
            volumen = p.volumen.volumen();
            valorenvio = 10;
            total = p.total;
            d = p.volumen;
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

    @FXML void resumenEnvio(ActionEvent event) {
        operadorResumen.update(envio, this);
        Globals.cambiarVista(Globals.loadView("operador.resumen", operadorResumen));
    }
}
