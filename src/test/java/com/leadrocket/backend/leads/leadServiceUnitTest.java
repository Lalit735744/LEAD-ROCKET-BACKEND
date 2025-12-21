package com.leadrocket.backend.leads;

import com.leadrocket.backend.leads.dto.LeadServiceDTO;
import com.leadrocket.backend.leads.model.Lead;
import com.leadrocket.backend.leads.repository.LeadRepository;
import com.leadrocket.backend.leads.service.LeadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class leadServiceUnitTest {

    private LeadRepository repository;
    private LeadService service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(LeadRepository.class);
        service = new LeadService(repository);
    }

    @Test
    void testGetByIdFound() {
        Lead lead = new Lead();
        lead.setId("1");
        lead.setName("Test");
        when(repository.findById(anyString())).thenReturn(Optional.of(lead));

        LeadServiceDTO dto = service.getById("1");
        assertNotNull(dto);
        assertEquals("1", dto.getId());
        assertEquals("Test", dto.getName());
    }
}

