package controller.gerente;

import utilities.View;
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
      enviar(CreateChart.medioDePago());
    }

    @FXML
    void paquetesEnviados(MouseEvent event) {
      enviar(CreateChart.paquetesEnviados());
    }

    @FXML
    void servicioSolicitado(MouseEvent event) {
      enviar(CreateChart.servicioSolicitado());
    }

    @FXML
    void ventasMensuales(MouseEvent event) {
      enviar(CreateChart.ventasMensuales());
    }

    @FXML
    void ventasSemanales(MouseEvent event) {
      enviar(CreateChart.ventasSemanales());
    }

    void enviar(Object[] chartInformation){
      View.newView("vacio.completo", new ReporteEmpresa(
                                                        (String[]) chartInformation[0],
                                                        (String[]) chartInformation[1],
                                                        (String[]) chartInformation[2],
                                                        (Number[][]) chartInformation[3]
        ));
    }

}
