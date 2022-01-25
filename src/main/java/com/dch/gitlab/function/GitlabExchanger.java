package com.dch.gitlab.function;

import com.dch.gitlab.exception.GitlabException;
import com.dch.gitlab.properties.GitlabInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class GitlabExchanger implements Function<GitlabInfo, String> {

    @Override
    public String apply(GitlabInfo gitlabInfo) {
        var responseMono =
                WebClient.create()
                        .get()
                        .uri(URI.create(gitlabInfo.getAddress()))
                        .header("Private-Token", gitlabInfo.getToken())
                        .retrieve()
                        .onStatus(HttpStatus::isError, resp -> Mono.empty())
                        .toEntity(String.class);

        var entity = responseMono.block();
        if (entity.getStatusCode() == HttpStatus.OK) {
            return entity.getBody();
        } else {
            throw new GitlabException(
                    "Error response status: " + entity.getStatusCode() +
                    ", body: " + entity.getBody());
        }
    }

}
