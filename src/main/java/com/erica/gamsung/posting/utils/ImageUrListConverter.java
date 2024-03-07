package com.erica.gamsung.posting.utils;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ImageUrListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = " , ";
    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        if (stringList == null) {
            return null;
        }

        return String.join(SPLIT_CHAR, stringList);
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        if (string == null || string.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(string.split(SPLIT_CHAR));
    }
}
