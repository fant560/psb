package ru.psb.backend_java.data;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
@Builder
@Getter
public class PeriodDto {

    private String id;
    private LocalDate start;
    private LocalDate end;
    private String name;
}
