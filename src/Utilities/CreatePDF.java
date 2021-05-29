package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.*;

public class CreatePDF {

  String[][] infoPaq; // INFORMACION DEL PAQUETE QUE DEBE IR ESCRITA EN LA FACTURA.

  /**
   * INFORMACION DEL PAQUETE QUE DEBE IR ESCRITA EN LA FACTURA.
   * @param infoPaq
   */
  public CreatePDF(String[][] infoPaq) {
    this.infoPaq = infoPaq;
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
    int repeat = 0; //Veces que se ha intentado cambiar el nombre
    
    //SI EXISTE SE LE AGREGA UN VALOR ENTRE PARENTESIS PARA GUARDAR EL NUEVO ARCHIVO
    while((new File(curDir)).exists()){
      if(repeat > 0) curDir = curDir.substring(0,curDir.length()-6) + Integer.toString(repeat) + ").pdf";
      else curDir = curDir.substring(0,curDir.length()-4) + "(1).pdf";
      repeat++;
      
    }

    return curDir;
  }
}
