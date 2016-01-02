package org.literacyapp.logic.converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class StringToStringConverter implements Converter<String, String> {

    /**
     * Convert empty strings ("") to null
     */
    public String convert(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        } else {
            return value;
        }
    }
}
