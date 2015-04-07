package com.wix.server.endpoint;

/**
 * Created by racastur on 25-03-2015.
 */
public class RestErrorMessage {

    private String errorCode;

    private String errorMessage;

    public RestErrorMessage() {

    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
