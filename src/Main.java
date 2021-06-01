import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

import controller.Login;
import javafx.application.Application;
import javafx.stage.Stage;
import utilities.CreatePDF;
import utilities.Globals;
import utilities.Ventana;

public class Main extends Application 
{
    public static void main(String[] args) {
        System.getProperties().setProperty("org.jooq.no-logo", "true"); // no mostrar logo de jOOQ
        launch(args);
    }

    //! Borrar 
    public void generatePDF() throws IOException{
        String[][] infoPaq = new String[10][2];
        infoPaq[0][0] = "Descripción";
        infoPaq[0][1] = "Precio (COP)";
        for(int i=1; i<infoPaq.length; i++){ 
            infoPaq[i][0] = "Paquete" + Integer.toString(i);
            infoPaq[i][1] = Integer.toString((new Random(10)).nextInt());
        }
        String[] infoDest = {"Nombre","Cedula","Direccion","Telefono", "Email"};  //INFORMACION DEL CLIENTE DESTINATARIO
        String[] infoPago = {Date.valueOf(LocalDate.now()).toString(),"160000","150","27000","135000"};  //INFORMACION DEL PAGO
        CreatePDF pdf = new CreatePDF(infoPaq,infoDest,infoDest, infoPago);
        pdf.pdfCreate();
    }


    public void start(Stage stage) throws Exception {
        generatePDF();
        // Globals.init(this);
        // Globals.pantalla = stage;
        // Ventana login = new Ventana("login", new Login());
        // login.start(stage);
    }

}
