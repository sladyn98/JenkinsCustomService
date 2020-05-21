package com.example.JenkinsCustomDistro;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import net.sf.json.JSONString;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.kohsuke.github.GHApp;
import org.kohsuke.github.GHAppInstallation;
import org.kohsuke.github.GHAppInstallationToken;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RestController;

import static com.example.JenkinsCustomDistro.JwtHelper.createJWT;

@RestController
@CrossOrigin("*")
public class GithubController {

    private static String APP_IDENTIFIER="53528";
    @RequestMapping(value = "/event_handler", method = RequestMethod.POST)
    public void handleEvent(@RequestBody String requestBodyString) throws Exception {
        System.out.println("Received event handler event");
        String token = authenticateApplication();
        FileUtils.writeStringToFile(new File("/home/sladyn/Jenkins/POC_CustomService/POC_CustomService/src/main/resources/static/token.txt"), token, Charset
            .forName("UTF-8"));
        JSONObject requestJSON = Utils.convertPayloadToJSON(requestBodyString);

        if (requestJSON.getString("action").equals("created")) {
            System.out.println("Initiating check run because we have received check run event");
            System.out.println("The GithubChecks Application has been successfully installed");
        }
    }


    @PostMapping(path = "/create_PR")
    @ResponseBody
    public String createPR(@RequestBody String postPayload) {
        String branchName = "";
        String description = "";
        try {
            System.out.println(postPayload);
            JSONObject jsonObject = new JSONObject(postPayload);
            branchName = jsonObject.getString("branchName");
            description = jsonObject.getString("description");
        } catch (Exception e) {
            e.printStackTrace();
        }
      return new PullRequestService().createBranch(branchName, description);
    }


    @SuppressWarnings("deprecation")
    private static String authenticateApplication() {
        try {
            String jwtToken = createJWT(APP_IDENTIFIER, 600000);
            GitHub gitHubApp = new GitHubBuilder().withEndpoint("https://api.github.com").withJwtToken(jwtToken).build();
            GHApp app = gitHubApp.getApp();
            List<GHAppInstallation> appInstallations = app.listInstallations().asList();
            if (!appInstallations.isEmpty()) {
                GHAppInstallation appInstallation = appInstallations.get(0);
                GHAppInstallationToken appInstallationToken = appInstallation
                    .createToken(appInstallation.getPermissions())
                    .create();
                return appInstallationToken.getToken();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
