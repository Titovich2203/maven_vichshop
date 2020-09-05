package vichshop.modeles;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class WatermarkPageEvent extends PdfPageEventHelper {

    Font FONT = new Font(Font.FontFamily.HELVETICA, 52, Font.BOLD, BaseColor.RED);

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(writer.getDirectContentUnder(),
                Element.ALIGN_CENTER, new Phrase("COMMANDE NON VALIDE", FONT),
                297.5f, 421, writer.getPageNumber() % 2 == 1 ? 45 : -45);
    }
}