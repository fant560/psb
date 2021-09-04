package ru.psb.backend_java.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class LegalEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String address;

    private String inn;
}
