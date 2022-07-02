package ai.elimu.util.csv;

import ai.elimu.model.content.Sound;
import ai.elimu.model.v2.enums.content.sound.SoundType;
import ai.elimu.web.content.sound.SoundCsvExportController;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvSoundExtractionHelper {

    private static final Logger logger = LogManager.getLogger();

    /**
     * For information on how the CSV files were generated, see {@link SoundCsvExportController#handleRequest}.
     */
    public static List<Sound> getSoundsFromCsvBackup(File csvFile) {
        logger.info("getSoundsFromCsvBackup");

        List<Sound> sounds = new ArrayList<>();

        Path csvFilePath = Paths.get(csvFile.toURI());
        logger.info("csvFilePath: " + csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(csvFilePath);
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .withHeader(
                            "id",
                            "value_ipa",
                            "value_sampa",
                            "audio_id",
                            "diacritic",
                            "sound_type",
                            "usage_count"
                    )
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                logger.info("csvRecord: " + csvRecord);

                Sound sound = new Sound();

                String valueIpa = csvRecord.get("value_ipa");
                sound.setValueIpa(valueIpa);

                String valueSampa = csvRecord.get("value_sampa");
                sound.setValueSampa(valueSampa);

                boolean diacritic = Boolean.valueOf(csvRecord.get("diacritic"));
                sound.setDiacritic(diacritic);

                if (StringUtils.isNotBlank(csvRecord.get("sound_type"))) {
                    SoundType soundType = SoundType.valueOf(csvRecord.get("sound_type"));
                    sound.setSoundType(soundType);
                }

                Integer usageCount = Integer.valueOf(csvRecord.get("usage_count"));
                sound.setUsageCount(usageCount);

                sounds.add(sound);
            }
        } catch (IOException ex) {
            logger.error(ex);
        }

        return sounds;
    }
}
