package com.dch.gitlab.function;

import com.dch.gitlab.exception.GitlabException;
import com.dch.gitlab.properties.GitlabPacketUpdateInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class GitlabPomUpdater implements Function<Path, Optional<Path>> {

    private final GitlabPacketUpdateInfo gitlabInfo;

    public static final String POM = "pom.xml";

    @Override
    public Optional<Path> apply(Path projectPath) {
        try {
            boolean update = false;

            var op = updateInner(projectPath);

            if(op.isPresent()) {
                update = true;
                updateBase(projectPath, op.get());
            }

            if (update)
                return Optional.of(projectPath);
            else
                return Optional.empty();

        } catch (IOException | XmlPullParserException e) {
            throw new GitlabException(e.getMessage(), e);
        }
    }

    private Optional<String> updateInner(Path projectPath) throws IOException, XmlPullParserException {
        boolean update = false;
        Model model = null;
        Path subProject = projectPath.resolve(projectPath.getName(projectPath.getNameCount() - 1));
        Path currentPath = subProject.resolve(POM);
        try (Reader reader = Files.newBufferedReader(currentPath)) {
            model = new MavenXpp3Reader().read(reader);
        }
        if (model.getParent().getVersion().equals(gitlabInfo.getVersionOld())) {
            update = true;
            var pomString = new String(Files.readAllBytes(currentPath), StandardCharsets.UTF_8);
            pomString = pomString.replaceFirst(gitlabInfo.getVersionOld(), model.getVersion());
            Files.write(currentPath, pomString.getBytes(StandardCharsets.UTF_8));
        }
        return update ? Optional.of(model.getVersion()) : Optional.empty();
    }

    private boolean updateBase(Path projectPath, String newVersion) throws IOException, XmlPullParserException {
        boolean update = false;
        Model model = null;
        Path currentPath = projectPath.resolve(POM);
        try (Reader reader = Files.newBufferedReader(currentPath)) {
            model = new MavenXpp3Reader().read(reader);
        }
        if (model.getVersion().equals(gitlabInfo.getVersionOld())) {
            update = true;
            var pomString = new String(Files.readAllBytes(currentPath), StandardCharsets.UTF_8);
            pomString = pomString.replaceFirst(gitlabInfo.getVersionOld(), newVersion);
            Files.write(currentPath, pomString.getBytes(StandardCharsets.UTF_8));
        }
        return update;
    }

}
