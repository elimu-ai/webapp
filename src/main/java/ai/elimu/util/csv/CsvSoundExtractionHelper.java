package ai.elimu.util.csv;

import ai.elimu.model.content.Sound;
import ai.elimu.model.v2.enums.content.sound.SoundType;
import ai.elimu.web.content.sound.SoundCsvExportController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toUnmodifiableList;

@Slf4j
public class CsvSoundExtractionHelper {

    private CsvSoundExtractionHelper() {
    }

    /**
     * For information on how the CSV files were generated, see {@link SoundCsvExportController#handleRequest}.
     */
    public static List<Sound> getSoundsFromCsvBackup(File csvFile) {
        log.info("getSoundsFromCsvBackup");

        Path csvFilePath = Paths.get(csvFile.toURI());
        log.info("csvFilePath: {}", csvFilePath);

        CSVFormat csvFormat = CSVFormat.Builder.create()
            .setHeader(
                "id",
                "value_ipa",
                "value_sampa",
                "audio_id",
                "diacritic",
                "sound_type",
                "usage_count"
            )
            .setSkipHeaderRecord(true)
            .build();

        try (var csvParser = new CSVParser(Files.newBufferedReader(csvFilePath), csvFormat)) {
            return csvParser.stream()
                .map(CsvSoundExtractionHelper::toSound)
                .collect(toUnmodifiableList());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        return emptyList();
    }

    @NotNull
    private static Sound toSound(CSVRecord csvRecord) {
        log.info("csvRecord: {}", csvRecord);

        Sound sound = new Sound();

        String valueIpa = csvRecord.get("value_ipa");
        sound.setValueIpa(valueIpa);

        String valueSampa = csvRecord.get("value_sampa");
        sound.setValueSampa(valueSampa);

        boolean diacritic = Boolean.parseBoolean(csvRecord.get("diacritic"));
        sound.setDiacritic(diacritic);

        SoundType soundType = extractSoundType(csvRecord.get("sound_type"));
        sound.setSoundType(soundType);

        Integer usageCount = NumberUtils.toInt(csvRecord.get("usage_count"));
        sound.setUsageCount(usageCount);

        return sound;
    }

    // TODO: 05.07.2022 This method can be replaced by a {@link org.apache.commons.lang3.EnumUtils::getEnum}
    private static SoundType extractSoundType(String soundTypeCsvValue) {
        if (StringUtils.isBlank(soundTypeCsvValue)) {
            return null;
        }

        try {
            return SoundType.valueOf(soundTypeCsvValue);
        } catch (IllegalArgumentException e) {
            log.error(
                "Tried to extract incorrect value: {} of {} enum",
                soundTypeCsvValue,
                SoundType.class.getSimpleName()
            );
            return null;
        }
    }
}
