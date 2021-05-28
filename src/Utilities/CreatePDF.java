package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.*;

public class CreatePDF {

  public void pdfCreate() throws IOException {
    PDDocument document = new PDDocument();
    PDPage page = new PDPage();
    document.addPage(page);
    FacturaContenido factura = new FacturaContenido(document, page);
    factura.crearFactura();

    document.save(urlFactura());
    document.close();
  }

  public String urlFactura() throws IOException {
    String curDir = System.getProperty("user.dir") + "/src/resources/facturas/factura.pdf";
    File f = new File(curDir);
    f.delete();

    return curDir;
  }
}
