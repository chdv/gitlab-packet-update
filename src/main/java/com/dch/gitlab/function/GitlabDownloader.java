package com.dch.gitlab.function;

import com.dch.gitlab.converter.StringToInputStreamConverter;
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
public class GitlabDownloader implements Function<GitlabInfo, List<Path>> {

    private final StringToInputStreamConverter converter;

    private final GitlabExchanger gitlabExchanger;

    private final GitlabJsonParser gitlabJsonParser;

    private final GitlabCloner gitlabCloner;

    @Override
    public List<Path> apply(GitlabInfo gitlabInfo) {
        return Stream.of(gitlabInfo)
                .map(gitlabExchanger)
                .map(converter)
                .map(gitlabJsonParser)
                .flatMap(list->list.stream())
                .map(gitlabCloner)
                .collect(Collectors.toList());
    }
}
