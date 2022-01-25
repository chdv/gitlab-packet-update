package com.dch.gitlab.function;

import com.dch.gitlab.exception.GitlabException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GitlabDirList implements Function<String, List<Path>> {

    @Override
    public List<Path> apply(String outputDir) {
        try {
            return Files.list(Path.of(outputDir)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new GitlabException(e.getMessage(), e);
        }
    }
}
