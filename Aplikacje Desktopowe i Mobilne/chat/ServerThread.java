import java.io.IOException;
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
                Thread.sleep(100);
            }
        } catch(InterruptedException e) {
            System.out.println("ServerThread error: " + e);
        }
    }

    static void removeClient(ClientThread client) {
        if (v == null || client == null) return;
        synchronized(v) {
            if(v.remove(client)){
                try { if(client.in != null) client.in.close(); } catch(IOException e) {}
                try { if(client.out != null) client.out.close(); } catch(IOException e) {}
                try { if(client.socket != null && !client.socket.isClosed()) client.socket.close(); } catch(IOException e) {}
                try { System.out.println("Removed client: " + client.socket.getPort()); } catch(Exception e) { System.out.println("Removed client"); }
            }
        }
    }

    static void join(Socket s) throws Exception {
        if (v == null || s == null) return;
        synchronized(v) {
            for(int i = 0; i < v.size(); i++){
                ClientThread client = v.get(i);
                try{
                    client.out.write(("♦ "+s.getPort() + " has joined the chat\n").getBytes());
                }catch(IOException ioe){
                    System.out.println("Error writing join to client " + client.socket.getPort() + ": " + ioe);
                    removeClient(client);
                    i--; 
                }
            }
        }
    }
    
    static void message(Socket s, String message) throws Exception {
        if (v == null || s == null) return;
        synchronized(v) {
            for(int i = 0; i < v.size(); i++){
                ClientThread client = v.get(i);
                if(client == null) continue;
                try{
                    if(client.socket.getPort() != s.getPort()) {
                        client.out.write((s.getPort() + ": " + message + "\n").getBytes());
                    }
                }catch(IOException ioe){
                    System.out.println("Error writing message to client " + client.socket.getPort() + ": " + ioe);
                    removeClient(client);
                    i--;
                }
            }
        }
    }
    
    static void leave(Socket s) throws Exception {
        if (v == null || s == null) return;
        synchronized(v) {
            for(int i = 0; i < v.size(); i++){
                ClientThread client = v.get(i);
                try{
                    client.out.write(("♦ "+s.getPort() + " has left the chat\n").getBytes());
                }catch(IOException ioe){
                    System.out.println("Error writing leave to client " + client.socket.getPort() + ": " + ioe);
                    removeClient(client);
                    i--;
                }
            }
        }
    }
}

