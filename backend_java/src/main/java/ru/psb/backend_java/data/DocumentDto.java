package ru.psb.backend_java.data;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class DocumentDto {

    private String name;
    private String nomenclatureId;
    private String date;
    private Map<String, String> criteriaValueMap;

}
