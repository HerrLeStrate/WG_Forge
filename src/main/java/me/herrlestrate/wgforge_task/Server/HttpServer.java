package me.herrlestrate.wgforge_task.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class HttpServer {

    private static Queue<Long> timeHistory = new LinkedList<>();

    public HttpServer() throws Throwable {
        ServerSocket ss = new ServerSocket(8080);
        while (true) {
            Socket s = ss.accept();
            System.err.println("Client accepted");
            new Thread(new SocketProcessor(s)).start();
        }
    }

    public static Queue<Long> getTimeHistory() {
        return timeHistory;
    }
}
