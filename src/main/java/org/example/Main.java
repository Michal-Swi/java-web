package org.example;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        TCP new_tcp = new TCP();
        new_tcp.setPort(2137);

        /*
        InetAddress address;
        try {
            address = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        */

        new_tcp.startServer();
    }
}