package controller.gerente;

import java.util.ArrayList;
import java.util.List;

import model.Entities.Sede;
import utilities.View;

public class CreateChart {

  private static String[] informe;
  private static String[] fecha;
  private static ArrayList<String> sedeNombre;
  private static Number[][] sedeInformacion;
  private static final String[] MESES = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};

  /**
   * Carga los datos que necesita ReporteEmpresa para crear un Chart
   * relacionada a los métodos de pago.
   * @param sedeId  Lista con los IDs de las sedes solicitadas.
   */
  public static void medioDePago(ArrayList<Integer> sedeId){

    informe = new String[]{"Dinero Mensual","Mes","Dinero"};
    fecha = MESES;
    sedeNombre =  new ArrayList<String>();
    sedeInformacion = new Number[2][];

    for(int i=0; i<sedeId.size(); i++){
      sedeNombre.add(Sede.parseSede(sedeId.get(i)));                      //Agrega el nombre de la sede con ID "sedeId.get(i)"
      sedeInformacion[i] = model.Reportes.getMedioDePago(sedeId.get(i));  //Agrega la información de la query de pago.
    }
    
    View.newView("vacio.completo", new ReporteEmpresa(informe,fecha,sedeNombre.toArray(new String[0]),sedeInformacion));
  }
  public static void paquetesEnviados(){
    
  }
  public static void servicioSolicitado(){
  }
  public static void ventasMensuales(){
  }
  public static void ventasSemanales(){
  }
  
}
