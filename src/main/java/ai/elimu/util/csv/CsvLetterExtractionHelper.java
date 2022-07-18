package ai.elimu.util.csv;

import ai.elimu.model.content.Letter;
import ai.elimu.web.content.letter.LetterCsvExportController;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toUnmodifiableList;

public class CsvLetterExtractionHelper {

    private CsvLetterExtractionHelper() {
    }

    private static final Logger logger = LogManager.getLogger();

    /**
     * For information on how the CSV files were generated, see {@link LetterCsvExportController#handleRequest}.
     */
    public static List<Letter> getLettersFromCsvBackup(File csvFile) {
        logger.info("getLettersFromCsvBackup");

        Path csvFilePath = Paths.get(csvFile.toURI());
        logger.info("csvFilePath: {}", csvFilePath);

        CSVFormat csvFormat = CSVFormat.Builder.create()
            .setHeader(
                "id",
                "text",
                "diacritic",
                "usage_count"
            )
            .setSkipHeaderRecord(true)
            .build();

        try (var csvParser = new CSVParser(Files.newBufferedReader(csvFilePath), csvFormat)) {
            return csvParser.stream()
                .map(CsvLetterExtractionHelper::toLetter)
                .collect(toUnmodifiableList());
        } catch (IOException ex) {
            logger.error(ex);
        }

        return emptyList();
    }

    @NotNull
    private static Letter toLetter(CSVRecord csvRecord) {
        logger.info("csvRecord: {}", csvRecord);

        Letter letter = new Letter();

        String text = csvRecord.get("text");
        letter.setText(text);

        boolean diacritic = Boolean.parseBoolean(csvRecord.get("diacritic"));
        letter.setDiacritic(diacritic);

        Integer usageCount = NumberUtils.toInt(csvRecord.get("usage_count"));
        letter.setUsageCount(usageCount);

        return letter;
    }
}
