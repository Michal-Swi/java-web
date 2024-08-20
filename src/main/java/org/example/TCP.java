package org.example;

import java.io.*;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.Callable;

public class TCP {
    private int port;
    private InetAddress serverAddress;
    private ServerSocket serverSocket;
    private DataInputStream in;
    private String lastRequest;
    private String lastResponse;
    private Request lastRequestParsed = new Request();
    private Socket currentClientSocket;
    private boolean isServerRunning = false;
    private Map<String, Page> routes = new HashMap<String, Page>();

    public boolean startServer() {
        if (serverAddress == null) {
            return false;
        }

        if (port == 0) {
            return false;
        }

        try {
            serverSocket = new ServerSocket(port, 0, serverAddress);
        } catch (IOException e) {
            return false;
        }

        System.out.println("Socket created");

        isServerRunning = true;
        while (isServerRunning) {
            try {
                currentClientSocket = serverSocket.accept();
            } catch (IOException e) {
                return false;
            }

            System.out.println("Accepted");

            try {
                in = new DataInputStream(new BufferedInputStream(currentClientSocket.getInputStream()));
            } catch (IOException e) {
                lastRequest = null;
                continue;
            }

            System.out.println("Recived in");

            try {
                lastRequest = readIn(in);
            } catch (IOException e) {
                lastRequest = null;
            }

            sendResponse();
        }

        return true;
    }

    public boolean sendResponse() {
        if (lastResponse == null) {
            return false;
        }

        if (lastRequest == null) {
            return false;
        }

        lastRequestParsed.parseRequest(lastRequest);

        if (!routes.get(lastRequestParsed.getUrl()).isAccessibleByEveryone()) {
            return false;
        }

        Response response = new Response();
        response.setHtml(routes.get(lastRequestParsed.getUrl()).getHtml());
        response.setCode("200");
        response.setVersion("HTTP/1.1");
        response.setMessage("OK");
        response.formResponse();

        OutputStream out;
        try {
            out = currentClientSocket.getOutputStream();
        } catch (IOException e) {
            return false;
        }

        PrintWriter writer = new PrintWriter(out);

        writer.print(response.getResponse());
        writer.flush();

        return true;
    }

    public boolean addRoute(String route, Page page) {
        if (route == null) return false;

        if (page == null) return false;

        routes.put(route, page);
        return true;
    }

    private String readIn(DataInputStream in) throws IOException {
        String request = "";

        byte[] array = new byte[1024];
        in.read(array);

        for (int i = 0; i < 1024; i++) {
            if (array[i] == 10 && array[i - 1] == 13 &&
                    array[i - 2] == 10 && array[i - 3] == 13) {
                break;
            }
            request += (char) array[i];
        }

        return request;
    }

    public Map<String, Page> getRoutes() {
        return routes;
    }

    public void setRoutes(Map<String, Page> routes) {
        this.routes = routes;
    }

    public void stopServer() {
        isServerRunning = false;
    }

    public boolean isServerRunning() {
        return isServerRunning;
    }

    public void setServerRunning(boolean serverRunning) {
        isServerRunning = serverRunning;
    }

    public String getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(String lastResponse) {
        this.lastResponse = lastResponse;
    }

    public String getLastRequest() {
        return lastRequest;
    }

    public InetAddress getServerAddress() {
        return serverAddress;
    }

    public boolean setServerAddress(String serverAddress) {
        InetAddress inetServerAddress;

        try {
            inetServerAddress = InetAddress.getByName(serverAddress);
        } catch (UnknownHostException e) {
            return false;
        }

        this.serverAddress = inetServerAddress;
        return true;
    }

    public boolean isServerAddressSet() {
        return serverAddress.getAddress() != null;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public DataInputStream getIn() {
        return in;
    }

    public Socket getCurrentClientSocket() {
        return currentClientSocket;
    }
}
