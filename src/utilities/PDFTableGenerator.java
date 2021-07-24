package utilities;

import java.io.IOException;
import java.net.ConnectException;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Genera una tabla en un pdf
 */
public class PDFTableGenerator {

  private static PDImageXObject pdImage;            //Imagen de una linea que delimita los limites de la tabla.
  private static final float WIDTHLASTCOL = 85;     // Ancho de la ultima columna
  private static int rows;
  private static float rowHeight;                   //Ancho de la casilla
  private static float tableWidth;                  // Ancho de la tabla
  private static float tableHeight;                 // Largo de la tabla
  private static float cellMargin;                  //
  private static float marginWidth;                 //Grosor de los bordes
  private static float margin;                      // Espacio en blanco desde el margen de la izquierda/derecha.
  private static float yFirstCol;                   // Coordenada y de la primera fila de la tabla.
  private static PDPageContentStream contentStream; //contentStream del PDF
  private static PDDocument document;

  public static void init(PDDocument documento, float margen, float y, float rowH, String[][] content, PDPageContentStream cs) throws IOException{
    document = documento;
    margin = margen;
    yFirstCol = y;
    rows = content.length;
    rowHeight = rowH;
    tableWidth = document.getPage(0).getMediaBox().getWidth() - (2 * margin);
    tableHeight = rowHeight * rows;
    cellMargin = 5f;
    marginWidth = 0.1f;
    contentStream = cs;
    pdImage = PDImageXObject.createFromFile("src/resources/images/darkGray.png", document);
  }
  
  /**
   * 
   * @param document PDF en donde se insertará la tabla.
   * @param cs ContentStream del PDF.
   * @param content Contenido de la tabla.
   * @param margen Separación entre la tabla y el borde izquierdo de la página.
   * @param yFirst Coordenada y de la primer fila.
   * @param rowHeight Altura de las casillas
   */
  public static void drawTable(PDDocument document, PDPageContentStream cs, String[][] content, float margen, float yFirst, float rowHeight) throws IOException {
    init(document, margen, yFirst, rowHeight, content, cs);
    
    
    drawCellBackgroundColor("orange", 1);
    drawDecoration(1, content.length);
    addText(content);
    drawColumns();
    drawRow(0);
    drawRow(content.length);
    
  }

  public static void drawDecoration(int fRow, int lastRows) throws IOException{
    for (int i = fRow; i < lastRows; i++) {
      if(i%2 == 0) drawCellBackgroundColor("gray", i+1);
    }
  }

  public static void drawCellBackgroundColor(String color, int row) throws IOException{
    PDImageXObject image = PDImageXObject.createFromFile("src/resources/images/"+ color +".png", document);
    float firstCol = yFirstCol-rowHeight*row;
    contentStream.drawImage(image, margin, firstCol, tableWidth+marginWidth, rowHeight);
  }

  /**
   * Dibuja las lineas que separan las columnas
   */
  public static void drawColumns() throws IOException{
    contentStream.drawImage(pdImage, margin, yFirstCol - tableHeight, marginWidth, tableHeight + marginWidth);
    contentStream.drawImage(pdImage, tableWidth + margin - WIDTHLASTCOL , yFirstCol - tableHeight, marginWidth, tableHeight + marginWidth);
    contentStream.drawImage(pdImage, tableWidth + margin, yFirstCol - tableHeight, marginWidth, tableHeight + marginWidth);
  }

  public static void drawRow(int row) throws IOException{
    contentStream.drawImage(pdImage, margin, (yFirstCol - row*rowHeight), tableWidth + marginWidth, marginWidth);
  }

  /**
   * Dibuja las lineas seleccionadas
   * @param row Array con las lineas que se dibujan
   * @throws IOException
   */
  public static void drawRow(int[] row) throws IOException{
    float nexty;
    for (int i = 0; i < row.length; i++) {
      nexty = yFirstCol - row[i]*rowHeight;
      contentStream.drawImage(pdImage, margin, nexty, tableWidth + marginWidth, marginWidth);
    }
  }
  /**
   * Dibuja todas lineas que separan las filas.
   * @throws IOException
   */
  public static void drawRow() throws IOException{
    float nexty = yFirstCol;
    for (int i = 0; i <= rows; i++) {
      contentStream.drawImage(pdImage, margin, nexty, tableWidth + marginWidth, marginWidth);
      nexty -= rowHeight;
    }
  }

  public static String parseText(String s){
    final int MAXLENGTHSTRING = 50;  //Maximo numero de caracteres que puede ocupar una cadena en la tabla.
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
          if(i == 0)  contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
          else        contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);
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