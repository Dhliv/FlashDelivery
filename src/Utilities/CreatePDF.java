package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.*;

public class CreatePDF {

  String infoFact; // INFORMACION QUE DEBE IR ESCRITA EN LA FACTURA.

  /**
   * INFORMACION QUE DEBE IR ESCRITA EN LA FACTURA.
   * @param infoFact
   */
  public CreatePDF(String infoFact) {
    this.infoFact = infoFact;
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
    FacturaContenido factura = new FacturaContenido(document, page, infoFact);
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
    String curDir = System.getProperty("user.dir") + "/src/resources/facturas/factura.pdf";
    File f = new File(curDir);
    f.delete();

    return curDir;
  }
}
