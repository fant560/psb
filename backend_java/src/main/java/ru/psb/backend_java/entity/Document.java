package ru.psb.backend_java.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Document implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String documentNomenclatureId;

    private String inn;

    private Boolean unrecognized;

    private String documentAbsPath;

    private LocalDateTime dateOfUploading;

    private String state;
}
