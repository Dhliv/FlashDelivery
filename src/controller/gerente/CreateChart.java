package controller.gerente;

import java.util.ArrayList;
import java.util.function.Consumer;

import model.Entities.Sede;
import utilities.View;

public class CreateChart {

  private String[] informe;
  private String[] fecha;
  private ArrayList<String> sedeNombre;
  private Number[][] sedeInformacion;
  private final Integer DIAS = 0;
  private final Integer SEMANAS = 1;
  private final Integer MESES = 2;
  private final Integer AÑOS = 3;
  private final String[][] intervalos;
  private ArrayList<Integer> sedeId;
  
  /**
   * Crea un Diagrama de barras con los datos de las sedes y el periodo seleccionado.
   */
  public CreateChart(){

    this.intervalos = new String[4][];
    //TODO @Winja HACER FUNCIÓN PARA LOS DÍAS, MESES Y AÑOS.
    this.intervalos[DIAS] = new String[]{"Lunes","Marter","Miercoles","Jueves","Viernes","Sabado","Domingo"};
    this.intervalos[SEMANAS] = new String[]{"Lunes","Marter","Miercoles","Jueves","Viernes","Sabado","Domingo"};
    this.intervalos[MESES] = new String[]{"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    this.intervalos[AÑOS] = new String[]{};

    this.fecha = intervalos[MESES];
    this.sedeNombre = new ArrayList<String>();
  }

  /**
   * Cambia las sedes seleccionadas para mostrar en el diagrama.
   * Además, modifica el array sedeNombre que almacena los nombres
   * correspondientes a las sedes.
   * @param sedeId Lista de sedes que se mostraran en el diagrama
   */
  public void setSedeId(ArrayList<Integer> sedeId){
    this.sedeId = sedeId;
    sedeNombre.clear();
    for (Integer sede : sedeId) 
      sedeNombre.add(Sede.parseSede(sede));
    
    this.sedeInformacion = new Number[sedeId.size()][];
  }

  /**
   * Cambia el periodo en el que se mostrarán los datos.
   * @param periodo valor numerico que representa:
   * 0 - DIAS
   * 1 - SEMANAS
   * 2 - MESES
   * 3 - AÑOS
   */
  public void setPeriodo(Integer periodo){
    this.fecha = intervalos[periodo];
  }

  /**
   * Carga los datos que necesita ReporteEmpresa para crear un 
   * diagrama de barras relacionada a los métodos de pago.
   */
  public void medioDePago(){

    informe = new String[]{"Dinero Mensual","Mes","Dinero"};

    for(int i=0; i<sedeId.size(); i++){
      sedeInformacion[i] = model.Reportes.getMedioDePago(sedeId.get(i));  //Agrega la información de la query de pago.
    }


    View.newView("vacio.completo", new ReporteEmpresa(informe,fecha,sedeNombre.toArray(new String[0]),sedeInformacion));
  }

  /**
   * Carga los datos que necesita ReporteEmpresa para crear un 
   * diagrama de barras relacionada a los paquetes enviados
   */
  public void paquetesEnviados(){
    
    informe = new String[]{"Paquetes enviados por mes", "Mes", "Dinero"};
    
    for(int i=0; i<sedeId.size(); i++){
      sedeInformacion[i] = model.Reportes.getPaquetesEnviados(sedeId.get(i));  //Agrega la información de la query de pago.
    }

    View.newView("vacio.completo", new ReporteEmpresa(informe,fecha,sedeNombre.toArray(new String[0]),sedeInformacion));
  }

  /**
   * Carga los datos que necesita ReporteEmpresa para crear un 
   * diagrama de barras relacionada a los servicios solicitados.
   */
  public void servicioSolicitado(){
    
    informe = new String[]{"servicios solicitados por mes", "Mes", "Dinero"};
    for(int i=0; i<sedeId.size(); i++){
      sedeInformacion[i] = model.Reportes.getServicioSoliticado(sedeId.get(i));  //Agrega la información de la query de pago.
    }

    View.newView("vacio.completo", new ReporteEmpresa(informe,fecha,sedeNombre.toArray(new String[0]),sedeInformacion));
  }

  /**
   * Carga los datos que necesita ReporteEmpresa para crear un 
   * diagrama de barras relacionada a las ventas mensuales
   */
  public void ventasMensuales(){
    informe = new String[]{"Ventas mensuales", "Mes", "Dinero"};

    for(int i=0; i<sedeId.size(); i++){
      sedeInformacion[i] = model.Reportes.ventas(sedeId.get(i), MESES);  //Agrega la información de la query de pago.
    }
    
    View.newView("vacio.completo", new ReporteEmpresa(informe,fecha,sedeNombre.toArray(new String[0]),sedeInformacion));
  }

  /**
   * Carga los datos que necesita ReporteEmpresa para crear un 
   * diagrama de barras relacionada a las ventas semanales
   */
  public void ventasSemanales(){
    
    informe = new String[]{"Ventas semanales", "Mes", "Dinero"};

    for(int i=0; i<sedeId.size(); i++){
      sedeInformacion[i] = model.Reportes.ventas(sedeId.get(i), SEMANAS);  //Agrega la información de la query de pago.
    }

    View.newView("vacio.completo", new ReporteEmpresa(informe,fecha,sedeNombre.toArray(new String[0]),sedeInformacion));
  }
  
}
