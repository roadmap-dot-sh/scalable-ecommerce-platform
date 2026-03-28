package com.example.notificationservice.repository;

import com.example.notificationservice.entity.SmsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsLogRepository extends JpaRepository<SmsLog, String> {
}
