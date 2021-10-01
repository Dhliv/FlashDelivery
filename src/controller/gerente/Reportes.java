package controller.gerente;

import utilities.View;

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

    public void initialize(){
      ObservableList<String> sedesObservable = FXCollections.observableArrayList(); //Lista con las Sedes
      sedesObservable.add("Todas");
      sedesObservable.addAll(Sede.getSedesParsed());

      sedeChoicebox.getItems().addAll(sedesObservable);
      sedeChoicebox.setValue("Todas");
    }

    @FXML
    void mediosDePago(MouseEvent event) {
      ArrayList<Integer> sedeSeleccionada = new ArrayList<Integer>();

      if(sedeChoicebox.getValue().equals("Todas")){
        var items = sedeChoicebox.getItems();
        for (int i=1; i<items.size(); i++) {
          sedeSeleccionada.add(Sede.getIdSede(items.get(i)));
        }
      } 
      else sedeSeleccionada.add(Sede.getIdSede(sedeChoicebox.getValue()));
      
      CreateChart.medioDePago(sedeSeleccionada);
    }

    @FXML
    void paquetesEnviados(MouseEvent event) {
      CreateChart.paquetesEnviados();
    }

    @FXML
    void servicioSolicitado(MouseEvent event) {
      CreateChart.servicioSolicitado();
    }

    @FXML
    void ventasMensuales(MouseEvent event) {
      CreateChart.ventasMensuales();
    }

    @FXML
    void ventasSemanales(MouseEvent event) {
      CreateChart.ventasSemanales();
    }

}
