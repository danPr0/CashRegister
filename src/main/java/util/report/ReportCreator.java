package util.report;

import util.enums.Language;

public interface ReportCreator {
    void createReport(String filepath, Language lang);
}
