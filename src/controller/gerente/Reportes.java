package controller.gerente;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import model.Entities.Sede;

public class Reportes {

    @FXML
    private ChoiceBox<String> sedeChoicebox;
    CreateChart createChart;

    public void initialize(){
      ObservableList<String> sedesObservable = FXCollections.observableArrayList(); //Lista con las Sedes
      sedesObservable.add("Todas");
      sedesObservable.addAll(Sede.getSedesParsed());

      sedeChoicebox.getItems().addAll(sedesObservable);
      sedeChoicebox.setValue("Todas");
      createChart = new CreateChart();
      
    }

    @FXML
    void mediosDePago(MouseEvent event) {
      createChart.setSedeId(getSedesSeleccionadas());
      createChart.setPeriodo(2);
      createChart.medioDePago();
    }

    @FXML
    void paquetesEnviados(MouseEvent event) {
      createChart.setSedeId(getSedesSeleccionadas());
      createChart.setPeriodo(2);
      createChart.paquetesEnviados();
    }

    @FXML
    void servicioSolicitado(MouseEvent event) {
      createChart.setSedeId(getSedesSeleccionadas());
      createChart.setPeriodo(2);
      createChart.servicioSolicitado();
    }

    @FXML
    void ventasMensuales(MouseEvent event) {
      createChart.setSedeId(getSedesSeleccionadas());
      createChart.setPeriodo(2);
      createChart.ventasMensuales();
    }

    @FXML
    void ventasSemanales(MouseEvent event) {
      createChart.setSedeId(getSedesSeleccionadas());
      createChart.setPeriodo(1);
      createChart.ventasSemanales();
    }


    ArrayList<Integer> getSedesSeleccionadas(){
      ArrayList<Integer> sedeSeleccionada = new ArrayList<Integer>();

      if(sedeChoicebox.getValue().equals("Todas")){
        var items = sedeChoicebox.getItems();

        for (int i=1; i<items.size(); i++) {
          sedeSeleccionada.add(Sede.getIdSede(items.get(i)));
        }
      } 
      else 
        sedeSeleccionada.add(Sede.getIdSede(sedeChoicebox.getValue()));

      return sedeSeleccionada;
    }

    

}
