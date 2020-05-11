package products;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javafx.collections.ObservableList;

public class PdfGeneration {

    public void pdfGeneration(String fileName, ObservableList<Product> data) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            document.open();

            document.add(new Paragraph("Leltár", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 36)));

            document.add(new Paragraph("\n\n"));

            float[] columnWidths = {2, 4, 4, 3, 3};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(new Phrase("Termékek"));
            cell.setBackgroundColor(GrayColor.GRAYWHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(5);
            table.addCell(cell);

            table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Sorszám");
            table.addCell("Megnevezés");
            table.addCell("Vonalkód");
            table.addCell("Ár");
            table.addCell("Darabszám");
            table.setHeaderRows(1);

            table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            for (int i = 1; i <= data.size(); i++) {
                Product actualProduct = data.get(i - 1);

                table.addCell(""+i);
                table.addCell(actualProduct.getMegnevezes());
                table.addCell(actualProduct.getVonalkod());
                table.addCell(actualProduct.getAr()+ " Ft");
                table.addCell(actualProduct.getDarab());
            }

            document.add(table);

            Chunk signature = new Chunk("\n\nProgramozási technológiák gyakorlat beadandó \nKészítette: Juhász Bálint(DXIPQ0)");
            Paragraph base = new Paragraph(signature);
            document.add(base);

        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }


}
