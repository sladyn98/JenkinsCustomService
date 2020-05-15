package com.example.JenkinsCustomDistro;


import net.sf.json.JSONObject;
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

    @GetMapping
    public String getJSONSchema() {
        return schemaService.getJSONSchema();
    }

    @PostMapping(path = "uploadSchema")
    @ResponseBody
    public void uploadSchema(@RequestBody JSONObject postPayload) {
        System.out.println(postPayload.toString());
    }
}
