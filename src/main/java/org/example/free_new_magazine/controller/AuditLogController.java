package org.example.free_new_magazine.controller;

import lombok.RequiredArgsConstructor;
import org.example.free_new_magazine.entity.AuditLog;
import org.example.free_new_magazine.service.AuditLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;


    @GetMapping
    public List<AuditLog> getAllAuditLogs() {
        return auditLogService.getAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<AuditLog> getAuditLogById(@PathVariable Long id) {
        return ResponseEntity.ok(auditLogService.getById(id));
    }

    @GetMapping("/user/{userId}")
    public List<AuditLog> getAuditLogsByUser(@PathVariable Long userId) {
        return auditLogService.getByUserId(userId);
    }


    @GetMapping("/action")
    public List<AuditLog> getAuditLogsByAction(@RequestParam String actionType) {
        return auditLogService.getByAction(actionType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAudit(@PathVariable Long id) {
        auditLogService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
