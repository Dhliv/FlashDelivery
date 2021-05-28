package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.*;

public class CreatePDF {

  public void pdfCreate() throws IOException {
    FacturaContenido factura = new FacturaContenido();
    PDDocument document = new PDDocument();
    urlFactura(document);
    PDPage page = new PDPage();
    document.addPage(page);
    PDPageContentStream contentStream = new PDPageContentStream(document, page);

    factura.crearFactura(contentStream);

    document.close();
  }

  public void urlFactura(PDDocument document) throws IOException {
    String curDir = System.getProperty("user.dir") + "/src/resources/facturas/factura.pdf";
    File f = new File(curDir);
    f.delete();

    document.save(curDir);
  }
}
