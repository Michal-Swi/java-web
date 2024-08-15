package org.example;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        TCP server = new TCP();
        server.setPort(2137);

        boolean wasServerAddressSet =
                server.setServerAddress("127.0.0.1");

        if (!wasServerAddressSet) {
            return;
        }

        server.startServer();
        server.waitForClientRequest();
        System.out.println(server.getLastRequest());
    }
}