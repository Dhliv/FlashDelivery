package controller.gerente;

public class CreateChart {
  public static Object[] medioDePago(){
    String[] informe = {"Dinero Mensual","Mes","Dinero"};
    String[] fecha = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    String[] sedeNombre = {"Sede 1", "Sede 2"};
    Number[][] sedeInformacion = {{1,2,3,4,5,6,7,8,9,10,11,12},{1,2,3,4,5,6,7,8,9,10,11,12}};
    Object[] info = {informe, fecha, sedeNombre, sedeInformacion};
    return info;
  }
  public static Object[] paquetesEnviados(){
    return null;
  }
  public static Object[] servicioSolicitado(){
    return null;
  }
  public static Object[] ventasMensuales(){
    return null;
  }
  public static Object[] ventasSemanales(){
    return null;
  }
  
}
