package util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.ReportElement;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import service.ReportService;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.List;

public class ReportFileCreator {
    private static final ReportService reportService = ReportService.getInstance();

    public static void createCsv(String filename, ServletContext context) throws IOException {
        File file = new File(context.getRealPath("") + "/WEB-INF/reports/" + filename);
        FileWriter fileWriter = new FileWriter(file);

        try (CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.builder().setHeader("User", "Date", "Items", "Price").build())) {
            List<ReportElement> report = reportService.getAll();
            report.forEach((r -> {
                try {
                    printer.printRecord(r.getCreatedBy(), r.getClosed_at(), r.getItems_quantity(), r.getTotal_price());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        }
    }

    public static void createXls(String filename, ServletContext context) throws IOException {
        WritableWorkbook workbook = Workbook.createWorkbook(new File(context.getRealPath("") + "/WEB-INF/reports/" + filename));
        WritableSheet sheet = workbook.createSheet("Report", 0);
        try {
            sheet.addCell(new Label(0, 0, "User"));
            sheet.addCell(new Label(1, 0, "Date"));
            sheet.addCell(new Label(2, 0, "Items"));
            sheet.addCell(new Label(3, 0, "Price"));
        } catch (WriteException e) {
            e.printStackTrace();
        }

        List<ReportElement> report = reportService.getAll();
        report.forEach((r) -> {
            int index = report.indexOf(r);
            try {
                sheet.addCell(new Label(0, index + 1, r.getCreatedBy()));
                sheet.addCell(new Label(1, index + 1, r.getClosed_at().toString()));
                sheet.addCell(new jxl.write.Number(2, index + 1, r.getItems_quantity()));
                sheet.addCell(new Number(3, index + 1, r.getTotal_price()));
            } catch (WriteException e) {
                e.printStackTrace();
            }
        });

        workbook.write();
        try {
            workbook.close();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    public static void createPdf(String filename, ServletContext context) throws FileNotFoundException {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(context.getRealPath("") + "/WEB-INF/reports/" + filename));
            document.open();

            PdfPTable table = new PdfPTable(4);
            addTableHeaderToPdf(table);
            addRowsToPdf(table);

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private static void addTableHeaderToPdf(PdfPTable table) {
        PdfPCell header = new PdfPCell();
        header.setPhrase(new Phrase("User"));
        table.addCell(header);
        header.setPhrase(new Phrase("Date"));
        table.addCell(header);
        header.setPhrase(new Phrase("Items"));
        table.addCell(header);
        header.setPhrase(new Phrase("Price"));
        table.addCell(header);
    }

    private static void addRowsToPdf(PdfPTable table) {
        List<ReportElement> report = reportService.getAll();
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
