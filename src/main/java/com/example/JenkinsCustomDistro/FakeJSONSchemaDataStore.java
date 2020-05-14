package com.example.JenkinsCustomDistro;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Repository;

@Repository
public class FakeJSONSchemaDataStore {

    public String getJSONSchema() {
        try {
            return new String(Files.readAllBytes(Paths.get("/home/sladyn/Jenkins/POC_CustomService/POC_CustomService/src/main/resources/static/jcasc.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Hello world";
    }
}
