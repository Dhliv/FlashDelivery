package utilities;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.*;
public class CreatePDF {

  public void pdfCreate() throws IOException {
    PDDocument document = new PDDocument();
    PDPage page = new PDPage();
    document.addPage(page);
    FacturaContenido factura = new FacturaContenido(document,page);
    factura.crearFactura();

    
    document.save(urlFactura(document));
    document.close();
  }

  public String urlFactura(PDDocument document) throws IOException{
    String curDir = System.getProperty("user.dir");
    curDir = curDir + "/src/resources/facturas/factura.pdf";
    return curDir;
  }
}
