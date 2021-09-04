package ru.psb.backend_java.uitls;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileInfo {

    private String inn;
    private String filename;
    private String nomenclature;
    private byte[] bytes;
}
