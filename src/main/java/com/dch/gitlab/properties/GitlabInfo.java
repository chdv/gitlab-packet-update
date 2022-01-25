package com.dch.gitlab.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConstructorBinding;

@RequiredArgsConstructor
@ConstructorBinding
@Getter
public class GitlabInfo {

    private final String address;

    private final String token;

}
