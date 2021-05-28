package utilities;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDFontFactory;
import org.apache.pdfbox.pdmodel.font.PDType1CFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
public class CreatePDF {

  public void pdfCreate() throws IOException{
    PDDocument document = new PDDocument();
    PDPage my_page = new PDPage();
    document.addPage(my_page);
    PDPage page1 = document.getPage(0);
    PDPageContentStream contentStream = new PDPageContentStream(document, page1);
    
    contentStream.beginText();
      contentStream.newLineAtOffset(25, 700);
      contentStream.setFont( PDType1Font.TIMES_ROMAN, 12 );
      contentStream.showText("text");
      contentStream.endText();
    contentStream.close();


    document.save("D:/my_doc.pdf");
    document.close();
  }
}
