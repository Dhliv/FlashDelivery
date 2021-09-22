
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import controller.Login;
import controller.gerente.Admin;
import controller.operador.OperadorOficina;
import javafx.application.Application;
import javafx.stage.Stage;
import utilities.CreatePDF;
import utilities.Globals;
import utilities.SpecificAlerts;
import utilities.Ventana;
import utilities.View;

public class Main extends Application {
  public static void main(String[] args) {
    System.getProperties().setProperty("org.jooq.no-logo", "true"); // no mostrar logo de jOOQ
    launch(args);
  }

  // ! Borrar

  public void generatePDF() throws IOException {
    String[][] infoPaq = new String[1][2];
    String prueba = "El estándar ambiental Green Dragon está estructurado en 5 niveles:\n Compromiso con la Gestión Ambiental\n Comprensión de las responsabilidades ambientales\n Manejo de los impactos ambientales\n  Programa de gestión ambiental\n y Mejora ambiental continua\n Con el fin de proporcionar a las organizaciones los elementos de un sistema de gestión ambiental eficaz que sea apropiado para el contexto, la naturaleza y la escala de sus actividades y operaciones; un sistema para reducir los impactos ambientales de las organizaciones y proteger el medio ambiente, incluida la minimización de la degradación de los ecosistemas, el cambio climático, la pérdida de biodiversidad, la contaminación, el uso de recursos finitos, la gestión de desechos; además de aportar herramientas de implementación para lograr ISO14001: 2015, el sistema comunitario de ecogestión y auditoría medioambientales";

    for (int i = 0; i < infoPaq.length; i++) {
      infoPaq[i][0] = prueba;
      infoPaq[i][1] = Integer.toString((new Random(10)).nextInt());
    }
    String[] infoDest = { "Nombre", "Cedula", "Direccion", "Telefono", "Email" }; // INFORMACION DEL CLIENTE
                                                                                  // DESTINATARIO
    String[] infoPago = { Date.valueOf(LocalDate.now()).toString(), "160000", "150", "27000", "135000" }; // INFORMACION
                                                                                                          // DEL
                                                                                                          // PAGO
    CreatePDF pdf = new CreatePDF(infoPaq, infoDest, infoDest, infoPago);
    pdf.pdfCreate();
  }

  public void start(Stage stage) throws Exception {
    // generatePDF();

    View.init(this);
    Globals.init(this);
    SpecificAlerts.init();
    Globals.pantalla = stage;

    /*Ventana login = new Ventana("login", new Login());
    login.start(stage);*/

    Ventana vent2 = new Ventana("admin", new Admin("APA"));
    vent2.start(Globals.pantalla);

    /*Ventana vent = new Ventana("operadorOficina", new OperadorOficina(model.Entities.Empleado.cargarEmpleado("12345")));
    vent.start(Globals.pantalla);*/
  }
}
