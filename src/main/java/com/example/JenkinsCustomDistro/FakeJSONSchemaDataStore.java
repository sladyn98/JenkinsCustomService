package com.example.JenkinsCustomDistro;


import org.springframework.stereotype.Repository;

@Repository
public class FakeJSONSchemaDataStore {

    public String getJSONSchema() {
        String Schema = "{\n"
            + "  \"title\": \"A registration form\",\n"
            + "  \"description\": \"A simple form example.\",\n"
            + "  \"type\": \"object\",\n"
            + "  \"required\": [\n"
            + "    \"firstName\",\n"
            + "    \"lastName\"\n"
            + "  ],\n"
            + "  \"properties\": {\n"
            + "    \"firstName\": {\n"
            + "      \"type\": \"string\",\n"
            + "      \"title\": \"First name\",\n"
            + "      \"default\": \"Chuck\"\n"
            + "    },\n"
            + "    \"lastName\": {\n"
            + "      \"type\": \"string\",\n"
            + "      \"title\": \"Last name\"\n"
            + "    },\n"
            + "    \"age\": {\n"
            + "      \"type\": \"integer\",\n"
            + "      \"title\": \"Age\"\n"
            + "    },\n"
            + "    \"bio\": {\n"
            + "      \"type\": \"string\",\n"
            + "      \"title\": \"Bio\"\n"
            + "    },\n"
            + "    \"password\": {\n"
            + "      \"type\": \"string\",\n"
            + "      \"title\": \"Password\",\n"
            + "      \"minLength\": 3\n"
            + "    },\n"
            + "    \"telephone\": {\n"
            + "      \"type\": \"string\",\n"
            + "      \"title\": \"Telephone\",\n"
            + "      \"minLength\": 10\n"
            + "    }\n"
            + "  }\n"
            + "}";
        return Schema;
    }
}
