package ru.psb.backend_java.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompanyDto {

    private String id;
    private String name;
    private String inn;
    private Boolean checked;
    private List<DocumentDto> documents;
}
