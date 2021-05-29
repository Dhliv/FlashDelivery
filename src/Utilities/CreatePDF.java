package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.*;

public class CreatePDF {

  String[][] infoPaq; // INFORMACION DEL PAQUETE QUE DEBE IR ESCRITA EN LA FACTURA.

  /**
   * INFORMACION DEL PAQUETE QUE DEBE IR ESCRITA EN LA FACTURA.
   * @param infoPaq
   */
  public CreatePDF(String[][] infoPaq) {
    this.infoPaq = infoPaq;
  }

  /**
   * Crea el PDF en donde se encuentra toda la información de infoFact con el
   * formato esperado para la factura.
   * @throws IOException
   */
  public void pdfCreate() throws IOException {
    PDDocument document = new PDDocument();
    PDPage page = new PDPage();
    document.addPage(page);
    FacturaContenido factura = new FacturaContenido(document, page, infoPaq);
    factura.crearFactura();

    document.save(urlFactura());
    document.close();
  }

  /**
   * 
   * @return
   * @throws IOException
   */
  public String urlFactura() throws IOException {
    //TODO ANTES DE CREAR, BORRAR EL ANTERIOR O CAMBIAR POR nombreArchivo.pdf (n)
    String curDir = System.getProperty("user.dir") + "/src/resources/facturas/factura.pdf";
    File f = new File(curDir);
    if(f.exists()) f.delete();

    return curDir;
  }
}
