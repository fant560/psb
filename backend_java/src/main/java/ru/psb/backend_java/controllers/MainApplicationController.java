package ru.psb.backend_java.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.psb.backend_java.service.FileService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping(value = "/api")
@RestController
public class MainApplicationController {

    @Value("${root.document.folder:psb}")
    private String rootDocumentFolder;
    private final FileService fileService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @PostMapping(value = "/uploadArchive")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
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
}
