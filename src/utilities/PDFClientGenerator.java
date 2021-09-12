package utilities;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


/**
 * Dibuja la información del cliente con formato en un archivo PDF.
 */
public class PDFClientGenerator {

  private static final int TEXTSEPARATION = 18; //Interlineado


  /**
   * Dibuja la información de cliente iniciando desde la posición y = yIni y x = xPos. 
   * @param document Documento PDF
   * @param contentStream Pagina y documento en donde actuará la función
   * @param cliente Información a imprimir
   * @param yIni Posición incial en y
   * @param xPos Posición inicial en x
   * @throws IOException
   */
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
