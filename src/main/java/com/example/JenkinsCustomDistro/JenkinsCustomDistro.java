package com.example.JenkinsCustomDistro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JenkinsCustomDistro {
	public static void main(String[] args) {
		SpringApplication.run(JenkinsCustomDistro.class, args);
		System.out.println("Starting custom war service");
	}
}

