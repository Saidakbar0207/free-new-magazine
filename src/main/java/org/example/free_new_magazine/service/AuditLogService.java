package org.example.free_new_magazine.service;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.AuditLog;
import org.example.free_new_magazine.entity.User;
import org.example.free_new_magazine.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final CurrentUserService currentUserService;

    public void log(String action, String endpoint){
        Long userId = null;
        try {
            User user = currentUserService.getCurrentUser();
            if (user != null && user.getId() != null) {
                userId = user.getId().longValue();
            }
        } catch (RuntimeException ex) {
        }

        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setAction(action);
        log.setEndpoint(endpoint);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    public List<AuditLog> getAll() {
        return auditLogRepository.findAll();
    }

    public AuditLog getById(Long id) {
        AuditLog auditLog = auditLogRepository.findById(id).orElse(null);
        return auditLog;
    }

    public List<AuditLog> getByUserId(Long userId) {
        return auditLogRepository.findByUserId(userId);
    }

    public List<AuditLog> getByAction(String action) {
        return auditLogRepository.findByAction(action);
    }

    public void deleteById(Long id) {
        auditLogRepository.deleteById(id);
    }



}
