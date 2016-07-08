package com.ktds.junho.sems;

/**
 * Created by 206-005 on 2016-07-04.
 */
public class LoginResult {

    private String sessionId;
    private String message;
    private String result;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = "JSESSIONID=" + sessionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
