package utilities;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.*;
public class CreatePDF {

  public void pdfCreate() throws IOException{
    PDDocument document = new PDDocument();
    document.save("D:/my_doc.pdf");
    document.close();
  }
}
