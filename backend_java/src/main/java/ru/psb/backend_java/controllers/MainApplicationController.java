package ru.psb.backend_java.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.psb.backend_java.entity.Document;
import ru.psb.backend_java.service.DocumentService;
import ru.psb.backend_java.service.FileService;
import ru.psb.backend_java.uitls.FileInfo;

import javax.annotation.PreDestroy;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping(value = "/api")
@RestController
@Slf4j
public class MainApplicationController {

    @Value("${root.document.folder:psb}")
    private String rootDocumentFolder;
    private final FileService fileService;
    private final DocumentService documentService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @PostMapping(value = "/uploadArchive")
    public ResponseEntity<?> uploadArchive(@RequestParam("file") MultipartFile multipartFile) {
        var split = multipartFile.getOriginalFilename().split("\\.");
        var extension = split[split.length - 1];
        if (extension.equals("gz") || extension.equals("zip") || extension.equals("tar")) {
            executorService.submit(() -> fileService.process(multipartFile));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(400)
                    .body("Неправильный формат архива, ожидалось tar/gz/zip");
        }
    }

    @PostMapping(value = "/uploadDocument", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadDocument(@RequestParam("file") MultipartFile multipartFile,
                                            @RequestParam("inn") String inn,
                                            @RequestParam("nomenclature") String nomenclature) throws Exception {
        var fileInfo = FileInfo.builder()
                        .inn(inn)
                                .nomenclature(nomenclature)
                                        .filename(multipartFile.getOriginalFilename())
                                                .bytes(multipartFile.getBytes())
                .build();
        executorService.submit(() -> documentService.process(fileInfo));
        return ResponseEntity.ok("Успешно загружен");

    }

    @GetMapping(value = "/index")
    public List<Document> getDocumentList() {
        var documents = documentService.findAll();
        log.info("Найдено {} документов", documents.size());
        return documentService.findAll();
    }

    @GetMapping(value = "/getDocuments")
    public ResponseEntity<?> getDocuments() throws Exception {
        var documentMap = new HashMap<String, Object>();
        var rootFolderFilepath = Paths.get(".").toAbsolutePath()
                .resolve("src")
                .resolve("main")
                .resolve("resources")
                .resolve(rootDocumentFolder);

        Files.walk(rootFolderFilepath).forEach(path -> {
            var file = new File(path.toAbsolutePath().toString());
            if (file.isDirectory() && Arrays.stream(Objects.requireNonNull(file.listFiles()))
                    .noneMatch(File::isDirectory)) {
                documentMap.put(rootFolderFilepath.relativize(path).toString(), Arrays.stream(Objects.requireNonNull(file.listFiles()))
                        .map(File::getName)
                        .collect(Collectors.toList()));
            }
        });
        return ResponseEntity.ok(documentMap);
    }

    @PreDestroy
    public void shutdown() {
        this.executorService.shutdown();
    }
}
