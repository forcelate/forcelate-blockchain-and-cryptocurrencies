package com.jdv.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Service
public class PeerService {
    private static List<Socket> sockets = new ArrayList<>();

    @Value("${socketServer.port}")
    private int socketServerPort;

    @PostConstruct
    public void init() throws IOException {
        // create socket server
        ServerSocket serverSocket = new ServerSocket(socketServerPort);
        // create initial socket
        Socket socket = new Socket(InetAddress.getLocalHost().getHostAddress(), socketServerPort);
        // push to peers
        sockets.add(socket);
    }
}
