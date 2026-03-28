package com.example.notificationservice.repository;

import com.example.notificationservice.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<NotificationTemplate, String> {

    Optional<NotificationTemplate> findByCode(String code);
}
