package com.example.JenkinsCustomDistro;


import io.jenkins.tools.warpackager.lib.config.Config;
import io.jenkins.tools.warpackager.lib.impl.Builder;
import java.io.File;
import org.springframework.stereotype.Service;

@Service
public class WarGeneratorService {

    public static final String DEFAULT_TMP_DIR_NAME = "tmp";
    public static final String DEFAULT_VERSION = "1.0-SNAPSHOT";


    public void generateWAR() {
        System.out.println("Building the war");
        final Config cfg;
        final File configPath = new File("/home/sladyn/Jenkins/POC_CustomService/POC_CustomService/src/main/resources/static/packager-config.yml");
        try {
            cfg = Config.loadConfig(configPath);
            cfg.buildSettings.setTmpDir(new File(DEFAULT_TMP_DIR_NAME));
            cfg.buildSettings.setVersion(DEFAULT_VERSION);
            cfg.buildSettings.setInstallArtifacts(true);
            new Builder(cfg).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
