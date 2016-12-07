package org.literacyapp.rest.v1;

import java.util.ArrayList;
import java.util.List;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.Device;
import org.literacyapp.model.content.Number;
import org.literacyapp.model.content.Word;
import org.literacyapp.model.admin.ApplicationVersion;
import org.literacyapp.model.content.Allophone;
import org.literacyapp.model.content.Letter;
import org.literacyapp.model.content.multimedia.Audio;
import org.literacyapp.model.content.multimedia.Image;
import org.literacyapp.model.content.multimedia.Video;
import org.literacyapp.model.gson.DeviceGson;
import org.literacyapp.model.gson.content.NumberGson;
import org.literacyapp.model.gson.content.WordGson;
import org.literacyapp.model.gson.admin.ApplicationGson;
import org.literacyapp.model.gson.admin.ApplicationVersionGson;
import org.literacyapp.model.gson.content.AllophoneGson;
import org.literacyapp.model.gson.content.LetterGson;
import org.literacyapp.model.gson.content.multimedia.AudioGson;
import org.literacyapp.model.gson.content.multimedia.ImageGson;
import org.literacyapp.model.gson.content.multimedia.VideoGson;

public class JavaToGsonConverter {
    
    public static AudioGson getAudioGson(Audio audio) {
        if (audio == null) {
            return null;
        } else {
            AudioGson audioGson = new AudioGson();
            
            audioGson.setId(audio.getId());
            audioGson.setLocale(audio.getLocale());
            audioGson.setTimeLastUpdate(audio.getTimeLastUpdate());
            audioGson.setRevisionNumber(audio.getRevisionNumber());
            audioGson.setContentStatus(audio.getContentStatus());
            
            audioGson.setDownloadUrl("/audio/" + audio.getId() + "." + audio.getAudioFormat().toString().toLowerCase());
            audioGson.setDownloadSize(audio.getBytes().length / 1024);
            audioGson.setContentType(audio.getContentType());
            audioGson.setLiteracySkills(audio.getLiteracySkills());
            audioGson.setNumeracySkills(audio.getNumeracySkills());
            
            List<LetterGson> letters = new ArrayList<>();
            for (Letter letter : audio.getLetters()) {
                LetterGson letterGson = getLetterGson(letter);
                letters.add(letterGson);
            }
            audioGson.setLetters(letters);
            
            List<NumberGson> numbers = new ArrayList<>();
            for (Number number : audio.getNumbers()) {
                NumberGson numberGson = getNumberGson(number);
                numbers.add(numberGson);
            }
            audioGson.setNumbers(numbers);
            
            List<WordGson> words = new ArrayList<>();
            for (Word word : audio.getWords()) {
                WordGson wordGson = getWordGson(word);
                words.add(wordGson);
            }
            audioGson.setWords(words);
            
            audioGson.setTranscription(audio.getTranscription());
            audioGson.setAudioType(audio.getAudioFormat());
            
            return audioGson;
        }
    }
    
    public static AllophoneGson getAllophoneGson(Allophone allophone) {
        if (allophone == null) {
            return null;
        } else {
            AllophoneGson allophoneGson = new AllophoneGson();
            
            allophoneGson.setId(allophone.getId());
            allophoneGson.setLocale(allophone.getLocale());
            allophoneGson.setTimeLastUpdate(allophone.getTimeLastUpdate());
            allophoneGson.setRevisionNumber(allophone.getRevisionNumber());
            allophoneGson.setContentStatus(allophone.getContentStatus());
            
            allophoneGson.setValueIpa(allophone.getValueIpa());
            allophoneGson.setValueSampa(allophone.getValueSampa());
            
            return allophoneGson;
        }
    }
    
    public static ApplicationGson getApplicationGson(Application application) {
        if (application == null) {
            return null;
        } else {
            ApplicationGson applicationJson = new ApplicationGson();
            applicationJson.setId(application.getId());
            applicationJson.setLocale(application.getLocale());
            applicationJson.setPackageName(application.getPackageName());
            applicationJson.setLiteracySkills(application.getLiteracySkills());
            applicationJson.setNumeracySkills(application.getNumeracySkills());
            applicationJson.setApplicationStatus(application.getApplicationStatus());
            return applicationJson;
        }
    }
    
    public static ApplicationVersionGson getApplicationVersionGson(ApplicationVersion applicationVersion) {
        if (applicationVersion == null) {
            return null;
        } else {
            ApplicationVersionGson applicationVersionGson = new ApplicationVersionGson();
            applicationVersionGson.setId(applicationVersion.getId());
            applicationVersionGson.setApplication(getApplicationGson(applicationVersion.getApplication()));
            applicationVersionGson.setFileSizeInKb(applicationVersion.getBytes().length / 1024);
            applicationVersionGson.setFileUrl("/apk/" + applicationVersion.getApplication().getPackageName() + "-" + applicationVersion.getVersionCode() + ".apk");
            applicationVersionGson.setContentType(applicationVersion.getContentType());
            applicationVersionGson.setVersionCode(applicationVersion.getVersionCode());
            applicationVersionGson.setStartCommand(applicationVersion.getStartCommand());
            applicationVersionGson.setTimeUploaded(applicationVersion.getTimeUploaded());
            return applicationVersionGson;
        }
    }
    
    public static DeviceGson getDeviceGson(Device device) {
        if (device == null) {
            return null;
        } else {
            DeviceGson deviceJson = new DeviceGson();
            
            deviceJson.setId(device.getId());
            deviceJson.setDeviceId(device.getDeviceId());
//            deviceJson.setDeviceModel(device.getDeviceModel());
//            deviceJson.setTimeRegistered(device.getTimeRegistered());
//            deviceJson.setOsVersion(device.getOsVersion());
//            deviceJson.setLocale(device.getLocale());
//            
//            Set<DeviceGson> devicesNearby = new HashSet<DeviceGson>();
//            for (Device deviceNearby : device.getDevicesNearby()) {
//                DeviceGson deviceJsonNearby = getDeviceGson(deviceNearby);
//                if (deviceJsonNearby != null) {
//                    devicesNearby.add(deviceJsonNearby);
//                }
//            }
//            if (!devicesNearby.isEmpty()) {
//                deviceJson.setDevicesNearby(devicesNearby);
//            }
            
            return deviceJson;
        }
    }
    
    public static ImageGson getImageGson(Image image) {
        if (image == null) {
            return null;
        } else {
            ImageGson imageGson = new ImageGson();
            
            imageGson.setId(image.getId());
            imageGson.setLocale(image.getLocale());
            imageGson.setTimeLastUpdate(image.getTimeLastUpdate());
            imageGson.setRevisionNumber(image.getRevisionNumber());
            imageGson.setContentStatus(image.getContentStatus());
            
            imageGson.setDownloadUrl("/image/" + image.getId() + "." + image.getImageFormat().toString().toLowerCase());
            imageGson.setDownloadSize(image.getBytes().length / 1024);
            imageGson.setContentType(image.getContentType());
            imageGson.setLiteracySkills(image.getLiteracySkills());
            imageGson.setNumeracySkills(image.getNumeracySkills());
            
            List<LetterGson> letters = new ArrayList<>();
            for (Letter letter : image.getLetters()) {
                LetterGson letterGson = getLetterGson(letter);
                letters.add(letterGson);
            }
            imageGson.setLetters(letters);
            
            List<NumberGson> numbers = new ArrayList<>();
            for (Number number : image.getNumbers()) {
                NumberGson numberGson = getNumberGson(number);
                numbers.add(numberGson);
            }
            imageGson.setNumbers(numbers);
            
            List<WordGson> words = new ArrayList<>();
            for (Word word : image.getWords()) {
                WordGson wordGson = getWordGson(word);
                words.add(wordGson);
            }
            imageGson.setWords(words);
            
            imageGson.setTitle(image.getTitle());
            imageGson.setImageFormat(image.getImageFormat());
            
            return imageGson;
        }
    }

    public static LetterGson getLetterGson(Letter letter) {
        if (letter == null) {
            return null;
        } else {
            LetterGson letterJson = new LetterGson();
            
            letterJson.setId(letter.getId());
            letterJson.setLocale(letter.getLocale());
            letterJson.setTimeLastUpdate(letter.getTimeLastUpdate());
            letterJson.setRevisionNumber(letter.getRevisionNumber());
            letterJson.setContentStatus(letter.getContentStatus());
            
            letterJson.setText(letter.getText());
            letterJson.setUsageCount(letter.getUsageCount());
            
            return letterJson;
        }
    }
    
    public static NumberGson getNumberGson(org.literacyapp.model.content.Number number) {
        if (number == null) {
            return null;
        } else {
            NumberGson numberJson = new NumberGson();
            
            numberJson.setId(number.getId());
            numberJson.setLocale(number.getLocale());
            numberJson.setTimeLastUpdate(number.getTimeLastUpdate());
            numberJson.setRevisionNumber(number.getRevisionNumber());
            numberJson.setContentStatus(number.getContentStatus());
            
            numberJson.setValue(number.getValue());
            numberJson.setSymbol(number.getSymbol());
            numberJson.setWord(getWordGson(number.getWord()));
            
            return numberJson;
        }
    }
    
    public static VideoGson getVideoGson(Video video) {
        if (video == null) {
            return null;
        } else {
            VideoGson videoGson = new VideoGson();
            
            videoGson.setId(video.getId());
            videoGson.setLocale(video.getLocale());
            videoGson.setTimeLastUpdate(video.getTimeLastUpdate());
            videoGson.setRevisionNumber(video.getRevisionNumber());
            videoGson.setContentStatus(video.getContentStatus());
            
            videoGson.setDownloadUrl("/video/" + video.getId() + "." + video.getVideoFormat().toString().toLowerCase());
            videoGson.setDownloadSize(video.getBytes().length / 1024);
            videoGson.setContentType(video.getContentType());
            videoGson.setLiteracySkills(video.getLiteracySkills());
            videoGson.setNumeracySkills(video.getNumeracySkills());
            
            List<LetterGson> letters = new ArrayList<>();
            for (Letter letter : video.getLetters()) {
                LetterGson letterGson = getLetterGson(letter);
                letters.add(letterGson);
            }
            videoGson.setLetters(letters);
            
            List<NumberGson> numbers = new ArrayList<>();
            for (Number number : video.getNumbers()) {
                NumberGson numberGson = getNumberGson(number);
                numbers.add(numberGson);
            }
            videoGson.setNumbers(numbers);
            
            List<WordGson> words = new ArrayList<>();
            for (Word word : video.getWords()) {
                WordGson wordGson = getWordGson(word);
                words.add(wordGson);
            }
            videoGson.setWords(words);
            
            videoGson.setTitle(video.getTitle());
            videoGson.setVideoFormat(video.getVideoFormat());
            videoGson.setThumbnailDownloadUrl("/video/" + video.getId() + "/thumbnail.png");
            
            return videoGson;
        }
    }
    
    public static WordGson getWordGson(Word word) {
        if (word == null) {
            return null;
        } else {
            WordGson wordJson = new WordGson();
            
            wordJson.setId(word.getId());
            wordJson.setLocale(word.getLocale());
            wordJson.setTimeLastUpdate(word.getTimeLastUpdate());
            wordJson.setRevisionNumber(word.getRevisionNumber());
            wordJson.setContentStatus(word.getContentStatus());
            
            wordJson.setText(word.getText());
            wordJson.setPhonetics(word.getPhonetics());
            wordJson.setUsageCount(word.getUsageCount());
            
            return wordJson;
        }
    }
}
