import controller.Login;
import controller.OperadorOficina;
import javafx.application.Application;
import javafx.stage.Stage;
import utilities.Globals;
import utilities.Ventana;
import utilities.CreatePDF;

public class Main extends Application {
    public static void main(String[] args) {
        System.getProperties().setProperty("org.jooq.no-logo", "true"); // no mostrar logo de jOOQ
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        CreatePDF pdf = new CreatePDF();
        pdf.pdfCreate();
        
        Globals.init(this);
        Globals.pantalla = stage;
        Ventana login = new Ventana("login", new Login());
        login.start(stage);

        // # prueba para la vista operador.cliente
        /*stage.setScene(Globals.loadScene("operadorOficina"));
        stage.show();*/
    }

}
