package util.report;

import dto.ReportDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ReportService;
import service_impl.ReportServiceImpl;
import util.enums.Language;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static util.GetProperties.getMessageByLang;

public class CsvReportCreator implements ReportCreator {
    private final ReportService reportService = ReportServiceImpl.getInstance();
    private final Logger logger = LogManager.getLogger(CsvReportCreator.class);

    @Override
    public void createReport(String filepath, Language lang) {
        try {
            File file = new File(filepath);
            OutputStream out = new FileOutputStream(file);
            OutputStreamWriter fileWriter = new OutputStreamWriter(out, StandardCharsets.UTF_8);

            CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.builder().setHeader("#",
                    getMessageByLang("table.report.createdBy", lang), getMessageByLang("table.report.createdAt", lang),
                    getMessageByLang("table.report.quantity", lang), getMessageByLang("table.report.price", lang)).build());

            List<ReportDTO> report = reportService.convertToDTO(reportService.getAll(), lang);
            for (ReportDTO r : report) {
                printer.printRecord(r.getIndex(), r.getCreatedBy(), r.getClosedAt(), r.getItemsQuantity(), r.getTotalPrice());
            }
            printer.close();
        } catch (IOException e) {
            logger.error("Cannot create csv report", e);
        }
    }
}
