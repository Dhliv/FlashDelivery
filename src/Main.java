import java.sql.Date;
import java.time.LocalDate;

import controller.Login;
import javafx.application.Application;
import javafx.stage.Stage;
import utilities.Globals;
import utilities.Ventana;
import utilities.CreatePDF;
import utilities.PDFCreateExtraFunctions;

public class Main extends Application 
{
    public static void main(String[] args) {
        System.getProperties().setProperty("org.jooq.no-logo", "true"); // no mostrar logo de jOOQ
        launch(args);
    }

    


    public void start(Stage stage) throws Exception {
        String[][] infoPaq = {{"a","b"},{"a","b"},{"a","b"},{"a","b"},{"a","b"},{"a","b"},{"a","b"},{"a","b"},{"a","b"},{"a","b"},{"a","b"}}; // INFORMACION DEL PAQUETE QUE DEBE IR ESCRITA EN LA FACTURA.
        String[] infoDest = {"Nombre","Cedula","Direccion","Telefono", "Email"};  //INFORMACION DEL CLIENTE DESTINATARIO
        String[] infoPago = {Date.valueOf(LocalDate.now()).toString(),"160000","150","27000","135000"};  //INFORMACION DEL PAGO
        CreatePDF pdf = new CreatePDF(infoPaq,infoDest,infoDest, infoPago);
        pdf.pdfCreate();
        // Globals.init(this);
        // Globals.pantalla = stage;
        // Ventana login = new Ventana("login", new Login());
        // login.start(stage);
    }

}
