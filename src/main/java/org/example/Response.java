package org.example;

import java.util.Vector;

public class Response {
    private String version;
    private String code;
    private String message;
    private Vector<String> additionalInfo = new Vector<>();
    private String html;
    private String response;

    public boolean formResponse() {
        if (version == null) {
            return false;
        }

        if (code == null) {
            return false;
        }

        if (message == null) {
            return false;
        }

        response = version + ' ' + code + ' ' + message + "\r\n";

        if (!additionalInfo.isEmpty()) {
            for (String s : additionalInfo) {
                response += (s + "\r\n");
            }
        }

        response += "\r\n";

        if (!html.isEmpty()) {
            response += html + "\r\n";
        }

        return true;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Vector<String> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(Vector<String> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
