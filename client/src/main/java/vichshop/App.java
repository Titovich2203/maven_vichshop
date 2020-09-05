package vichshop;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.stage.Stage;
import vichshop.utils.Fabrique;
import vichshop.utils.LoadView;
import vichshop.utils.Utils;

import java.io.FileOutputStream;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App extends Application
{

    @Override
    public void start(Stage primaryStage) {
        try {
            //LoadView.showView("CONNEXION","FormLogin.fxml",1);
            LoadView.showView("CONNEXION","FormDashboard.fxml",1);

            //Date date = new Date(System.currentTimeMillis());
            //System.out.println(Utils.convertDateToString(date));

            /*
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("ChronoUnits.pdf"));
            doc.open();

            Font bold = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph paragraph = new Paragraph("Duration of ChronoUnits");

            PdfPTable table = new PdfPTable(2);
            Stream.of("Chrono Unit", "Duration").forEach(table::addCell);

            Arrays.stream(ChronoUnit.values())
                    .forEach(val -> {
                        table.addCell(val.toString());
                        table.addCell(val.getDuration().toString());
                    });

            paragraph.add(table);
            doc.add(paragraph);
            doc.close();
            System.out.println("OK");

             */
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
