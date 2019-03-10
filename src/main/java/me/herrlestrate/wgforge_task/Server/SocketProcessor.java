package me.herrlestrate.wgforge_task.Server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class SocketProcessor implements Runnable {

    private Socket s;
    private InputStream is;
    private OutputStream os;
    private String line;

    public SocketProcessor(Socket s) throws Throwable {
        this.s = s;
        this.is = s.getInputStream();
        this.os = s.getOutputStream();
    }

    public void run() {
        try {
            readInputHeaders();

            String[] args = line.split(" ");
            System.err.println(args[0] + ":" + args[1]);
            if(args[0].equals("GET")){
                if(args[1].equalsIgnoreCase("/ping")){
                    writeResponse("Cats Service. Version 0.1");
                }
            }else if(args[0].equals("POST")){

            }
        } catch (Throwable t) {
            /*do nothing*/
        } finally {
            try {
                s.close();
            } catch (Throwable t) {
                /*do nothing*/
            }
        }
        System.err.println("Client processing finished");
    }

    private void writeResponse(String s) throws Throwable {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + s.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + s;
        os.write(result.getBytes());
        os.flush();
    }

    private void readInputHeaders() throws Throwable {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        line = "";
        while(true) {
            String s = br.readLine();
            if(line.isEmpty() && s != null)line = s;
            if(s == null || s.trim().length() == 0) {
                break;
            }
        }
    }
}