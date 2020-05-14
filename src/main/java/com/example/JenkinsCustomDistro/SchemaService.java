package com.example.JenkinsCustomDistro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchemaService {

    private final SchemaDataAccessService schemaDataAccessService;

    @Autowired
    public SchemaService(
        SchemaDataAccessService schemaDataAccessService) {
        this.schemaDataAccessService = schemaDataAccessService;
    }

    String getJSONSchema() {
        return schemaDataAccessService.getJSONSchema();
    }
}
