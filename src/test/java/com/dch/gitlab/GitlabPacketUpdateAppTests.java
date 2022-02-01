package com.dch.gitlab;

import com.dch.gitlab.function.GitlabCommiter;
import com.dch.gitlab.function.GitlabDirList;
import com.dch.gitlab.function.GitlabDownloader;
import com.dch.gitlab.function.GitlabJsonParser;
import com.dch.gitlab.function.GitlabPusher;
import com.dch.gitlab.properties.GitlabPacketUpdateInfo;
import com.dch.gitlab.function.GitlabPomUpdater;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
class GitlabPacketUpdateAppTests {

    @MockBean
    private GitlabAppRunner gitlabAppRunner;

    @Test
    public void testProcessesJsonParse(@Autowired GitlabJsonParser parser,
                                       @Value("classpath:input/gitlab-processes.json") Resource inputJson) throws IOException {
        var res = parser.apply(inputJson.getInputStream());
        MatcherAssert.assertThat(res, Matchers.hasSize(1));
    }

    @Test
    @Disabled
    public void testGitlabDownloader(@Autowired GitlabPacketUpdateInfo gitlabInfo,
                                     @Autowired GitlabDownloader gitlabDownloader) {
        Optional.of(gitlabInfo.getGitlab()).map(gitlabDownloader);
    }

    @Test
    @Disabled
    public void testPomUpdater(@Autowired GitlabPacketUpdateInfo gitlabInfo,
                               @Autowired GitlabPomUpdater gitlabPomUpdater,
                               @Autowired GitlabDirList gitlabDirList) {
        log.info("count " +
                Stream.of(gitlabInfo.getOutputDir())
                        .map(gitlabDirList)
                        .flatMap(list -> list.stream())
                        .map(gitlabPomUpdater)
                        .filter(o -> o.isPresent())
                        .count());
    }

    @Test
    @Disabled
    public void testCommit(@Autowired GitlabPacketUpdateInfo gitlabInfo,
                           @Autowired GitlabCommiter gitlabCommiter,
                           @Autowired GitlabDirList gitlabDirList) {
        log.info("count " +
                Stream.of(gitlabInfo.getOutputDir())
                        .map(gitlabDirList)
                        .flatMap(list -> list.stream())
                        .map(gitlabCommiter)
                        .count());
    }

    @Test
    @Disabled
    public void testSmallPush(@Autowired GitlabPusher gitlabPusher) {
        log.info("list " +
                Stream.of(Path.of("C:\\Workspace\\processes\\bp-access-limitation-fu-tm"))
                        .map(gitlabPusher)
                        .collect(Collectors.toList()));
    }

    @Test
    @Disabled
    public void testPush(@Autowired GitlabPacketUpdateInfo gitlabInfo,
                         @Autowired GitlabPusher gitlabPusher,
                         @Autowired GitlabDirList gitlabDirList) {
        log.info("count " +
                Stream.of(gitlabInfo.getOutputDir())
                        .map(gitlabDirList)
                        .flatMap(list -> list.stream())
                        .map(gitlabPusher)
                        .count());
    }

}