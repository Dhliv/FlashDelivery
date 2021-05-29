package utilities;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import utilities.PDFTableGenerator;

public class FacturaContenido {

  private PDDocument document; // Documento PDF.
  private PDPage page; // Primer pagina de la factura.
  private String[][] contenido; // Informacion que ha de ubicarse en la factura.
  private static PDFTableGenerator pdfTableGenerator;

  /**
   * Constructor de la clase FacturaContenido.
   * @param document  Documento PDF.
   * @param page      Primer pagina de la factura.
   * @param contenido Informacion que ha de ubicarse en la factura
   */
  public FacturaContenido(PDDocument document, PDPage page, String[][] contenido) {
    this.document = document;
    this.page = page;
    this.contenido = contenido;
  }

  /**
   * Crea la factura.
   * @throws IOException Sino no me deja compilar :c
   */
  public void crearFactura() throws IOException {
    PDPageContentStream contentStream = new PDPageContentStream(document, document.getPage(0));
    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
    pdfTableGenerator.drawTable(document, contentStream, contenido, 25, 700);
    contentStream.beginText();
    contentStream.newLineAtOffset(25, 700);
    contentStream.setLeading(14.5f);
    

    contentStream.endText();

    contentStream.close();
  }

}
