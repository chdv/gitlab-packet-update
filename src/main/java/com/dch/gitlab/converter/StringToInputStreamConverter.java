package com.dch.gitlab.converter;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

@Component
public class StringToInputStreamConverter implements Function<String, InputStream> {

    @Override
    public InputStream apply(String s) {
        return new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
    }

}
