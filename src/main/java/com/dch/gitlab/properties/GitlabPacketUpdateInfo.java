package com.dch.gitlab.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("packet-update")
@RequiredArgsConstructor
@ConstructorBinding
@Getter
public class GitlabPacketUpdateInfo {

    private final GitlabInfo gitlab;

    private final String outputDir;

    private final String branchName;

    private final String commitMessage;

    private final String versionOld;

    private final String versionNew;

    private final String cloneCommand;

}
