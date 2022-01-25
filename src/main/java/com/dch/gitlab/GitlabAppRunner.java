package com.dch.gitlab;

import com.dch.gitlab.function.GitlabProjectsUpdater;
import com.dch.gitlab.properties.GitlabPacketUpdateInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitlabAppRunner implements ApplicationRunner {

    private final GitlabProjectsUpdater gitlabProjectsUpdater;

    private final GitlabPacketUpdateInfo gitlabInfo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        gitlabProjectsUpdater.apply(gitlabInfo.getGitlab());
    }

}
