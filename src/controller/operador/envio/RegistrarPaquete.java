package controller.operador.envio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Entities.Empleado;
import model.Entities.Paquete;
import utilities.GeneralAlerts;
import utilities.Globals;
import utilities.TextFieldRestrictions;

public class RegistrarPaquete {

    @FXML
    private Button atrasPaquete;// Botón para devolverse a la vista de clientes
    @FXML
    private Button btRegistrarEnvios;
    @FXML
    private TextField Peso;
    @FXML
    private TextField Valor;
    @FXML
    private TextArea Descripcion;
    @FXML
    private CheckBox Seguro;
    @FXML
    private Button btEliminar;
    @FXML
    private Button btEditar;
    @FXML
    private Button btRegistrar;
    @FXML
    private TextField Alto;
    @FXML
    private TextField Largo;
    @FXML
    private TextField Ancho;
    @FXML
    private TableView<Paquete> tbPaquetes;
    @FXML
    private TableColumn<Paquete, Integer> tcPeso;
    @FXML
    private TableColumn<Paquete, Integer> tcValor;
    @FXML
    private TableColumn<Paquete, String> tcDescripcion;
    @FXML
    private TableColumn<Paquete, Integer> tcVolumen;
    @FXML
    private TableColumn<Paquete, Integer> tcValorEnvio;
    @FXML
    private TableColumn<Paquete, Integer> tcTotal;
    // Lista de elementos de la tabla
    private ObservableList<Paquete> list;

    private int selectedP; // Índice de la fila seleccionada en la tabla de paquetes
    private boolean modify; // Boolean que indica si se está modificando un paquete o no

    private model.RegistrarEnvio envio;
    private OperadorResumen operadorResumen;
    private Empleado operador;

    public RegistrarPaquete(model.RegistrarEnvio envio, Empleado operador) {
        this.envio = envio;
        this.operador = operador;
    }

    public void initialize() {
        selectedP = -1;
        if (operadorResumen == null)
            operadorResumen = new OperadorResumen();
        TextFieldRestrictions.textFieldNumeric(Peso);
        TextFieldRestrictions.textFieldNumeric(Valor);
        TextFieldRestrictions.textFieldNumeric(Ancho);
        TextFieldRestrictions.textFieldNumeric(Largo);
        TextFieldRestrictions.textFieldNumeric(Alto);

        modify = false;
        tcPeso.setCellValueFactory(new PropertyValueFactory<Paquete, Integer>("peso"));
        tcValor.setCellValueFactory(new PropertyValueFactory<Paquete, Integer>("valor"));
        tcDescripcion.setCellValueFactory(new PropertyValueFactory<Paquete, String>("descripcion"));
        tcVolumen.setCellValueFactory(new PropertyValueFactory<Paquete, Integer>("volumen"));
        tcValorEnvio.setCellValueFactory(new PropertyValueFactory<Paquete, Integer>("valorenvio"));
        tcTotal.setCellValueFactory(new PropertyValueFactory<Paquete, Integer>("total"));

        list = FXCollections.observableArrayList();
        tbPaquetes.setItems(list);
    }

    @FXML
    void atras(ActionEvent event) {
        if (event.getSource() == atrasPaquete)
            Globals.cambiarVista("operador.cliente", this);
    }

    @FXML
    void editarPaquete(ActionEvent event) {
        int index = tbPaquetes.getSelectionModel().getFocusedIndex();
        if (index == -1)
            return;
        if (!modify) {
            btRegistrar.setDisable(true);
            selectedP = index;
            Paquete pt = list.get(index);
            Peso.setText(pt.peso + "");
            Valor.setText(pt.valor + "");
            Descripcion.setText(pt.descripcion);
            Ancho.setText(pt.volumen.ancho + "");
            Largo.setText(pt.volumen.largo + "");
            Alto.setText(pt.volumen.alto + "");
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
                list.add(index, p);
                btRegistrar.setDisable(false);
                btEliminar.setDisable(false);
                tbPaquetes.setDisable(false);
                selectedP = -1;
                btEditar.setText("Editar");
                clearFieldsPaquetes();
            } catch (NumberFormatException e) {
                GeneralAlerts.showEmptyFieldAlert();
            }
        }
        modify = !modify;
        // Obtener el index seleccionado?
    }

    @FXML
    void eliminarPaquete(ActionEvent event) {
        int index = tbPaquetes.getSelectionModel().getFocusedIndex();
        if (index == -1)
            return;
        envio.eliminarPaquete(index);
        list.remove(index);
    }

    @FXML
    void registrarPaquete(ActionEvent event) {
        try { // Faltan validaciones
            Integer peso = Integer.parseInt(Peso.getText());
            Integer valor = Integer.parseInt(Valor.getText());
            String descripcion = Descripcion.getText();
            Integer ancho = Integer.parseInt(Ancho.getText());
            Integer largo = Integer.parseInt(Largo.getText());
            Integer alto = Integer.parseInt(Alto.getText());
            Boolean seguro = Seguro.isSelected();

            Paquete p = envio.agregarPaquete(peso, valor, descripcion, ancho, largo, alto, seguro, -1);
            list.add(p);
            clearFieldsPaquetes();
        } catch (NumberFormatException e) {
            GeneralAlerts.showEmptyFieldAlert();
        }
    }
    private void clearFieldsPaquetes() {
        Peso.setText("");
        Valor.setText("");
        Descripcion.setText("");
        Alto.setText("");
        Largo.setText("");
        Ancho.setText("");
    }

    @FXML
    void resumenEnvio(ActionEvent event) {
        operadorResumen.update(envio);
        Globals.cambiarVista(Globals.loadView("operador.resumen", operadorResumen));
    }
}
