package com.example.JenkinsCustomDistro;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SchemaDataAccessService {

    private final FakeJSONSchemaDataStore fakeJSONSchemaDataStore;

    @Autowired
    public SchemaDataAccessService(FakeJSONSchemaDataStore fakeJSONSchemaDataStore) {
        this.fakeJSONSchemaDataStore = fakeJSONSchemaDataStore;
    }

    String getJSONSchema() {
        return fakeJSONSchemaDataStore.getJSONSchema();
    }
}
