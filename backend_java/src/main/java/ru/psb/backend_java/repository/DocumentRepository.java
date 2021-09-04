package ru.psb.backend_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.psb.backend_java.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
