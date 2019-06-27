/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barras;

import Vista.Login;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.persistence.Persistence;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Carlos
 */
public class Barras {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws com.itextpdf.text.DocumentException
     */
    public static void main(String[] args) throws FileNotFoundException, DocumentException {

        Dimension sd = Toolkit.getDefaultToolkit().getScreenSize();

        try {

            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    Login v = new Login(Persistence.createEntityManagerFactory("barrasPU"));
                    v.setVisible(true);
                    break;
                } else {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {

        }

        /*try {
      
      Document doc  = new Document();
      PdfWriter pdf = PdfWriter.getInstance(doc, new FileOutputStream("codigos.pdf"));
      doc.open();
      
      Barcode39 code = new Barcode39();
      code.setCode("9827");
      Image img = code.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
      
      doc.add(img);
      
      doc.add(new Paragraph(" "));
      
      Barcode128 code128 = new Barcode128();
      code128.setCode("123443");
      Image img128 = code128.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
      
      doc.add(img128);
      
      doc.close();
      
      } catch (DocumentException | FileNotFoundException e) {
      }*/
    }

}
