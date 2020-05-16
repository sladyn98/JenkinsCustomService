package com.example.JenkinsCustomDistro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.IOException;

public class Util {

    public String convertStringToYaml(String jsonString) throws JsonProcessingException, IOException {
        JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonString);
        String jsonAsYaml = new YAMLMapper().writeValueAsString(jsonNodeTree);
        return jsonAsYaml;
    }
}
