package ru.psb.backend_java.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.lingala.zip4j.ZipFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${filesystem.rootFolder:#{environment.getProperty('USER_HOME')}/files}")
    private String rootFolder;

    private final RestService restService;

    @SneakyThrows
    public void process(MultipartFile archive) {
        String extractFolder = Paths.get(rootFolder)
                .resolve(UUID.randomUUID().toString()).toAbsolutePath().toString();

        new ZipFile(archive.getResource().getFile())
                .extractAll(extractFolder);

        Files.walk(Paths.get(extractFolder)).parallel().forEach(path -> {
            var filename = path.toAbsolutePath().toString();
            try {
                var bytes = Files.readAllBytes(path);

            } catch (IOException e) {
                throw new IllegalStateException("Всё плохо, файлы не читаются");
            }
        });
    }

}
