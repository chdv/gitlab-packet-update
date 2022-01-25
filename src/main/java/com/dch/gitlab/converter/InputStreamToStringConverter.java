package com.dch.gitlab.converter;

import com.dch.gitlab.exception.GitlabException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

@Component
public class InputStreamToStringConverter implements Function<InputStream, String> {

    @Override
    public String apply(InputStream inputStream) {
        try {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new GitlabException(e.getMessage(), e);
        }
    }

}
