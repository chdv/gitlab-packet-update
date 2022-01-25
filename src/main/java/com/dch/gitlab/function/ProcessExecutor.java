package com.dch.gitlab.function;

import com.dch.gitlab.converter.InputStreamToStringConverter;
import com.dch.gitlab.exception.GitlabException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessExecutor implements BiFunction<String, Path, Integer> {

    private final InputStreamToStringConverter inputStreamToStringConverter;

    @Override
    public Integer apply(String command, Path path) {
        try {
            File dir = Optional.ofNullable(path).map(Path::toFile).orElse(null);
            var p = Runtime.getRuntime().exec(command, new String[]{}, dir);
            int res = p.waitFor();
            log.info("command: " + command);
            log.info("dir: " + path);
            log.info("exit value: " + res);
            log.info("input stream: " + Optional.of(p.getInputStream()).map(inputStreamToStringConverter).orElse(""));
            log.info("error stream: " + Optional.of(p.getErrorStream()).map(inputStreamToStringConverter).orElse(""));

            return res;
        } catch (IOException | InterruptedException e) {
            throw new GitlabException(e.getMessage(), e);
        }
    }
}
