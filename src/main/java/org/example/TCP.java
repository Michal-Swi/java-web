package org.example;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class TCP {
    private int port;
    private InetAddress serverAddress;
    private ServerSocket serverSocket;
    private DataInputStream in;
    private String request;

    public boolean startServer() {
        try {
            serverSocket = new ServerSocket(port);
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
            request = readIn(in);
        } catch (IOException e) {
            return false;
        }

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

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
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
