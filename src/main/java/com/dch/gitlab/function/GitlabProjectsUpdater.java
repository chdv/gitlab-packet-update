package com.dch.gitlab.function;

import com.dch.gitlab.properties.GitlabInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class GitlabProjectsUpdater implements Function<GitlabInfo, List<Path>> {

    private final GitlabPomUpdater gitlabPomUpdater;

    private final GitlabDownloader gitlabDownloader;

    private final GitlabCommiter gitlabCommiter;

    private final GitlabPusher gitlabPusher;

    @Override
    public List<Path> apply(GitlabInfo gitlabInfo) {
        return Stream.of(gitlabInfo)
                .map(gitlabDownloader)
                .flatMap(list -> list.stream())
                .map(gitlabPomUpdater)
                .filter(o -> o.isPresent())
                .map(o -> o.get())
                .map(gitlabCommiter)
                .map(gitlabPusher)
                .collect(Collectors.toList());
    }
}
