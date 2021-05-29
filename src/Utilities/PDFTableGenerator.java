package utilities;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Genera una tabla en un pdf
 */
public class PDFTableGenerator {

  private static PDImageXObject pdImage;            //Imagen de una linea negra
  private static final float WIDTHLASTCOL = 50;     // Ancho de la ultima columna
  private static final int MAXLENGTHSTRING = 90;  //Maximo numero de caracteres que puede ocupar una cadena en la tabla.
  private static int rows;
  private static float rowHeight;
  private static float tableWidth;                  // Ancho de la tabla
  private static float tableHeight;                 // Largo de la tabla
  private static float cellMargin;                  //
  private static float marginWidth;                 //Grosor de los bordes
  private static float margin;                      // Espacio en blanco desde el margen de la izquierda/derecha.
  private static float yFirstCol;                   // Coordenada y de la primera fila de la tabla.
  private static PDPageContentStream contentStream; //contentStream del PDF

  public static void init(PDDocument document, float margen, float y, String[][] content, PDPageContentStream cs) throws IOException{
    margin = margen;
    yFirstCol = y;
    rows = content.length;
    rowHeight = 20f;
    tableWidth = document.getPage(0).getMediaBox().getWidth() - (2 * margin);
    tableHeight = rowHeight * rows;
    cellMargin = 5f;
    marginWidth = 0.4f;
    contentStream = cs;
    pdImage = PDImageXObject.createFromFile("src/resources/images/blackLine.png", document);
  }
  
  /**
   * 
   * @param document PDF en donde se insertará la tabla.
   * @param cs ContentStream del PDF.
   * @param content Contenido de la tabla.
   * @param margen Separación entre la tabla y el borde izquierdo de la página.
   * @param y Coordenada y de la primer fila.
   */
  public static void drawTable(PDDocument document, PDPageContentStream cs, String[][] content, float margen, float y) throws IOException {
    init(document, margen, y, content, cs);
    drawRow();
    drawColumns();
    addText(content);
  }

  /**
   * Dibuja las lineas que separan las columnas
   */
  public static void drawColumns() throws IOException{
    contentStream.drawImage(pdImage, margin, yFirstCol - tableHeight, marginWidth, tableHeight + marginWidth);
    contentStream.drawImage(pdImage, tableWidth + margin - WIDTHLASTCOL , yFirstCol - tableHeight, marginWidth, tableHeight + marginWidth);
    contentStream.drawImage(pdImage, tableWidth + margin, yFirstCol - tableHeight, marginWidth, tableHeight + marginWidth);
  }

  /**
   * Dibuja las lineas que separan las filas
   */
  public static void drawRow() throws IOException{
    float nexty = yFirstCol;
    for (int i = 0; i <= rows; i++) {
      contentStream.drawImage(pdImage, margin, nexty, tableWidth + marginWidth, marginWidth);
      nexty -= rowHeight;
    }
  }

  public static String parseText(String s){
    s = GeneralString.removeNewLine(s);
    s = GeneralString.cutString(s,MAXLENGTHSTRING);
    return s;
  }

  /**
   * 
   * @param content Texto que se añade en la tabla
   */
  public static void addText(String[][] content) throws IOException{
    float textx = margin + cellMargin;
    float texty = yFirstCol - 15;
    String text = "";
    for (int i = 0; i < content.length; i++) {
      for (int j = 0; j < content[i].length; j++) {
        text = parseText(content[i][j]);
        contentStream.beginText();
        contentStream.newLineAtOffset(textx, texty);
        contentStream.showText(text);
        contentStream.endText();
        textx += (tableWidth - WIDTHLASTCOL);
      }
      texty -= rowHeight;
      textx = margin + cellMargin;
    }
  }

}