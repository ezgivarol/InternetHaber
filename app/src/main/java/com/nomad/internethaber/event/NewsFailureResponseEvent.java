package com.nomad.internethaber.event;

public final class NewsFailureResponseEvent extends NewsResponseEvent {

    private Exception mException;

    public void setException(Exception exception) {
        this.mException = exception;
    }

    public Exception getException() {
        return mException;
    }
}
