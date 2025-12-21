package com.leadrocket.backend.leads;

import com.leadrocket.backend.leads.model.Lead;
import com.leadrocket.backend.leads.repository.TenantLeadRepository;
import com.leadrocket.backend.notifications.NotificationService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LeadNewService {

    private final TenantLeadRepository leadRepository;
    private final NotificationService notificationService;

    public LeadNewService(
            TenantLeadRepository leadRepository,
            NotificationService notificationService
    ) {
        this.leadRepository = leadRepository;
        this.notificationService = notificationService;
    }

    /**
     * Create a lead in tenant collection.
     * If assignedTo is set, send a notification to that user.
     */
    public Lead createLead(String companyId, Lead lead, String createdBy) {
        lead.setCreatedAt(new Date());
        lead.setUpdatedAt(new Date());

        Lead saved = leadRepository.save(companyId, lead);

        if (saved.getAssignedTo() != null) {
            notificationService.send(
                    companyId,
                    createdBy,
                    saved.getAssignedTo(),
                    "LEAD_ASSIGNED",
                    "New Lead Assigned",
                    "You have been assigned a new lead: " + saved.getName()
            );
        }

        return saved;
    }
}
