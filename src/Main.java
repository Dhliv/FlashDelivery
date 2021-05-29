import controller.Login;
import javafx.application.Application;
import javafx.stage.Stage;
import utilities.Globals;
import utilities.Ventana;
import utilities.CreatePDF;

public class Main extends Application 
{
    public static void main(String[] args) {
        System.getProperties().setProperty("org.jooq.no-logo", "true"); // no mostrar logo de jOOQ
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        String[][] contenido = {{"a","b"},{"c","d"}};
        String[] extra = {"a"};
        CreatePDF pdf = new CreatePDF(contenido, extra, extra, extra);
        pdf.pdfCreate();
        
        /*
        Globals.init(this);
        Globals.pantalla = stage;
        Ventana login = new Ventana("login", new Login());
        login.start(stage);
        */
    }

}
