package ru.psb.backend_java.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.psb.backend_java.entity.Document;
import ru.psb.backend_java.repository.DocumentRepository;
import ru.psb.backend_java.uitls.FileInfo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final RestService restService;

    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    public void process(FileInfo fileUtils) {
        documentRepository.save(Document.builder()
                .documentNomenclatureId(fileUtils.getNomenclature())
                .inn(fileUtils.getInn())
                .unrecognized(true)
                .dateOfUploading(LocalDateTime.now())
                .state("Отправлен на распознавание")
                .build());
        try {
            restService.process(fileUtils.getFilename(), fileUtils.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Ошибка обработки - {}", e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}
