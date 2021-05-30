package utilities;

import java.io.IOException;

import org.apache.pdfbox.contentstream.PDContentStream;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PDFBillGenerator {

  private static int rowHeight = 25;     //SEPARACIÓN ENTRE CADA STRING
  private static int infoPagoSize;       //DATOS QUE SE VAN A UBICAR EN LA INFORMACION DE PAGO
  private static final float XPOS = 439; //POSICION EN X DE LA INFORMACION DE PAGO
  private static final  float LINELENGTH = 80;

  public static void drawPayInfo(PDDocument document, PDPageContentStream contentStream, String[] infoPago, float yInit) throws IOException {
    yInit-=10; //Añade separacion de la tabla
    float y = yInit;
    infoPagoSize = infoPago.length;

    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
    for(int i=infoPagoSize-1; i>=0; i--){
      contentStream.beginText();
      contentStream.newLineAtOffset(XPOS+LINELENGTH/4, y);
      contentStream.showText(infoPago[i]);
      contentStream.endText();
      y -= rowHeight;
    }
    
    drawLines(document, contentStream, yInit);
    drawTextInfo(document, contentStream, yInit);
  }

  public static void drawTextInfo(PDDocument document, PDPageContentStream contentStream, float yInit) throws IOException{
    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
    float y = yInit+5;
    String[] info = {"SUBTOTAL", "    SEGURO", " IMPUESTO", "        TOTAL"};
    //info = GeneralString.textToRight(info);
    
    for(int i = 0; i < infoPagoSize; i++){
      contentStream.beginText();
      contentStream.newLineAtOffset(XPOS-55, y);
      contentStream.showText(info[i]);
      contentStream.endText();
      y -= rowHeight;
    }
  }

  /**
   * Dibuja las lineas decorativas en la seccion de Pago del PDF
   * @param document PDF ha modificar
   * @param contentStream el contentStream del PDF
   * @param yInit La posición Y desde donde se empieza a escribir
   * @throws IOException
   */
  public static void drawLines(PDDocument document, PDPageContentStream contentStream, float yInit) throws IOException{
    final float BORDERWIDTH = 1.5f;
    float yLine = yInit - 2 - BORDERWIDTH;
    PDImageXObject lineImage = PDImageXObject.createFromFile("src/resources/images/darkLightGray.png", document);

    for(int i=0; i<infoPagoSize; i++){
      contentStream.drawImage(lineImage, XPOS, yLine, LINELENGTH, BORDERWIDTH);
      yLine-= rowHeight;
    }

  }


  /**
   * Dibuja los datos propios de la factura en el PDF
   * @param date La fecha en la que se realiza el pago en formato String
   * @param idPDF el ID del pdf en formato String
   * @throws IOException
   */
  public static void drawBillInfo(PDDocument document, PDPageContentStream contentStream ,String date, String idPDF) throws IOException{
    final int DATE_XPOS = 456;
    final int DATE_YPOS = 677;
    final int IDPDF_XPOS = 464;
    final int IDPDF_YPOS = 640;
    
    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 9);
    contentStream.beginText();
      contentStream.newLineAtOffset(DATE_XPOS, DATE_YPOS);
      contentStream.showText(date);
    contentStream.endText();
    contentStream.beginText();
      contentStream.newLineAtOffset(IDPDF_XPOS, IDPDF_YPOS);
      contentStream.showText("No. "+idPDF);
    contentStream.endText();
  }
}
