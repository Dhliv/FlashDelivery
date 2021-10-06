package controller.gerente;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import model.Entities.Sede;
import utilities.Globals;

public class Reportes {

  @FXML private ComboBox<String> sedeChoicebox;
  CreateChart createChart;

  public void initialize() {
    ObservableList<String> sedesObservable = FXCollections.observableArrayList(); // Lista con las Sedes
    sedesObservable.add("Todas");
    sedesObservable.addAll(Sede.getSedesParsed());

    sedeChoicebox.getItems().addAll(sedesObservable);
    sedeChoicebox.setValue("Todas");
    createChart = new CreateChart();

    Globals.style.setParent(sedeChoicebox);

  }

  @FXML void mediosDePago(MouseEvent event) {
    createChart.setSedeId(getSedesSeleccionadas());
    createChart.setPeriodo(2);
    createChart.medioDePago("Efectivo");
  }

  @FXML void paquetesEnviados(MouseEvent event) {
    createChart.setSedeId(getSedesSeleccionadas());
    createChart.setPeriodo(2);
    createChart.paquetesEnviados();
  }

  @FXML void servicioSolicitado(MouseEvent event) {
    createChart.setSedeId(getSedesSeleccionadas());
    createChart.setPeriodo(2);
    createChart.servicioSolicitado();
  }

  @FXML void ventasMensuales(MouseEvent event) {
    createChart.setSedeId(getSedesSeleccionadas());
    createChart.setPeriodo(2);
    createChart.ventasMensuales();
  }

  @FXML void ventasSemanales(MouseEvent event) {
    createChart.setSedeId(getSedesSeleccionadas());
    createChart.setPeriodo(1);
    createChart.ventasSemanales();
  }

  /**
   * Abre el reporte de clientes registrados para las sedes que se deseen.
   * 
   * @param event not used.
   */
  @FXML void clientesRegistrados(MouseEvent event) {
    createChart.setSedeId(getSedesSeleccionadas());
    createChart.clientesRegistrados();
  }

  ArrayList<Integer> getSedesSeleccionadas() {
    ArrayList<Integer> sedeSeleccionada = new ArrayList<Integer>();

    if (sedeChoicebox.getValue().equals("Todas")) {
      var items = sedeChoicebox.getItems();

      for (int i = 1; i < items.size(); i++) {
        sedeSeleccionada.add(Sede.getIdSede(items.get(i)));
      }
    } else
      sedeSeleccionada.add(Sede.getIdSede(sedeChoicebox.getValue()));

    return sedeSeleccionada;
  }

}
