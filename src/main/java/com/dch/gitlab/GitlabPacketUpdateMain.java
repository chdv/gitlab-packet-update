package com.dch.gitlab;

import com.dch.gitlab.properties.GitlabPacketUpdateInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GitlabPacketUpdateInfo.class)
public class GitlabPacketUpdateMain {

    public static void main(String[] args) {
        var app = new SpringApplication(new Class[]{ GitlabPacketUpdateMain.class });
        app.setWebApplicationType(WebApplicationType.NONE);
        var ctx = app.run(args);
    }
}
