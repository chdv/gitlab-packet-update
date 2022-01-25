package com.dch.gitlab.function;

import com.dch.gitlab.exception.GitlabException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class GitlabJsonParser implements Function<InputStream, List<String>> {

    private final ObjectMapper objectMapper;

    @Override
    public List<String> apply(InputStream is) {
        try {
            JsonNode node = objectMapper.readTree(is);
            var projects = StreamSupport.stream(node.at("/projects").spliterator(), false);
            return projects.filter(n -> !n.at("/archived").booleanValue())
                    .map(n -> n.at("/ssh_url_to_repo").textValue())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new GitlabException(e.getMessage(), e);
        }
    }
}
