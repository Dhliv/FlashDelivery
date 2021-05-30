package utilities;

import java.util.Arrays;
import java.util.Iterator;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.*;
<<<<<<< HEAD
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
=======
>>>>>>> 6bcd1fa8097e93c7f2f9052c5a5ff2d0fabb3db1
import org.apache.pdfbox.util.Matrix;

public class FacturaContenido {

  private PDDocument document;          // Documento PDF.

  private String[][] infoPaq;           //INFORMACION DEL PAQUETE QUE DEBE IR ESCRITA EN LA FACTURA.
  private String[] infoDest;            //INFORMACION DEL CLIENTE DESTINATARIO
  private String[] infoRem;             //INFORMACION DEL CLIENTE REMITENTE
  private String[] infoPago;            //INFORMACION DEL PAGO
  private String idPDF;                  //FECHA EN LA QUE SE REALIZA EL PAGO

  /**
   * Constructor de la clase FacturaContenido.
   * @param document  Documento PDF.
   * @param contenido Informacion que ha de ubicarse en la factura
   */
  public FacturaContenido(PDDocument document, String[][] infoPaq, String[] infoRem, String[] infoDest, String[] infoPago, String idPDF) {
    this.document = document;
    this.infoPaq = infoPaq;
    this.infoRem = infoRem;
    this.infoDest = infoDest;
    this.infoPago = infoPago;
    this.idPDF = idPDF;
  }




  /**
   * Crea la factura.
   * @throws IOException Sino no me deja compilar :c
   */
  public void crearFactura() throws IOException {
    PDPage page = document.getPage(0);
    PDPage page2 = document.getPage(1);
    
    //HAbilita la edición de un PDF
    PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
    //Pone el contenido en la orientación correcta (UP)
    Matrix mt = new Matrix(1f, 0f, 0f, -1f, page.getCropBox().getLowerLeftX(), page.getCropBox().getUpperRightY());
    contentStream.transform(mt);

    final int XTABLE = 72; //Pocision X respecto a la izquierda de la tabla
    final int XREM = 92; //Pocision X respecto a la izquierda de la información del remitente
    final int XDEST = 302; //Pocision X respecto a la izquierda de la información del remitente
    
    System.out.println(infoPago[0]);
    System.out.println(idPDF);


    PDFBillGenerator.drawBillInfo(document, contentStream, infoPago[0], idPDF);
    PDFClientGenerator.drawClient(document,contentStream, infoRem, 480, XREM);
    PDFClientGenerator.drawClient(document,contentStream, infoDest, 480, XDEST);
    PDFTableGenerator.drawTable(document, contentStream, infoPaq, XTABLE, 450);
    PDFBillGenerator.drawPayInfo(document, contentStream, Arrays.copyOfRange(infoPago,1,infoPago.length), infoPaq.length);
    contentStream.close();

    
  }

}
