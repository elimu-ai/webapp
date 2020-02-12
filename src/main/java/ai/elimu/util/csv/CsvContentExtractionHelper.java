package ai.elimu.util.csv;

import ai.elimu.model.content.Allophone;
import ai.elimu.model.enums.content.allophone.SoundType;
import ai.elimu.web.content.allophone.AllophoneCsvExportController;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;

public class CsvContentExtractionHelper {
    
    private static final Logger logger = Logger.getLogger(CsvContentExtractionHelper.class);
    
    /**
     * For information on how the CSV files were generated, see {@link AllophoneCsvExportController#handleRequest}.
     */
    public static List<Allophone> getAllophonesFromCsvBackup(File csvFile) {
        logger.info("getAllophonesFromCsvBackup");
        
        logger.info("csvFile: " + csvFile);
        
        List<Allophone> allophones = new ArrayList<>();
        
        try {
            Scanner scanner = new Scanner(csvFile);
            int rowNumber = 0;
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                logger.info("row: " + row);
                
                rowNumber++;
                
                if (rowNumber == 1) {
                    // Skip the header row
                    continue;
                }
                
                // Expected header format: id,value_ipa,value_sampa,audio_id,diacritic,sound_type,usage_count
                // Expected row format: 1,"dʒ","dZ",null,false,null,-1
                
                // Prevent "dʒ" from being stored as ""dʒ""
                // TODO: find more robust solution (e.g. by using CSV parser library or JSON array parsing)
                row = row.replace("\"", "");
                logger.info("row (after removing '\"'): " + row);
                
                String[] rowValues = row.split(",");
                logger.info("rowValues: " + Arrays.toString(rowValues));
                
                // "id"
                Long id = Long.valueOf(rowValues[0]);
                logger.info("id: " + id);
                
                // "value_ipa"
                String valueIpa = String.valueOf(rowValues[1]);
                logger.info("valueIpa: \"" + valueIpa + "\"");
                
                // "value_sampa"
                String valueSampa = String.valueOf(rowValues[2]);
                logger.info("valueSampa: \"" + valueSampa + "\"");
                
                // "audio_id"
                Long audioId = null;
                if (!"null".equals(rowValues[3])) {
                    audioId = Long.valueOf(rowValues[3]);
                }
                logger.info("audioId: " + audioId);
                
                // "diacritic"
                boolean diacritic = Boolean.valueOf(rowValues[4]);
                logger.info("diacritic: " + diacritic);
                
                // "sound_type"
                SoundType soundType = null;
                if (!"null".equals(rowValues[5])) {
                    soundType = SoundType.valueOf(rowValues[5]);
                }
                logger.info("soundType: " + soundType);
                
                // "usage_count"
                int usageCount = Integer.valueOf(rowValues[6]);
                logger.info("usageCount: " + usageCount);
                
                Allophone allophone = new Allophone();
                allophone.setId(id);
                allophone.setValueIpa(valueIpa);
                allophone.setValueSampa(valueSampa);
                // TODO: allophone.setAudio();
                allophone.setDiacritic(diacritic);
                allophone.setSoundType(soundType);
                allophone.setUsageCount(usageCount);
                
                allophones.add(allophone);
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            logger.error(null, ex);
        }
        
        return allophones;
    }
}
