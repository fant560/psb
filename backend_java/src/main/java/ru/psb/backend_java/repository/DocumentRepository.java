package ru.psb.backend_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.psb.backend_java.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
