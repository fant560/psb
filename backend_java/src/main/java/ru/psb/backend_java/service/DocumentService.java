package ru.psb.backend_java.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.psb.backend_java.entity.Document;
import ru.psb.backend_java.repository.DocumentRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final RestService restService;

    public void process(String inn, String nomenclature, MultipartFile multipartFile){
        documentRepository.save(Document.builder()
                .documentNomenclatureId(nomenclature)
                .inn(inn)
                .build());
        try {
            restService.process(multipartFile.getOriginalFilename(), multipartFile.getBytes());
        } catch (Exception e) {
            log.error("Ошибка обработки - {}", e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
