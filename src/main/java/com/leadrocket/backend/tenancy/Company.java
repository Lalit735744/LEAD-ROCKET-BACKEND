package com.leadrocket.backend.tenancy;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

/**
 * Company document stored in global 'companies' collection. Each company will
 * have tenant-specific collections named using the company id.
 */
@Document(collection = "companies")
public class Company {

    @Id
    private String id;
    private String name;
    private String domain;
    private Instant createdAt = Instant.now();
    private Map<String, Boolean> featureFlags;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Map<String, Boolean> getFeatureFlags() { return featureFlags; }
    public void setFeatureFlags(Map<String, Boolean> featureFlags) { this.featureFlags = featureFlags; }
}

