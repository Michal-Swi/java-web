package org.example;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP {
    private int port;
    private InetAddress serverAddress;
    private ServerSocket serverSocket;
    private DataInputStream in;
    private String lastRequest;
    private String lastResponse;
    private OutputStream out;

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
        Socket clientSocket;

        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            return false;
        }

        System.out.println("Accepted");

        try {
            in = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        } catch (IOException e) {
            return false;
        }

        System.out.println("Recived in");

        try {
            lastRequest = readIn(in);
        } catch (IOException e) {
            return false;
        }

        try {
            out = clientSocket.getOutputStream();
        } catch (IOException e) {
            return false;
        }

        PrintWriter writer = new PrintWriter(out);

        String httpResponse =
                "HTTP/1.1 200 OK\r\n" +
                "Content-type: text/plain\r\n" +
                "\r\n" +
                "Test!\r\n";

        System.out.println(httpResponse);

        writer.print(httpResponse);
        writer.flush();

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

    public String getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(String lastResponse) {
        this.lastResponse = lastResponse;
    }

    public String getLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(String lastRequest) {
        this.lastRequest = lastRequest;
    }

    public InetAddress getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(InetAddress serverAddress) {
        this.serverAddress = serverAddress;
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
}
