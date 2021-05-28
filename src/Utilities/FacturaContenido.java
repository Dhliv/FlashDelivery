package utilities;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class FacturaContenido {

  PDDocument document; // Documento PDF.
  PDPage page; // Primer pagina de la factura.
  String contenido; // Informacion que ha de ubicarse en la factura.

  /**
   * Constructor de la clase FacturaContenido.
   * @param document  Documento PDF.
   * @param page      Primer pagina de la factura.
   * @param contenido Informacion que ha de ubicarse en la factura
   */
  public FacturaContenido(PDDocument document, PDPage page, String contenido) {
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
    contentStream.beginText();
    contentStream.newLineAtOffset(25, 700);
    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
    contentStream.setLeading(14.5f);
    contenido += "\n";
    drawText(parseText(contenido), contentStream);

    contentStream.endText();
    contentStream.close();
  }

  /**
   * Divide el texto en lineas por cada salto de linea encontrado.
   * 
   * @param text Texto que se quiere transformar
   * @return Lista de Strings con el formato requerido.
   * 
   */
  public ArrayList<String> parseText(String text) {

    int pos = 0;
    ArrayList<String> lines = new ArrayList<>();
    String line = "";

    for (int i = 0; i < text.length(); i++) {
      if (text.charAt(i) == ('\n')) {
        line = text.substring(pos, i);
        lines.add(line);
        pos = i + 1;
      }
    }

    if (pos < text.length()) lines.add(text.substring(pos, text.length() - 1));
    return lines;
  }

  /**
   * Dibuja el texto en el PDF
   * 
   * @param lines   Lista de lineas que se desean dibujar en el PDF;
   * @param content Pagina en donde se va a dibujar el texto;
   * @throws IOException
   */
  private void drawText(ArrayList<String> lines, PDPageContentStream content) throws IOException {
    for (int i = 0; i < lines.size(); i++) {
      content.showText(lines.get(i));
      content.newLine();
    }
  }
}
