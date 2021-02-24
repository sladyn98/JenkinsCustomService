package com.example.JenkinsCustomDistro;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


public class PullRequestService {

    public String createPullRequest(String branch, String prDesc) {
        try {
            System.out.println("Creating pull request");
            JSONObject pullRequestDetails = new JSONObject();
            pullRequestDetails.put("title", "New Packager Configuration");
            pullRequestDetails.put("body", prDesc);
            pullRequestDetails.put("head", branch);
            pullRequestDetails.put("base", "master");

            String url = "https://api.github.com/repos/sladyn98/SandBox/pulls";
            HttpPost post = new HttpPost(url);
            post.addHeader("Accept", "application/vnd.github.sailor-v-preview+json");
            post.addHeader("Authorization", returnToken());

            StringEntity input = new StringEntity(pullRequestDetails.toString());
            input.setContentType("application/json");
            post.setEntity(input);

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(post)) {
                String responseString = EntityUtils.toString(response.getEntity());
                JSONObject requestJSON = Utils.convertPayloadToJSON(responseString);
                return requestJSON.getString("html_url");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
      return "Sorry this URL could not be created";
    }

    public String createBranch(String branchName, String prDesc) {
            JSONObject branchDetails = new JSONObject();
            try {
                branchDetails.put("ref", "refs/heads/" + branchName);
                branchDetails.put("sha", getMasterSHA());
                String url = "https://api.github.com/repos/sladyn98/SandBox/git/refs";

                HttpPost post = new HttpPost(url);
                post.addHeader("Accept", "application/vnd.github.sailor-v-preview+json");
                post.addHeader("Authorization", returnToken());
                StringEntity input = new StringEntity(branchDetails.toString());
                input.setContentType("application/json");
                post.setEntity(input);

                try (CloseableHttpClient httpClient = HttpClients.createDefault();
                    CloseableHttpResponse response = httpClient.execute(post)) {
                    String responseString = EntityUtils.toString(response.getEntity());
                    System.out.println("Posting commit response");
                    System.out.println(responseString);
                }
            }catch (Exception e ) {
                e.printStackTrace();
            }
            return createFile("packager-config.yml", branchName, prDesc);
    }


    public String createFile(String filename, String branchName, String prDesc) {
        try {
            String url = "https://api.github.com/repos/sladyn98/SandBox/contents/" + filename;
            String packageContent = readFile("/home/sladyn/Jenkins/POC_CustomService/POC_CustomService/src/main/resources/static/packager-config.yml", StandardCharsets.US_ASCII);
            String base64encodedString = Base64.getEncoder().encodeToString(packageContent.getBytes("utf-8"));
            JSONObject newFileDetails = new JSONObject();
            newFileDetails.put("message", "Adding a new file");
            newFileDetails.put("content", base64encodedString);
            newFileDetails.put("branch", branchName);
            newFileDetails.put("sha", getMasterSHA());


            HttpPut put = new HttpPut(url);
            put.addHeader("Accept", "application/vnd.github.sailor-v-preview+json");
            put.addHeader("Authorization", returnToken());

            StringEntity input = new StringEntity(newFileDetails.toString());
            input.setContentType("application/json");
            put.setEntity(input);

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(put)) {
                String responseString = EntityUtils.toString(response.getEntity());
                System.out.println("Getting create file  request response");
                System.out.println(responseString);
            }

            return createPullRequest(branchName, prDesc);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "Error";
    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }


    public String getMasterSHA() {
        try {
            String url = "https://api.github.com/repos/sladyn98/SandBox/git/ref/heads/master";
            HttpGet get = new HttpGet(url);
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(get)) {
                String responseString = EntityUtils.toString(response.getEntity());
                JSONObject requestJSON = Utils.convertPayloadToJSON(responseString);
                return requestJSON.getJSONObject("object").getString("sha");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "404";
    }


    private String returnToken() {
        String token = null;
        try {
            token = readFile("/home/sladyn/Jenkins/POC_CustomService/POC_CustomService/src/main/resources/static/token.txt", StandardCharsets.US_ASCII);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String finaltoken = "token " + token;
        return finaltoken;
    }
}
