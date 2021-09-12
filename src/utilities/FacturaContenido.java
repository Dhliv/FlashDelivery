package utilities;

import java.util.Arrays;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.*;
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


  // public void printRandom(PDPageContentStream contentStream) throws IOException{
  //   float y=0f;
  //   for(int i=0; i<100; i++){
  //     contentStream.beginText();
  //       contentStream.newLineAtOffset(0, y);
  //       contentStream.showText("MaxTamaño: " + Float.toString(y));
  //     contentStream.endText();
  //     y+=10f;
  //   }
  // }


  /**
   * Función que permite imprimir la información
   * de los paquetes sin salirse del limite inferior de las paginas del PDF
   * 
   * @param contentStream ContentStream en donde se dibuja (documento, pagina)
   * @param rowHeight Tamaño de las celdas de la tabla
   * @throws IOException
   */
  public void drawTableMultiPage(PDPageContentStream contentStream, float rowHeight) throws IOException{
    final int XTABLE = 72; //Pocision X respecto a la izquierda de la tabla
    final int YTABLE_PAGE1 = 450-15; //Posición Y inicial de la tabla en la primer pagina.
    final int YTABLE_NEXT_PAGE = 770-30; //Posición Y inicial de las paginas siguientes.
    final int YMIN = 70; //Posición minima en Y en donde se es permitido dibujar.
    
    int yTable = YTABLE_PAGE1;
    int rowsPerPage = (int)((yTable-YMIN)/rowHeight);//Filas de la columna por pagina
    int nextRow = 0;

    //Dibujar las primeras N paginas sin desbordamiento
    while(infoPaq.length-nextRow > rowsPerPage){ //Split

      PDFTableGenerator.drawTable(document, contentStream, Arrays.copyOfRange(infoPaq, nextRow, nextRow+rowsPerPage), XTABLE, yTable, rowHeight);
      PDFTableGenerator.drawHeader();

      contentStream.close();
      contentStream.close();

      nextRow += rowsPerPage;
      yTable = YTABLE_NEXT_PAGE;
      rowsPerPage = (int)((yTable-YMIN)/rowHeight);
      
      PDPage page = new PDPage();
      document.addPage(page);
      contentStream = new PDPageContentStream(document, page);
      
    }
    
    // Dibujar la ultima pagina
    PDFTableGenerator.drawTable(document, contentStream, Arrays.copyOfRange(infoPaq, nextRow, infoPaq.length), XTABLE, yTable, rowHeight);
    PDFTableGenerator.drawHeader();
    contentStream.close();
    
    

  }


  /**
   * Crea la factura.
   * @throws IOException Sino no me deja compilar :c>
   */
  public void crearFactura() throws IOException {
    final int XREM = 92; //Pocision X respecto a la izquierda de la información del remitente
    final int XDEST = 302; //Pocision X respecto a la izquierda de la información del remitente
    final float ROWHEIGTH = 20f; //Ancho de las filas.
    final float tableHeight = ROWHEIGTH*infoPaq.length; //Ancho de la tabla

    PDPage page = document.getPage(0);
    
    //HAbilita la edición de un PDF
    PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
    //Pone el contenido en la orientación correcta (UP)
    Matrix mt = new Matrix(1f, 0f, 0f, -1f, page.getCropBox().getLowerLeftX(), page.getCropBox().getUpperRightY());
    contentStream.transform(mt);

    //Dibuja la información exceptuando la tabla
    PDFBillGenerator.drawBillInfo(document, contentStream, infoPago[0], idPDF);
    PDFClientGenerator.drawClient(document,contentStream, infoRem, 480, XREM);
    PDFClientGenerator.drawClient(document,contentStream, infoDest, 480, XDEST);

    //Dibuja la tabla
    drawTableMultiPage(contentStream, ROWHEIGTH);
    PDFBillGenerator.drawPayInfo(document, contentStream, Arrays.copyOfRange(infoPago,1,infoPago.length), tableHeight);
    contentStream.close();
    
    

    
  }

}
