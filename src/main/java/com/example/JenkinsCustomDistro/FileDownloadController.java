package com.example.JenkinsCustomDistro;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("apis/download")
public class FileDownloadController {

    @RequestMapping(path = "/jcascYaml",  method = RequestMethod.GET)
    public ResponseEntity<Resource> download(String param) throws IOException {
        File file = new File("/home/sladyn/Jenkins/POC_CustomService/POC_CustomService/src/main/resources/static/jcasc.yaml");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=jcasc.yaml");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType( "application/octet-stream"))
            .body(resource);
    }
}
