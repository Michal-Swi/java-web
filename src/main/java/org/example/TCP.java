package org.example;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCP {
    private int port;
    private InetAddress serverAddress;
    private ServerSocket serverSocket;
    private DataInputStream in;
    private String lastRequest;
    private String lastResponse;
    private boolean isSocketCreated = false;
    private Socket currentClientSocket;

    public boolean startServer() {
        isSocketCreated = false;

        if (serverAddress == null) {
            return false;
        }

        if (port == 0) {
            return false;
        }

        try {
            serverSocket = new ServerSocket(port, 0, serverAddress);
            isSocketCreated = true;
        } catch (IOException e) {
            return false;
        }

        System.out.println("Socket created");

        return true;
    }

    public boolean waitForClientRequest() {
        try {
            currentClientSocket = serverSocket.accept();
        } catch (IOException e) {
            return false;
        }

        System.out.println("Accepted");

        try {
            in = new DataInputStream(new BufferedInputStream(currentClientSocket.getInputStream()));
        } catch (IOException e) {
            return false;
        }

        System.out.println("Recived in");

        try {
            lastRequest = readIn(in);
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public boolean sendResponse() {
        if (lastResponse == null) {
            return false;
        }

        OutputStream out;
        try {
            out = currentClientSocket.getOutputStream();
        } catch (IOException e) {
            return false;
        }

        PrintWriter writer = new PrintWriter(out);

        System.out.println(lastResponse);

        writer.print(lastResponse);
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

    public boolean isSocketCreated() {
        return isSocketCreated;
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
