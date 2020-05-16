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
        return "";
    }


    public String getJCasCYaml() {
        try {
            return new String(Files.readAllBytes(Paths.get("/home/sladyn/Jenkins/POC_CustomService/POC_CustomService/src/main/resources/static/jcasc.yaml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getCWPConfig() {
        try {
            return new String(Files.readAllBytes(Paths.get("/home/sladyn/Jenkins/POC_CustomService/POC_CustomService/src/main/resources/static/packager-config.yml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
