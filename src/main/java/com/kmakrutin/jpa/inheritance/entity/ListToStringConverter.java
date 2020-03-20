package com.kmakrutin.jpa.inheritance.entity;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ListToStringConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> codes) {
        return codes.stream()
                .filter(code -> code != null && !code.isBlank())
                .map(String::toUpperCase)
                .distinct()
                .collect(Collectors.joining(", "));
    }

    @Override
    public List<String> convertToEntityAttribute(String dbCodes) {
        return Arrays.stream(dbCodes.split(", ")).collect(Collectors.toList());
    }
}
