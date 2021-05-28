package utilities;


import java.io.IOException;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class FacturaContenido {
  public void crearFactura(PDPageContentStream contentStream) throws IOException{
    contentStream.beginText();
      contentStream.newLineAtOffset(25, 700);
      contentStream.setFont( PDType1Font.TIMES_ROMAN, 12 );
      contentStream.showText("text");
      contentStream.endText();
    contentStream.close();
  }
}
