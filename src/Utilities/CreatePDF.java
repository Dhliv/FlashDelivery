package utilities;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.*;

public class CreatePDF {

  public void pdfCreate() throws IOException {
    PDDocument document = new PDDocument();
    String curDir = System.getProperty("user.dir");
    document.save(curDir + "/src/resources/facturas/factura.pdf");
    PDPage my_page = new PDPage();
    document.addPage(my_page);
    document.close();
  }
}
