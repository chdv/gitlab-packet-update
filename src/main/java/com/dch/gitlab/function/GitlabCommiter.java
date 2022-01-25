package com.dch.gitlab.function;

import com.dch.gitlab.properties.GitlabPacketUpdateInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class GitlabCommiter implements Function<Path, Path> {

    private final ProcessExecutor processExecutor;

    private final GitlabPacketUpdateInfo gitlabInfo;

    @Override
    public Path apply(Path path) {
        processExecutor.apply("git checkout -b " + gitlabInfo.getBranchName(), path);
        processExecutor.apply("git add .", path);
        processExecutor.apply("git commit -m \"" + gitlabInfo.getCommitMessage() + "\"", path);
        return path;
    }

}
