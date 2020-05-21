package com.example.JenkinsCustomDistro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("api/json-schema")
public class SchemaController {

    private final  SchemaService schemaService;

    @Autowired
    public SchemaController(SchemaService schemaService) {
        this.schemaService = schemaService;
    }

    @CrossOrigin
    @GetMapping
    public String getJSONSchema() {
        return schemaService.getJSONSchema();
    }

    @PostMapping(path = "uploadSchema")
    @ResponseBody
    public void uploadSchema(@RequestBody JSONObject postPayload) {
        System.out.println(postPayload.toString());
        try {
            String yamlData = new Util().convertStringToYaml(postPayload.toString());
            Files.write(Paths.get("/home/sladyn/Jenkins/POC_CustomService/POC_CustomService/src/main/resources/static/jcasc.yaml"), yamlData.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping(path = "getYaml")
    public String getJCasCYaml() {
        return schemaService.getJCasCYaml();
    }

    @GetMapping(path = "getCWPConfig")
    public String getCWPConfig() {
        return schemaService.getCWPConfig();
    }

    @GetMapping(path = "getPlugins")
    public List<String> getPlugins() {
        List<String> pluginStringList = new ArrayList<String>();
        try {
            String content = new String(Files.readAllBytes(Paths.get("/home/sladyn/Jenkins/POC_CustomService/POC_CustomService/src/main/resources/static/updateCenter.json")));
            JSONObject jsonObject = new JSONObject(content);
            JSONObject jsonPluginList = jsonObject.getJSONObject("plugins");
            for(int i = 0; i<jsonPluginList.names().length(); i++){
                System.out.println(jsonPluginList.names().getString(i));
                pluginStringList.add(jsonPluginList.names().getString(i));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return pluginStringList;
    }
}
