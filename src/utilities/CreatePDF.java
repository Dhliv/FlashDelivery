package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.*;

public class CreatePDF {

  private String[][] infoPaq; // INFORMACION DEL PAQUETE QUE DEBE IR ESCRITA EN LA FACTURA.
  private String[] infoDest;  //INFORMACION DEL CLIENTE DESTINATARIO
  private String[] infoRem;   //INFORMACION DEL CLIENTE REMITENTE
  private String[] infoPago;  //INFORMACION DEL PAGO

  /**
   * Solicita la iNFORMACION DEL PAQUETE QUE DEBE IR ESCRITA EN LA FACTURA.
   * 
   * @param infoPaq   Descripción y precio de los paquetes
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
   * 
   * @throws IOException
   */
  public void pdfCreate(String idPDF) throws IOException {
    File file = new File("src/resources/templates/templatePDFFactura.pdf");
    PDDocument document = Loader.loadPDF(file);   
    FacturaContenido factura = new FacturaContenido(document, infoPaq, infoRem, infoDest, infoPago, idPDF);
    factura.crearFactura();

    document.save(urlFactura(idPDF));
    System.out.println("pdf: " + idPDF + "creado");
    document.close();
  }

  public void pdfCreate() throws IOException{pdfCreate("");}

  /**
   * Genera una url valida en donde se podrá guardar un archivo pdf.
   * 
   * @param name Nombre del archivo pdf
   * @return Direccion en donde se guarda el archivo pdf
   * @throws IOException
   */
  public String urlFactura(String name) throws IOException {
    String curDir = System.getProperty("user.dir") + "/src/resources/facturas/factura"+ name +".pdf";
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
