package utilities;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
public class PDFBillGenerator {

  public static void drawPayInfo(PDDocument document, PDPageContentStream contentStream, String[] infoPago, int row){
    
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
