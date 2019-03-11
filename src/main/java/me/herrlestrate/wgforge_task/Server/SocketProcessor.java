package me.herrlestrate.wgforge_task.Server;

import me.herrlestrate.wgforge_task.Application;
import me.herrlestrate.wgforge_task.SQL.SQLWorker;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.ResultSet;

public class SocketProcessor implements Runnable {

    private Socket s;
    private InputStream is;
    private OutputStream os;
    private String line;
    private JSONObject json;
    private SQLWorker sqlWorker;

    public SocketProcessor(Socket s) throws Throwable {
        this.s = s;
        this.is = s.getInputStream();
        this.os = s.getOutputStream();
        sqlWorker = Application.getSqlWorker();
    }

    public void run() {
        try {
            readInputHeaders();

            String[] args = line.split(" ");
            System.err.println(args[0] + ":" + args[1]);
            if(args[0].equals("GET")){
                if(args[1].equalsIgnoreCase("/ping")){
                    writeResponse("Cats Service. Version 0.1", "Content-Type: text/html\r\n");
                }
                if(args[1].equalsIgnoreCase("/json")){
                    String answer = "";
                    String type = "Content-Type: application/json\r\n";

                    writeResponse("",type);
                }
            }else if(args[0].equals("POST")){
                String message = "OK!";
                String name = "",color = "";
                int tail = 0,whiskers = 0;
                try{
                    name = json.getString("name");
                    ResultSet rs = sqlWorker.getCat(name);
                    if(rs.next())message = "This cat already exists!";
                }catch(JSONException e){
                    message = "Invalid name(accepted not string!)";
                }
                try{
                    color = json.getString("color");
                }catch (JSONException ex){
                    message = "Invalid color(accepted not color!)";
                }
                try {
                    tail = (json.getInt("tail_length"));
                    if(tail<0)message = "Invalid tail(tail cannot be < 0!)";
                }catch (JSONException ex){
                    message = "Invalid tail(accepted not int!)";
                }
                try{
                    whiskers = (json.getInt("whiskers_length"));
                    if(whiskers < 0)message = "Invalid whiskers(whiskers cannot be < 0!)";
                }catch (JSONException ex){
                    message = "Invalid whiskers(accepted not int!)";
                }
                if(message.equalsIgnoreCase("OK!")){
                    sqlWorker.addCat(name,color,tail,whiskers);
                    ResultSet rs = sqlWorker.getCat(name);
                    if(!rs.next())message = "Invalid color type!";
                }
                writeResponse(message,"Content-Type: text/html\r\n");
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

    private void writeResponse(String s,String type) throws Throwable {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                //"Content-Type: text/html\r\n" +
                type +
                "Content-Length: " + s.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + s;
        os.write(result.getBytes());
        os.flush();
    }

    private void readInputHeaders() throws Throwable {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        line = "";
        int postDataI = 0;
        while(true) {
            String s = br.readLine();
            System.err.println(s);
            if(s.startsWith("Content-Length: "))postDataI = new Integer(s.substring(
                    s.indexOf("Content-Length:") + 16));
            if(line.isEmpty() && s != null)line = s;
            if(s == null || s.trim().length() == 0) {
                break;
            }

        }
        String postData = "";
        json = new JSONObject("{}");
        if (postDataI > 0) {
            char[] charArray = new char[postDataI];
            br.read(charArray, 0, postDataI);
            postData = new String(charArray);

            System.err.println(postData);

            json = new JSONObject(postData);
        }

    }
}