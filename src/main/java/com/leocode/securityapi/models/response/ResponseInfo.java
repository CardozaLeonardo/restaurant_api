package com.leocode.securityapi.models.response;

public class ResponseInfo {

    private String message;

    public ResponseInfo(String message) {

        this.message = message;
    }

    public ResponseInfo() {

    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
