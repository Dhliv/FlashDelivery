package utilities;


import java.io.IOException;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class FacturaContenido {

  PDDocument document;
  PDPage page;

  public FacturaContenido(PDDocument document, PDPage page){
    this.document = document;
    this.page = page;
  }
  public void crearFactura() throws IOException{
    PDPageContentStream contentStream = new PDPageContentStream(document, document.getPage(0));
    contentStream.beginText();
      contentStream.newLineAtOffset(25, 700);
      contentStream.setFont( PDType1Font.TIMES_ROMAN, 12 );
      contentStream.showText("text");
      contentStream.endText();
    contentStream.close();
  }
}
