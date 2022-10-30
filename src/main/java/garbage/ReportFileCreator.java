//package util.report;
//
//import com.itextpdf.text.*;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.pdf.*;
//import dto.ReportDTO;
//import jxl.Workbook;
//import jxl.write.Number;
//import jxl.write.*;
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVPrinter;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import service.ReportService;
//import service_impl.ReportServiceImpl;
//import util.enums.Language;
//
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//import static util.GetProperties.getMessageByLang;
//
///**
// * Utility for creating .csv, .pdf and .xls files with report
// */
//public class ReportFileCreator {
//    private static final ReportService REPORT_SERVICE = ReportServiceImpl.getInstance();
//    private static final Logger logger = LogManager.getLogger(ReportFileCreator.class);
//
//    public static void createCsv(String filepath, Language lang) {
//        try {
//            File file = new File(filepath);
//            OutputStream out = new FileOutputStream(file);
//            OutputStreamWriter fileWriter = new OutputStreamWriter(out, StandardCharsets.UTF_8);
//
//            CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.builder().setHeader("#",
//                    getMessageByLang("table.report.createdBy", lang), getMessageByLang("table.report.createdAt", lang),
//                    getMessageByLang("table.report.quantity", lang), getMessageByLang("table.report.price", lang)).build());
//
//            List<ReportDTO> report = REPORT_SERVICE.convertToDTO(REPORT_SERVICE.getAll());
//            for (ReportDTO r : report) {
//                printer.printRecord(r.getIndex(), r.getCreatedBy(), r.getClosedAt(), r.getItemsQuantity(), r.getTotalPrice());
//            }
//            printer.close();
//        } catch (IOException e) {
//            logger.error("Cannot create csv report", e);
//        }
//    }
//
//    public static void createXls(String filepath, Language lang) {
//        try {
//            WritableWorkbook workbook = Workbook.createWorkbook(new File(filepath));
//            WritableSheet sheet = workbook.createSheet("Report", 0);
//
//            sheet.addCell(new Label(0, 0, "#"));
//            sheet.addCell(new Label(1, 0, getMessageByLang("table.report.createdBy", lang)));
//            sheet.addCell(new Label(2, 0, getMessageByLang("table.report.createdAt", lang)));
//            sheet.addCell(new Label(3, 0, getMessageByLang("table.report.quantity", lang)));
//            sheet.addCell(new Label(4, 0, getMessageByLang("table.report.price", lang)));
//
//            List<ReportDTO> report = REPORT_SERVICE.convertToDTO(REPORT_SERVICE.getAll());
//            for (ReportDTO r : report) {
//                int index = report.indexOf(r);
//                sheet.addCell(new Number(0, index + 1, r.getIndex()));
//                sheet.addCell(new Label(1, index + 1, r.getCreatedBy()));
//                sheet.addCell(new Label(2, index + 1, r.getClosedAt()));
//                sheet.addCell(new Number(3, index + 1, r.getItemsQuantity()));
//                sheet.addCell(new Label(4, index + 1, r.getTotalPrice()));
//            }
//
//            workbook.write();
//            workbook.close();
//        } catch (WriteException | IOException e) {
//            logger.error("Cannot create xls report", e.getCause());
//        }
//    }
//
//    public static void createPdf(String filepath, Language lang) {
//        Document document = new Document();
//        try {
//            PdfWriter.getInstance(document, new FileOutputStream(filepath));
//            document.open();
//
//            float[] columnWidths = {1, 8, 8, 4, 4};
//            PdfPTable table = new PdfPTable(columnWidths);
//            table.setWidthPercentage(100);
//            table.setSpacingBefore(0f);
//            table.setSpacingAfter(0f);
//            addTableHeaderToPdf(table, lang);
//            addRowsToPdf(table);
//
//            document.add(table);
//            document.close();
//        } catch (DocumentException | IOException e) {
//            logger.error("Cannot create pdf report", e.getCause());
//        }
//    }
//
//    private static void addTableHeaderToPdf(PdfPTable table, Language lang) throws IOException, DocumentException {
//        PdfPCell header = new PdfPCell();
//        header.setHorizontalAlignment(Element.ALIGN_CENTER);
//        String FONT = "/arial.ttf";
//
//        BaseFont bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//        com.itextpdf.text.Font font = new com.itextpdf.text.Font(bf, 16, Font.NORMAL);
//
//        header.setPhrase(new Phrase("#"));
//        table.addCell(header);
//        header.setPhrase(new Phrase(getMessageByLang("table.report.createdBy", lang), font));
//        table.addCell(header);
//        header.setPhrase(new Phrase(getMessageByLang("table.report.createdAt", lang), font));
//        table.addCell(header);
//        header.setPhrase(new Phrase(getMessageByLang("table.report.quantity", lang), font));
//        table.addCell(header);
//        header.setPhrase(new Phrase(getMessageByLang("table.report.price", lang), font));
//        table.addCell(header);
//    }
//
//    private static void addRowsToPdf(PdfPTable table) {
//        List<ReportDTO> report = REPORT_SERVICE.convertToDTO(REPORT_SERVICE.getAll());
//        report.forEach((r) -> {
//            PdfPCell cell = new PdfPCell();
//            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//            cell.setPhrase(new Phrase(String.valueOf(r.getIndex())));
//            table.addCell(cell);
//            cell.setPhrase(new Phrase(r.getCreatedBy()));
//            table.addCell(cell);
//            cell.setPhrase(new Phrase(r.getClosedAt()));
//            table.addCell(cell);
//            cell.setPhrase(new Phrase(String.valueOf(r.getItemsQuantity())));
//            table.addCell(cell);
//            cell.setPhrase(new Phrase(String.valueOf(r.getTotalPrice())));
//            table.addCell(cell);
//        });
//    }
//}
