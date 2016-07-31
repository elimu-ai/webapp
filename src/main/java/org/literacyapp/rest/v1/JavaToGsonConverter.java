package org.literacyapp.rest.v1;

import java.util.HashSet;
import java.util.Set;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.Device;
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
            
            audioGson.setFileUrl("/audio/" + audio.getId() + "." + audio.getAudioFormat().toString().toLowerCase());
            audioGson.setFileSize(audio.getBytes().length / 1024);
            audioGson.setContentType(audio.getContentType());
            audioGson.setAttributionUrl(audio.getAttributionUrl());
            audioGson.setLiteracySkills(audio.getLiteracySkills());
            audioGson.setNumeracySkills(audio.getNumeracySkills());
            
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
            deviceJson.setDeviceModel(device.getDeviceModel());
            deviceJson.setTimeRegistered(device.getTimeRegistered());
            deviceJson.setOsVersion(device.getOsVersion());
            deviceJson.setLocale(device.getLocale());
            
            Set<DeviceGson> devicesNearby = new HashSet<DeviceGson>();
            for (Device deviceNearby : device.getDevicesNearby()) {
                DeviceGson deviceJsonNearby = getDeviceGson(deviceNearby);
                if (deviceJsonNearby != null) {
                    devicesNearby.add(deviceJsonNearby);
                }
            }
            if (!devicesNearby.isEmpty()) {
                deviceJson.setDevicesNearby(devicesNearby);
            }
            
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
            
            imageGson.setFileUrl("/image/" + image.getId() + "." + image.getImageFormat().toString().toLowerCase());
            imageGson.setFileSize(image.getBytes().length / 1024);
            imageGson.setContentType(image.getContentType());
            imageGson.setAttributionUrl(image.getAttributionUrl());
            imageGson.setLiteracySkills(image.getLiteracySkills());
            imageGson.setNumeracySkills(image.getNumeracySkills());
            
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
            
            letterJson.setText(letter.getText());
            
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
            
            videoGson.setFileUrl("/video/" + video.getId() + "." + video.getVideoFormat().toString().toLowerCase());
            videoGson.setFileSize(video.getBytes().length / 1024);
            videoGson.setContentType(video.getContentType());
            videoGson.setAttributionUrl(video.getAttributionUrl());
            videoGson.setLiteracySkills(video.getLiteracySkills());
            videoGson.setNumeracySkills(video.getNumeracySkills());
            
            videoGson.setTitle(video.getTitle());
            videoGson.setVideoFormat(video.getVideoFormat());
            
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
            
            wordJson.setText(word.getText());
            // TODO: set phonetics
            
            return wordJson;
        }
    }
}
