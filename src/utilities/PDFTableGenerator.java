package utilities;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


/**
 * Genera una tabla en un pdf
 */
public class PDFTableGenerator {

  private static PDImageXObject pdImage; // Imagen de una linea que delimita los limites de la tabla.
  private static final float WIDTHLASTCOL = 85; // Ancho de la ultima columna
  private static int rows;
  private static float rowHeight; // Ancho de la casilla
  private static float tableWidth; // Ancho de la tabla
  private static float tableHeight; // Largo de la tabla
  private static float cellMargin; //
  private static float marginWidth; // Grosor de los bordes
  private static float margin; // Espacio en blanco desde el margen de la izquierda/derecha.
  private static float yFirstCol; // Coordenada y de la primera fila de la tabla.
  private static PDPageContentStream contentStream; // contentStream del PDF
  private static PDDocument document;
  private static PDType0Font regularFont;
  private static PDType0Font regularBoldFont;

  public static void init(PDDocument documento, float margen, float y, float rowH, String[] content,
      PDPageContentStream cs) throws IOException {
    document = documento;
    margin = margen;
    yFirstCol = y;
    rows = content.length;
    rowHeight = rowH;
    tableWidth = document.getPage(0).getMediaBox().getWidth() - (2 * margin);
    
    //SE IMPORTAN LAS FUENTES TIPOGRAFICAS.
    regularFont = PDType0Font.load(document, new File("C:/Users/pract/Downloads/Lato/Lato-Regular.ttf")); 
    regularBoldFont =  PDType0Font.load(document, new File("C:/Users/pract/Downloads/Lato/Lato-Bold.ttf"));
    
    cellMargin = 5f;
    marginWidth = 0.1f;
    contentStream = cs;
    pdImage = PDImageXObject.createFromFile("src/resources/images/darkGray.png", document);
  }

  /**
   * Dibuja la tabla en el PDF
   * 
   * @param document  PDF en donde se insertará la tabla.
   * @param cs        ContentStream del PDF.
   * @param content   Contenido de la tabla.
   * @param margen    Separación entre la tabla y el borde izquierdo de la página.
   * @param yFirst    Coordenada y de la primer fila.
   * @param rowHeight Altura de las casillas
   */
  public static void drawTable(PDDocument document, PDPageContentStream cs, String[] texto, String monto, float margen,
      float yFirst, float rowHeight) throws IOException {
    init(document, margen, yFirst, rowHeight, texto, cs);

    rows = texto.length;
    tableHeight = rowHeight * rows;
    
    
    drawDecoration(0, rows);
    addText(texto, monto);
    drawColumns();
    drawRow(0);
    drawRow(rows);

  }

  /**
   * Dibuja el header de la tabla de la factura
   * @throws IOException
   */
  public static void drawHeader() throws IOException {

    float headerPosY = yFirstCol + 4; // Posición del header
    drawCellBackgroundColor("orange", 0);
    drawText(margin + cellMargin, headerPosY, "Descripción", regularBoldFont, 12);
    drawText(margin + cellMargin + tableWidth - WIDTHLASTCOL, headerPosY, "Precio(COP)", regularBoldFont, 12);
  }

  /**
   * Crea un fondo de color en las filas que intercala entre gris y blanco.
   * 
   * @param fRow     Primera fila a ser decorada.
   * @param lastRows Ultima fila a ser decorada.
   * @throws IOException
   */
  public static void drawDecoration(int fRow, int lastRows) throws IOException {
    for (int i = fRow; i < lastRows; i++) {
      drawCellBackgroundColor("gray", i+1);
    }
  }

  /**
   * Pinta de color "color" alguna la fila con indice "row"
   * 
   * @param color String de un color almacenado en la carpeta images.
   * @param row   Fila que se desea colorear.
   * @throws IOException
   */
  public static void drawCellBackgroundColor(String color, int row) throws IOException {
    PDImageXObject image = PDImageXObject.createFromFile("src/resources/images/" + color + ".png", document);
    float firstCol = yFirstCol - rowHeight * row;
    contentStream.drawImage(image, margin, firstCol, tableWidth + marginWidth, rowHeight);
  }

  /**
   * Dibuja las lineas que separan las columnas
   */
  public static void drawColumns() throws IOException {
    contentStream.drawImage(pdImage, margin, yFirstCol - tableHeight, marginWidth, tableHeight + marginWidth);
    contentStream.drawImage(pdImage, tableWidth + margin - WIDTHLASTCOL, yFirstCol - tableHeight, marginWidth,
        tableHeight + marginWidth);
    contentStream.drawImage(pdImage, tableWidth + margin, yFirstCol - tableHeight, marginWidth,
        tableHeight + marginWidth);
  }

  /**
   * Dibuja una linea que separan dos filas
   * 
   * @param row
   * @throws IOException
   */
  public static void drawRow(int row) throws IOException {
    contentStream.drawImage(pdImage, margin, (yFirstCol - row * rowHeight), tableWidth + marginWidth, marginWidth);
  }

  /**
   * Dibuja las lineas seleccionadas
   * 
   * @param row Array con las lineas que se dibujan
   * @throws IOException
   */
  public static void drawRow(int[] row) throws IOException {
    float nexty;
    for (int i = 0; i < row.length; i++) {
      nexty = yFirstCol - row[i] * rowHeight;
      contentStream.drawImage(pdImage, margin, nexty, tableWidth + marginWidth, marginWidth);
    }
  }
  

  /**
   * Dibuja el texto text en las posiciones X=posX y Y=posY
   * 
   * @param posX posición X en formato float
   * @param posY posición Y en formato float
   * @param text texto en string
   * @param fuente Tipo de fuente tipografica
   * @param fuenteSize Tamaño de la fuente
   * @throws IOException
   */
  public static void drawText(float posX, float posY, String text, PDFont fuente, int fuenteSize) throws IOException {
    contentStream.beginText();
    contentStream.setFont(fuente, fuenteSize); // Fuente de la letra
    contentStream.newLineAtOffset(posX, posY);
    contentStream.showText(text);
    contentStream.endText();
  }

  /**
   * Añadir texto a la tabla del PDF
   * 
   * @param text Texto a escribir en la primer columna de la tabla.
   * @param monto Valor del paquete
   */
  public static void addText(String[] text, String monto) throws IOException {
    
    float textx = margin + cellMargin; //Posición en X del texto
    float texty = yFirstCol - 15; //Posición en Y del texto

        //Primer columna
        for (int k = 0; k < text.length; k++) {
          drawText(textx, texty, text[k], regularFont, 10);
          texty -= rowHeight;
        }

        texty = (yFirstCol + texty) / 2; // Centrar verticalmente la segunda columna
        textx += (tableWidth - WIDTHLASTCOL); // Segunda columna horizontalmente
        drawText(textx,texty, monto, regularFont, 10);
  }

}