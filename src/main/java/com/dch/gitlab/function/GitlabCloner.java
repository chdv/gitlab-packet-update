package com.dch.gitlab.function;

import com.dch.gitlab.properties.GitlabPacketUpdateInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class GitlabCloner implements Function<String, Path> {

    private final ProcessExecutor processExecutor;

    private final GitlabPacketUpdateInfo gitlabInfo;

    @Override
    public Path apply(String gitlabUrl) {
        String name = gitlabUrl.substring(gitlabUrl.lastIndexOf('/') + 1, gitlabUrl.lastIndexOf('.'));
        String resultDir = gitlabInfo.getOutputDir() + File.separator + name;
        processExecutor.apply(gitlabInfo.getCloneCommand() + " " + gitlabUrl + " " + resultDir, null);
        return Path.of(resultDir);
    }
}
