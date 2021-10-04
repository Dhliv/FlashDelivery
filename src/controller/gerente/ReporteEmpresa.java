package controller.gerente;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import utilities.View;

/**
 * Controlador de la vista vacio.completo.fxml
 * 
 * @author Alejandro Pergueza Amaya (El crack)
 * @author David Henao
 * @version 1.1
 * @since 4/10/2021
 */
public class ReporteEmpresa implements Initializable {

  private String[] fecha; // Fechas o Intervalos de tiempo que se mostrarán en el grafico
  private String[] sedeNombre; // Nombre de la o las sedes que son solicitadas en el reporte
  private Number[][] sedeInformacion; // Información cuantitativa que se quiere mostrar en el grafico
  private Integer NUMERO_FECHAS;
  private Integer NUMERO_SEDES;
  private String[] informe;
  private boolean seeButtons;
  private ArrayList<Integer> sedeId;

  @FXML
  private Button btnEfectivo;

  @FXML
  private Button btnTCredito;

  @FXML
  private Button btnTDebito;

  @FXML
  private BarChart<String, Number> barchart;

  /**
   * Crea una grafica de barras.
   * 
   * @param informe         Array conformado por nombre de: {informe, eje X y eje
   *                        Y}.
   * @param fecha           Textos UNICOS que aparecen en el eje X
   * @param sedeNombre      Nombre de la o las sedes que son solicitadas en el
   *                        reporte
   * @param sedeInformacion Información cuantitativa que se quiere mostrar en el
   *                        grafico En la primer dimesión se identifica una sede,
   *                        en la segunda se añaden cada uno de los datos
   *                        correspondientes a esta.
   */
  public ReporteEmpresa(String[] informe, String[] fecha, String[] sedeNombre, Number[][] sedeInformacion,
      boolean seeButtons) {
    this.fecha = fecha;
    this.sedeNombre = sedeNombre;
    this.sedeInformacion = sedeInformacion;
    NUMERO_FECHAS = fecha.length;
    NUMERO_SEDES = sedeInformacion.length;
    this.informe = informe;
    this.seeButtons = seeButtons;
  }

  /**
   * Cambia la visibilidad de los botones especificos del reporte medio de pago.
   */
  private void changeVisibility() {
    btnEfectivo.setVisible(seeButtons);
    btnTCredito.setVisible(seeButtons);
    btnTDebito.setVisible(seeButtons);
  }

  /**
   * Se obtienen los ids de la sede para ejecutar reportes relacionados a metodo
   * de pago.
   * 
   * @param sedes
   */
  public void setSedeId(ArrayList<Integer> sedes) {
    sedeId = sedes;
  }

  /**
   * Cambia la información que se desplegará en el chart: sedeInformación y Titulo
   * del informe.
   * 
   * @param medioPago Medio de pago a consultar.
   */
  private void changeContentOfChart(String medioPago) {
    CreateChart cc = new CreateChart();
    cc.setSedeId(sedeId);
    cc.medioDePago(medioPago);
  }

  /**
   * Vuelve a la ventana anterior.
   * 
   * @param event Not used.
   */
  @FXML
  void atras(MouseEvent event) {
    View.cambiar("reportes", new Reportes());
  }

  /**
   * Cambia el contenido del chart a la consulta hecha con el metodo de pago
   * Efectivo.
   * 
   * @param event Not used.
   */
  @FXML
  void metodoPagoEfectivo(MouseEvent event) {
    changeContentOfChart("Efectivo");
  }

  /**
   * Cambia el contenido del chart a la consulta hecha con el metodo de pago
   * Credito.
   * 
   * @param event Not used.
   */
  @FXML
  void metodoPagoTCredito(MouseEvent event) {
    changeContentOfChart("Credito");
  }

  /**
   * Cambia el contenido del chart a la consulta hecha con el metodo de pago
   * Debito.
   * 
   * @param event Not used.
   */
  @FXML
  void metodoPagoTDebito(MouseEvent event) {
    changeContentOfChart("Debito");
  }

  /**
   * Inserta los datos deseados en el chart.
   */
  private void insertDataInChart() {
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();

    barchart.setTitle(informe[0]); // Se añade titulo
    xAxis.setLabel(informe[1]); // Se añade nombre de eje X
    yAxis.setLabel(informe[2]); // Se añade nombre de eje Y

    XYChart.Data<String, Number> sedeChart;
    XYChart.Series<String, Number> datosIntervalo;
    for (int j = 0; j < NUMERO_SEDES; j++) {

      datosIntervalo = new XYChart.Series<String, Number>();
      datosIntervalo.setName(sedeNombre[j]);

      for (int i = 0; i < NUMERO_FECHAS; i++) {

        sedeChart = new XYChart.Data<String, Number>(fecha[i], sedeInformacion[j][i]);
        datosIntervalo.getData().add(sedeChart);
      }

      barchart.getData().add(datosIntervalo);
    }
  }

  /**
   * Inicializa los componentes gráficos necesarios: chart y botones relacionados
   * al metodo de pago.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    changeVisibility();
    insertDataInChart();
  }
}
