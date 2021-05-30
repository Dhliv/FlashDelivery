package utilities;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.Matrix;

public class FacturaContenido {

  private PDDocument document;          // Documento PDF.

  private String[][] infoPaq;           //INFORMACION DEL PAQUETE QUE DEBE IR ESCRITA EN LA FACTURA.
  private String[] infoDest;            //INFORMACION DEL CLIENTE DESTINATARIO
  private String[] infoRem;             //INFORMACION DEL CLIENTE REMITENTE
  private String[] infoPago;            //INFORMACION DEL PAGO
  private static final int XTABLE = 72; //Pocision X respecto a la izquierda de la tabla
  private static final int XREM = 92;

  /**
   * Constructor de la clase FacturaContenido.
   * @param document  Documento PDF.
   * @param contenido Informacion que ha de ubicarse en la factura
   */
  public FacturaContenido(PDDocument document, String[][] infoPaq, String[] infoRem, String[] infoDest, String[] infoPago) {
    this.document = document;
    this.infoPaq = infoPaq;
    this.infoRem = infoRem;
    this.infoDest = infoDest;
    this.infoPago = infoPago;
  }

  /**
   * Crea la factura.
   * @throws IOException Sino no me deja compilar :c
   */
  public void crearFactura() throws IOException {
    PDPage page = document.getPage(0);
    PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
    Matrix mt = new Matrix(1f, 0f, 0f, -1f, page.getCropBox().getLowerLeftX(), page.getCropBox().getUpperRightY());
    contentStream.transform(mt);
    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
    PDFTableGenerator.drawTable(document, contentStream, infoPaq, XTABLE, 450);
    PDFClientGenerator.drawClient(document,contentStream, infoRem, 480, XREM);


    
    /*
    contentStream.beginText();
    contentStream.newLineAtOffset(25, 700);
    contentStream.setLeading(14.5f);
    

    contentStream.endText();
    */
    contentStream.close();
  }

}
