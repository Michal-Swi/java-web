package org.example;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        TCP new_tcp = new TCP();
        new_tcp.setPort(2137);

        InetAddress addr;
        try {
            addr = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        new_tcp.setServerAddress(addr);

        Request request = new Request();

        new_tcp.startServer();
        String req = new_tcp.getLastRequest();
    }
}