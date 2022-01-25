package com.dch.gitlab.exception;

import org.springframework.core.NestedRuntimeException;

public class GitlabException extends NestedRuntimeException {

    public GitlabException(String msg) {
        super(msg);
    }

    public GitlabException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
