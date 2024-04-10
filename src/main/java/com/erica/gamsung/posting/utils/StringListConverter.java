package com.erica.gamsung.posting.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String SPLIT_CHAR = " @ ";

    /**
     * Java
     * List.of("안녕하세요", "반갑습니다", "우리 가게로 놀러오세요")
     * ->
     * DB
     * "안녕하세요 @ 반갑습니다 @ 우리 가게로 놀러오세요"
     */
    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        return String.join(SPLIT_CHAR, stringList);
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        return Arrays.asList(string.split(SPLIT_CHAR));
    }
}
