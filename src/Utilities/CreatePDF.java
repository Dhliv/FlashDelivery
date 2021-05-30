package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.*;

public class CreatePDF {

  String[][] infoPaq; // INFORMACION DEL PAQUETE QUE DEBE IR ESCRITA EN LA FACTURA.
  String[] infoDest;  //INFORMACION DEL CLIENTE DESTINATARIO
  String[] infoRem;   //INFORMACION DEL CLIENTE REMITENTE
  String[] infoPago;  //INFORMACION DEL PAGO

  /**
   * INFORMACION DEL PAQUETE QUE DEBE IR ESCRITA EN LA FACTURA.
   * @param infoPaq   Datos que se imprimen en la tabla
   * @param infoRem   Datos del remitente
   * @param infoDest  Datos del destinatario
   * @param infoPago  Datos monetarios del pago
   */
  public CreatePDF(String[][] infoPaq, String[] infoRem, String[] infoDest, String[] infoPago) {
    this.infoPaq = infoPaq;
    this.infoRem = infoRem;
    this.infoDest = infoDest;
    this.infoPago = infoPago;
  }

  /**
   * Crea el PDF en donde se encuentra toda la información de infoFact con el
   * formato esperado para la factura.
   * @throws IOException
   */
  public void pdfCreate() throws IOException {
    PDDocument document = new PDDocument();
    PDPage page = new PDPage();
    document.addPage(page);
    FacturaContenido factura = new FacturaContenido(document, page, infoPaq);
    factura.crearFactura();

    document.save(urlFactura());
    document.close();
  }

  /**
   * 
   * @return
   * @throws IOException
   */
  public String urlFactura() throws IOException {
    //TODO AÑADIR IDENTIFICADOR A LA FACTURA
    String curDir = System.getProperty("user.dir") + "/src/resources/facturas/factura.pdf";
    int repeat = 1; //Veces que se ha intentado cambiar el nombre
    int lastPos = curDir.length()-4; //Ultima posicion antes de modificar el string
    //SI EXISTE SE LE AGREGA UN VALOR ENTRE PARENTESIS PARA GUARDAR EL NUEVO ARCHIVO
    while((new File(curDir)).exists()){
      if(repeat > 1) curDir = curDir.substring(0,lastPos) + "(" + Integer.toString(repeat) + ").pdf";
      else curDir = curDir.substring(0,lastPos) + "(1).pdf";
      repeat++;
      
    }

    return curDir;
  }
}
