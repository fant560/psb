package ru.psb.backend_java.controllers;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.psb.backend_java.data.CompanyDto;
import ru.psb.backend_java.data.DocumentDto;
import ru.psb.backend_java.data.PeriodDto;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

@RequestMapping(value = "/api/mock")
@RestController
public class MockApiController {

    private final Map<String, CompanyDto> companies = new HashMap<>();
    private final Map<String, PeriodDto> periods = new HashMap<>();

    @PostConstruct
    public void setCompanies() {
        var c1 = CompanyDto.builder()
                .id(UUID.randomUUID().toString())
                .checked(true)
                .inn(RandomStringUtils.randomNumeric(12))
                .name("Elma")
                .build();
        companies.put(c1.getId(), c1);

        var c2 = CompanyDto.builder()
                .id(UUID.randomUUID().toString())
                .checked(false)
                .inn(RandomStringUtils.randomNumeric(12))
                .name("Kamaz")
                .build();
        companies.put(c2.getId(), c2);

        var c3 = CompanyDto.builder()
                .id(UUID.randomUUID().toString())
                .checked(true)
                .inn(RandomStringUtils.randomNumeric(12))
                .name("Yandex")
                .build();
        companies.put(c3.getId(), c3);

        var c4 = CompanyDto.builder()
                .id(UUID.randomUUID().toString())
                .checked(true)
                .inn(RandomStringUtils.randomNumeric(12))
                .name("Coca-cola")
                .build();
        companies.put(c4.getId(), c4);

        var c5 = CompanyDto.builder()
                .id(UUID.randomUUID().toString())
                .checked(false)
                .inn(RandomStringUtils.randomNumeric(12))
                .name("Pepsi")
                .build();
        companies.put(c5.getId(), c5);

        var p1 = PeriodDto.builder()
                .id(UUID.randomUUID().toString())
                .start(LocalDate.of(2020, 7, 1))
                .end(LocalDate.of(2020, 12, 31))
                .name("Второе полугодие 2020 года")
                        .build();
        periods.put(p1.getId(), p1);

        var p2 = PeriodDto.builder()
                .id(UUID.randomUUID().toString())
                .start(LocalDate.of(2021, 1, 1))
                .end(LocalDate.of(2021, 6, 30))
                .name("Первое полугодие 2021 года")
                .build();
        periods.put(p2.getId(), p2);
    }

    @RequestMapping(value = "/companies")
    private Collection<CompanyDto> companies() {
        return companies.values();
    }

    @RequestMapping(value = "/company/{id}")
    private CompanyDto companyDetails(@RequestParam("id") String id) {
        return makeMock(companies.get(id));
    }

    @RequestMapping(value = "/periods")
    private Collection<PeriodDto> periods(){
        return periods.values();
    }

    @RequestMapping(value = "/periods/{id}")
    private Collection<CompanyDto> companiesByPeriod(@RequestParam("id") String periodId){
        return companies.values();
    }



    private CompanyDto makeMock(CompanyDto companyDto) {
        if (companyDto.getName().equals("Elma") || companyDto.getName().equals("Kamaz"))
            if (companyDto.getChecked()) {
                companyDto.setDocuments(new ArrayList<>());
                companyDto.getDocuments().addAll(buildDocuments(companyDto.getChecked()));
                return companyDto;
            }
        return companyDto;
    }

    private List<DocumentDto> buildDocuments(Boolean isChecked) {
        return List.of(
                DocumentDto.builder()
                        .name("Устав")
                        .nomenclatureId("33a37ce4-c6a9-4dad-8424-707abd47c125")
                        .date(LocalDate.now().toString())
                                .build(),
                DocumentDto.builder()
                        .name("Досье по ЮЛ")
                        .nomenclatureId("555ced1c-c169-4d61-9a82-348801494581")
                        .build(),
                DocumentDto.builder()
                        .name("Бухгалтерская отчетность_форма 1")
                        .nomenclatureId("4f501f4a-c665-4cc8-9715-6ed26e7819f2")
                        .build(),
                DocumentDto.builder()
                        .name("Бухгалтерская отчетность_форма 2")
                        .nomenclatureId("cabd193c-f9a9-4a9c-a4ae-80f0347adf40")
                        .date(LocalDate.now().toString())
                        .build(),
                DocumentDto.builder()
                        .name("Аудиторское заключение")
                        .nomenclatureId("16f35ccc-b90f-4731-8178-11f3e0e3ca20")
                        .build(),
                DocumentDto.builder()
                        .name("Описание_деятельности_ГК")
                        .nomenclatureId("a397c2cf-c5ad-4560-bc65-db4f79840f82")
                        .build(),
                DocumentDto.builder()
                        .name(isChecked ? "Решение_назначение ЕИО" : null)
                        .nomenclatureId(isChecked ? "3af37c7f-d8b1-46de-98cc-683b0ffb3513": null)
                        .build()
        );
    }
}
