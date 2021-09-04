package ru.psb.backend_java.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.psb.backend_java.service.FileService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class MainApplicationController {

    private final FileService fileService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @PostMapping(value = "/uploadArchive")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile multipartFile) {
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
}
