import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class ServerThread extends Thread {
    static Vector<ClientThread> v;
    
    ServerThread(Vector<ClientThread> tv) throws IOException {
        v = tv;
    }
    
    public void run() {
        try {
            while(true) {
                Thread.sleep(100); // Prevent CPU intensive loop
            }
        } catch(Exception e) {
            System.out.println("ServerThread error: " + e);
        }
    }

    static void join(Socket s) throws Exception {
        for(int i = 0; i < v.size(); i++){
            ClientThread client = v.get(i);
            client.out.write(("♦ "+s.getPort() + " has joined the chat\n").getBytes());
        }
    }
    
    static void message(Socket s, String message) throws Exception {
        for(int i = 0; i < v.size(); i++){
            ClientThread client = v.get(i);
            if(client.socket.getPort() != s.getPort()) {
                client.out.write((s.getPort() + ": " + message + "\n").getBytes());
            }
        }
    }
    
    static void leave(Socket s) throws Exception {
        for(int i = 0; i < v.size(); i++){
            ClientThread client = v.get(i);
            client.out.write(("♦ "+s.getPort() + " has left the chat\n").getBytes());
        }
    }
}

