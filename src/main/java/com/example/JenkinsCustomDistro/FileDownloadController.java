package com.example.JenkinsCustomDistro;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final WarGeneratorService warGeneratorService;

    @Autowired
    public FileDownloadController(
        WarGeneratorService warGeneratorService) {
        this.warGeneratorService = warGeneratorService;
    }

    @RequestMapping(path = "/jcascYaml",  method = RequestMethod.GET)
    public ResponseEntity<Resource> download() throws IOException {
        File file = new File("/home/sladyn/Jenkins/POC_CustomService/POC_CustomService/src/main/resources/static/jcasc.yaml");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        String headerValue = "attachment; filename=jcasc.yaml";
        return returnResource(returnHeaders(headerValue), file, resource);
    }

    @RequestMapping(path = "/war", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadWAR() throws IOException {
        warGeneratorService.generateWAR();
        File warfile = new File("/home/sladyn/Jenkins/POC_CustomService/POC_CustomService/tmp/output/target/jenkins-casc-demo-1.0-SNAPSHOT.war");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(warfile));
        String headerValue = "attachment; filename=jenkins.war";
        return returnResource(returnHeaders(headerValue), warfile, resource);

    }

    @RequestMapping(path = "/dockerfile", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadDockerFile() throws IOException {
        warGeneratorService.generateWAR();
        File dockerFile = new File("/home/sladyn/Jenkins/POC_CustomService/POC_CustomService/tmp/output/Dockerfile");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(dockerFile));
        String headerValue = "attachment; filename=Dockerfile";
        return returnResource(returnHeaders(headerValue), dockerFile, resource);
    }

    private HttpHeaders returnHeaders(String headerValue) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, headerValue);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }

    private ResponseEntity<Resource> returnResource(HttpHeaders headers, File file, InputStreamResource resource){
        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType( "application/octet-stream"))
            .body(resource);
    }
}
