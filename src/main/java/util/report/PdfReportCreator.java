package util.report;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dto.ReportDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ReportService;
import service_impl.ReportServiceImpl;
import util.enums.Language;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static util.GetProperties.getMessageByLang;

public class PdfReportCreator implements ReportCreator {
    private final ReportService reportService = ReportServiceImpl.getInstance();
    private final Logger logger = LogManager.getLogger(PdfReportCreator.class);

    @Override
    public void createReport(String filepath, Language lang) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filepath));
            document.open();

            float[] columnWidths = {1, 8, 8, 4, 4};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);
            table.setSpacingBefore(0f);
            table.setSpacingAfter(0f);
            addTableHeaderToPdf(table, lang);
            addRowsToPdf(table, lang);

            document.add(table);
            document.close();
        } catch (DocumentException | IOException e) {
            logger.error("Cannot create pdf report", e.getCause());
        }
    }

    private void addTableHeaderToPdf(PdfPTable table, Language lang) throws IOException, DocumentException {
        PdfPCell header = new PdfPCell();
        header.setHorizontalAlignment(Element.ALIGN_CENTER);

        String FONT = "/arial.ttf";

        BaseFont bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new com.itextpdf.text.Font(bf, 16, Font.NORMAL);

        header.setPhrase(new Phrase("#", font));
        table.addCell(header);
        header.setPhrase(new Phrase(getMessageByLang("table.report.createdBy", lang), font));
        table.addCell(header);
        header.setPhrase(new Phrase(getMessageByLang("table.report.createdAt", lang), font));
        table.addCell(header);
        header.setPhrase(new Phrase(getMessageByLang("table.report.quantity", lang), font));
//        header.addElement(new Chunk("\u20B4", font));
        table.addCell(header);
        header.setPhrase(new Phrase(getMessageByLang("table.report.price", lang), font));
        table.addCell(header);
    }

    private void addRowsToPdf(PdfPTable table, Language lang) {
        List<ReportDTO> report = reportService.convertToDTO(reportService.getAll(), lang);
        report.forEach((r) -> {
            PdfPCell cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPhrase(new Phrase(String.valueOf(r.getIndex())));
            table.addCell(cell);
            cell.setPhrase(new Phrase(r.getCreatedBy()));
            table.addCell(cell);
            cell.setPhrase(new Phrase(r.getClosedAt()));
            table.addCell(cell);
            cell.setPhrase(new Phrase(String.valueOf(r.getItemsQuantity())));
            table.addCell(cell);
            cell.setPhrase(new Phrase(String.valueOf(r.getTotalPrice())));
            table.addCell(cell);
        });
    }
}
