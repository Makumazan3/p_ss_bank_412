package com.bank.publicinfo.aspects;

import com.bank.publicinfo.model.Audit;
import com.bank.publicinfo.repositories.AuditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuditAspectTest {

    @InjectMocks
    private AuditAspect auditAspect;

    @Mock
    private AuditRepository auditRepository;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void auditAspectTest() throws Exception {
        Object result = new Object();
        String json = "{\"key\":\"value\"}";

        when(objectMapper.writeValueAsString(result)).thenReturn(json);

        auditAspect.afterCreateMethod(result);

        verify(auditRepository).save(argThat(audit ->
                "CREATE".equals(audit.getOperationType()) &&
                        "Object".equals(audit.getEntityType()) &&
                        LocalDateTime.now().isAfter(audit.getCreatedAt().minusSeconds(1)) &&
                        LocalDateTime.now().isBefore(audit.getCreatedAt().plusSeconds(1)) &&
                        json.equals(audit.getEntityJson())
        ));
    }

    @Test
    void auditUpdateMethod() throws Exception {
        Object result = new Object();
        String json = "{\"key\":\"value\"}";

        when(objectMapper.writeValueAsString(result)).thenReturn(json);

        auditAspect.afterUpdateMethod(result);

        verify(auditRepository).save(argThat(audit ->
                "UPDATE".equals(audit.getOperationType()) &&
                        "Object".equals(audit.getEntityType()) &&
                        LocalDateTime.now().isAfter(audit.getCreatedAt().minusSeconds(1)) &&
                        LocalDateTime.now().isBefore(audit.getCreatedAt().plusSeconds(1)) &&
                        json.equals(audit.getEntityJson())));
    }

    @Test
    void auditDeleteMethod() throws Exception {
        Object result = new Object();
        String json = "{\"key\":\"value\"}";

        when(objectMapper.writeValueAsString(result)).thenReturn(json);

        auditAspect.afterDeleteMethod(result);

        verify(auditRepository).save(argThat(audit ->
                "DELETE".equals(audit.getOperationType()) &&
                        "Object".equals(audit.getEntityType()) &&
                        LocalDateTime.now().isAfter(audit.getCreatedAt().minusSeconds(1)) &&
                        LocalDateTime.now().isBefore(audit.getCreatedAt().plusSeconds(1)) &&
                        json.equals(audit.getEntityJson())));
    }

    @Test
    void auditIgnoresNullResult() {
        auditAspect.afterCreateMethod(null);
        verify(auditRepository, never()).save(any(Audit.class));
    }
}