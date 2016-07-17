package org.literacyapp.rest;

import java.util.HashSet;
import java.util.Set;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.Device;
import org.literacyapp.model.Word;
import org.literacyapp.model.json.DeviceJson;
import org.literacyapp.model.json.NumberJson;
import org.literacyapp.model.json.WordJson;
import org.literacyapp.model.json.admin.application.ApplicationJson;

public class JavaToJsonConverter {
    
    public static ApplicationJson getApplicationJson(Application application) {
        if (application == null) {
            return null;
        } else {
            ApplicationJson applicationJson = new ApplicationJson();
            applicationJson.setId(application.getId());
            applicationJson.setLocale(application.getLocale());
            applicationJson.setPackageName(application.getPackageName());
            applicationJson.setLiteracySkills(application.getLiteracySkills());
            applicationJson.setNumeracySkills(application.getNumeracySkills());
            applicationJson.setApplicationStatus(application.getApplicationStatus());
            return applicationJson;
        }
    }
    
    public static DeviceJson getDeviceJson(Device device) {
        if (device == null) {
            return null;
        } else {
            DeviceJson deviceJson = new DeviceJson();
            deviceJson.setId(device.getId());
            deviceJson.setDeviceId(device.getDeviceId());
            deviceJson.setDeviceModel(device.getDeviceModel());
            deviceJson.setTimeRegistered(device.getTimeRegistered());
            deviceJson.setOsVersion(device.getOsVersion());
            deviceJson.setLocale(device.getLocale());
            deviceJson.setRooted(device.isRooted());
            
            Set<DeviceJson> devicesNearby = new HashSet<DeviceJson>();
            for (Device deviceNearby : device.getDevicesNearby()) {
                DeviceJson deviceJsonNearby = getDeviceJson(deviceNearby);
                devicesNearby.add(deviceJsonNearby);
            }
            if (!devicesNearby.isEmpty()) {
                deviceJson.setDevicesNearby(devicesNearby);
            }
            
            return deviceJson;
        }
    }

    public static NumberJson getNumberJson(org.literacyapp.model.Number number) {
        if (number == null) {
            return null;
        } else {
            NumberJson numberJson = new NumberJson();
            numberJson.setId(number.getId());
            numberJson.setLocale(number.getLocale());
            numberJson.setValue(number.getValue());
            numberJson.setSymbol(number.getSymbol());
            numberJson.setWord(getWordJson(number.getWord()));
            numberJson.setDominantColor(number.getDominantColor());
            return numberJson;
        }
    }
    
    public static WordJson getWordJson(Word word) {
        if (word == null) {
            return null;
        } else {
            WordJson wordJson = new WordJson();
            wordJson.setId(word.getId());
            wordJson.setLocale(word.getLocale());
            wordJson.setText(word.getText());
            return wordJson;
        }
    }
}
