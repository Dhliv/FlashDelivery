package controller.operador.verPaquetes;

import com.jfoenix.controls.JFXButton;

import controller.operador.OperadorOficina;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.Envio;
import model.Entities.Cliente;
import model.Entities.Empleado;
import model.Entities.Paquete;
import utilities.View;

public class operadorVerInfo {
    @FXML
    private JFXButton atrasPaquete;
    @FXML
    private Label labelID;
    @FXML
    private Label labelFechaRegistro;
    @FXML
    private Label labelMetodoPago;
    @FXML
    private Label labelDireccionEntrega;
    @FXML
    private CheckBox checkEntregado;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private Label labelVolumen;
    @FXML
    private Label labelRemitente;
    @FXML
    private Label labelDestinatario;

    private Envio en;
    private Cliente rem, des;
    private Paquete p;
    private Empleado op;

    public operadorVerInfo(Envio en, Empleado op) {
        this.op = op;
        this.en = en;
        this.rem = Cliente.buscarCliente(en.getCliente_envio());
        this.des = Cliente.buscarCliente(en.getCliente_envio());
        this.p = Paquete.cargarPaquete(en.id);
    }

    public void initialize() {
        labelID.setText(en.getIdenvio() + "");
        labelDestinatario.setText(des.nombre);
        labelDireccionEntrega.setText(en.direccion_entrega);
        labelFechaRegistro.setText(en.fecha_registro.toString());
        labelMetodoPago.setText(en.metodo_pago);
        labelRemitente.setText(rem.nombre);
        labelVolumen.setText(p.getVolumen() + "");
        txtDescripcion.setText(p.getDescripcion().replace("\\n", "\n"));
    }

    @FXML
    void atras(ActionEvent event) {
        View.cambiarFull("operadorOficinaTabla", new OperadorConsulta(op));
    }

}
