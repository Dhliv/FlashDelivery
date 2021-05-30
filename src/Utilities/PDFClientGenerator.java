package utilities;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


public class PDFClientGenerator {

  private static final int TEXTSEPARATION = 18; //Separacion entre una linea de texto y otra


  public static void drawClient(PDDocument document, PDPageContentStream contentStream, String[] cliente, float yIni, float xPos) throws IOException{
    float newY = yIni;
    for(int i=cliente.length-1; i>=0; i--){
      contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        contentStream.newLineAtOffset(xPos, newY);
        contentStream.showText(cliente[i]);
      contentStream.endText();
      newY+= TEXTSEPARATION;
    }
  }
}
