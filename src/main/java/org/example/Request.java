package org.example;

import java.util.Vector;

public class Request {
    private String method = "";
    private String url = "";
    private String version = "";
    private Vector<String> body = new Vector<String>();

    public void parseRequest(String request) {
        int i = 0;
        for (; request.charAt(i) != ' '; i++) {
            method += request.charAt(i);
        }

        i++;

        for (; request.charAt(i) != ' '; i++) {
            url += request.charAt(i);
        }

        i++;

        for (; request.charAt(i) != '\n'; i++) {
            version += request.charAt(i);
        }


        for (; i < request.length(); i++) {
            String line = "";

            for (; request.charAt(i) != '\n' && request.charAt(i) != '\r'; i++) {
                line += request.charAt(i);
            }

            body.addElement(line);
        }

    }

    public void printHeader() {
        System.out.println(method + ' ' + url + ' ' + version);
    }

    public String getVersion() {
        return version;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public Vector<String> getBody() {
        return body;
    }
}
