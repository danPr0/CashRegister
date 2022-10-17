package garbage;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.ReportEntity;
import service_impl.ReportServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@WebServlet("/download-pdf-report")
public class DownloadPdfReport extends HttpServlet {
    private final ReportServiceImpl reportServiceImpl = ReportServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(req.getServletContext().getRealPath("") + "/WEB-INF/reports/report.pdf"));
            document.open();

            PdfPTable table = new PdfPTable(4);
            addTableHeader(table);
            addRows(table);

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        resp.setHeader("Content-disposition", "attachment; filename=report.pdf");
        try (InputStream in = req.getServletContext().getResourceAsStream("/WEB-INF/reports/report.pdf");
             OutputStream out = resp.getOutputStream()) {
            out.write(in.readAllBytes());
        }
    }

    private void addTableHeader(PdfPTable table) {
        PdfPCell header = new PdfPCell();
        header.setPhrase(new Phrase("Usesdfdsfdsfdsfsfdfsffsdfr"));
        table.addCell(header);
        header.setPhrase(new Phrase("Date"));
        table.addCell(header);
        header.setPhrase(new Phrase("Items"));
        table.addCell(header);
        header.setPhrase(new Phrase("Price"));
        table.addCell(header);
    }

    private void addRows(PdfPTable table) {
        List<ReportEntity> report = reportServiceImpl.getAll();
        report.forEach((r) -> {
            PdfPCell header = new PdfPCell();
            header.setPhrase(new Phrase(r.getCreatedBy()));
            table.addCell(header);
            header.setPhrase(new Phrase(r.getClosed_at().toString()));
            table.addCell(header);
            header.setPhrase(new Phrase(String.valueOf(r.getItems_quantity())));
            table.addCell(header);
            header.setPhrase(new Phrase(String.valueOf(r.getTotal_price())));
            table.addCell(header);
        });
    }
}
