package controller.gerente;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import utilities.Globals;
import utilities.View;

public class ReporteEmpresa implements Initializable {

    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";
    private String[] fecha;           //Fechas o Intervalos de tiempo que se mostrarán en el grafico
    private String[] sedeNombre;      //Nombre de la o las sedes que son solicitadas en el reporte
    private Number[][] sedeInformacion; //Información cuantitativa que se quiere mostrar en el grafico
    private Integer NUMERO_FECHAS;
    private Integer NUMERO_SEDES;
    private String[] informe;

    // @FXML
    // private ChoiceBox<?> sedeChoicebox;
    @FXML
    private BarChart<String, Number> barchart;

    /**
     * Crea una grafica de barras.
     * 
     * @param informe           Array conformado por: nombre del informe, eje X y eje Y.
     * @param fecha             Textos UNICOS que aparecen en el eje X
     * @param sedeNombre        Nombre de la o las sedes que son solicitadas en el reporte
     * @param sedeInformacion   Información cuantitativa que se quiere mostrar en el grafico
     *                          En el primer Array identifica una sede, en el segundo
     *                          se añaden cada uno de los datos correspondientes a esta.
     */
    public ReporteEmpresa(String[] informe, String[] fecha, String[] sedeNombre, Number[][] sedeInformacion){
      this.fecha = fecha;
      this.sedeNombre = sedeNombre;
      this.sedeInformacion = sedeInformacion;
      NUMERO_FECHAS = fecha.length;
      NUMERO_SEDES = sedeInformacion.length;
      this.informe = informe;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
      final CategoryAxis xAxis = new CategoryAxis();
      final NumberAxis yAxis = new NumberAxis();

      barchart.setTitle(informe[0]);  //Se añade titulo
      xAxis.setLabel(informe[1]);     //Se añade nombre de eje X
      yAxis.setLabel(informe[2]);     //Se añade nombre de eje Y

        XYChart.Data<String,Number> sedeChart;
        XYChart.Series <String, Number> datosIntervalo;
        for(int j=0; j<NUMERO_SEDES; j++){

          datosIntervalo = new XYChart.Series<String,Number>();
          datosIntervalo.setName(sedeNombre[j]);
          
        for(int i=0; i<NUMERO_FECHAS; i++){
          
            sedeChart = new XYChart.Data<String,Number>(fecha[i], sedeInformacion[j][i]);
            datosIntervalo.getData().add(sedeChart);
          }

          barchart.getData().add(datosIntervalo);
        }
        
    }
}
