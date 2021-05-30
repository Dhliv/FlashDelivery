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
        
        String[][] contenido = {{"a","b"},{"c","d"}, {"e","f"}, {"g","h"},{"a","b"},{"c","d"}, {"e","f"}, {"g","h"}};
        String[] pago = {Date.valueOf(LocalDate.now()).toString(),"Subtotal","Impuesto","Seguro","Total"};
        String[] cliente = {"Alejandro Pergueza","1004680841","Transversal 4C #1608","3136639980","practica22correo@gmail.com"};
        CreatePDF pdf = new CreatePDF(contenido, cliente, cliente, pago);
        pdf.pdfCreate("55");
        
        
        
        // Globals.init(this);
        // Globals.pantalla = stage;
        // Ventana login = new Ventana("login", new Login());
        // login.start(stage);
        
    }

}
