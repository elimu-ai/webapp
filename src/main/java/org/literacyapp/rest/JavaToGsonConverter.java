package org.literacyapp.rest;

import java.util.HashSet;
import java.util.Set;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.Device;
import org.literacyapp.model.content.Word;
import org.literacyapp.model.admin.ApplicationVersion;
import org.literacyapp.model.content.Allophone;
import org.literacyapp.model.gson.DeviceGson;
import org.literacyapp.model.gson.content.NumberGson;
import org.literacyapp.model.gson.content.WordGson;
import org.literacyapp.model.gson.admin.ApplicationGson;
import org.literacyapp.model.gson.admin.ApplicationVersionGson;
import org.literacyapp.model.gson.content.AllophoneGson;

public class JavaToGsonConverter {
    
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
//            applicationVersionJson.setTimeUploaded(applicationVersion.getTimeUploaded());
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
            deviceJson.setRooted(device.isRooted());
            
            Set<DeviceGson> devicesNearby = new HashSet<DeviceGson>();
            for (Device deviceNearby : device.getDevicesNearby()) {
                DeviceGson deviceJsonNearby = getDeviceGson(deviceNearby);
                devicesNearby.add(deviceJsonNearby);
            }
            if (!devicesNearby.isEmpty()) {
                deviceJson.setDevicesNearby(devicesNearby);
            }
            
            return deviceJson;
        }
    }

    public static NumberGson getNumberGson(org.literacyapp.model.content.Number number) {
        if (number == null) {
            return null;
        } else {
            NumberGson numberJson = new NumberGson();
            numberJson.setId(number.getId());
            numberJson.setLocale(number.getLocale());
            
            numberJson.setValue(number.getValue());
            numberJson.setSymbol(number.getSymbol());
            numberJson.setWord(getWordGson(number.getWord()));
            return numberJson;
        }
    }
    
    public static WordGson getWordGson(Word word) {
        if (word == null) {
            return null;
        } else {
            WordGson wordJson = new WordGson();
            wordJson.setId(word.getId());
            wordJson.setLocale(word.getLocale());
            
            wordJson.setText(word.getText());
            return wordJson;
        }
    }
}
