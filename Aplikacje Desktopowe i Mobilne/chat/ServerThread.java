import java.io.IOException;
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
    
    @Override
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
                try {
                    if(client.in != null) client.in.close(); 
                }catch(IOException e){
                }
                try {
                    if(client.out != null) client.out.close(); 
                }catch(IOException e){
                }
                try {
                    if(client.socket != null && !client.socket.isClosed()) client.socket.close(); 
                }catch(IOException e){
                }
                try {
                    System.out.println("Removed client: " + (client.username != null ? client.username : client.socket.getPort())); 
                }catch(Exception e){
                    System.out.println("Removed client");
                }
                try {
                    broadcastUserList();
                }catch(Exception e){
                }
            }
        }
    }
    static void join(ClientThread c) throws Exception {
        if (v == null || c == null) return;
        synchronized(v) {
            for(int i = 0; i < v.size(); i++){
                ClientThread client = v.get(i);
                try{
                    client.out.write((">>> "+(c.username != null ? c.username : c.socket.getPort()) + " has joined the chat\n").getBytes());
                }catch(IOException ioe){
                    System.out.println("Error writing join to client " + client.socket.getPort() + ": " + ioe);
                    removeClient(client);
                    i--; 
                }
            }
            broadcastUserList();
        }
    }
    
    static void message(ClientThread sender, String message) throws Exception {
        if (v == null || sender == null) return;
        if (message.startsWith("/private ")) {
            String[] parts = message.split(" ", 3);
            if (parts.length >= 3) {
                String reciver = parts[1];
                String msgBody = parts[2];
                ClientThread reciverClient = null;
                ClientThread senderClient = null;
                synchronized(v) {
                    for (ClientThread client : v) {
                        if (client != null && client.username != null) {
                            if (client.username.equals(reciver)) reciverClient = client;
                            if (client.username.equals(sender.username)) senderClient = client;
                        }
                    }
                    if (reciverClient != null) {
                        try {
                            reciverClient.out.write(("/private " + sender.username + " " + reciver + " " + msgBody + "\n").getBytes());
                        } catch(IOException e) {
                            System.out.println("Error writing private message to client " + reciverClient.socket.getPort() + ": " + e);
                            removeClient(reciverClient);
                        }
                    }
                    if (senderClient != null && senderClient != reciverClient) {
                        try {
                            senderClient.out.write(("/private " + sender.username + " " + reciver + " " + msgBody + "\n").getBytes());
                        } catch(IOException e) {
                            System.out.println("Error writing private message echo to client " + senderClient.socket.getPort() + ": " + e);
                            removeClient(senderClient);
                        }
                    }
                }
            }
            return;
        }
        synchronized(v) {
            for(int i = 0; i < v.size(); i++){
                ClientThread client = v.get(i);
                if(client == null) continue;
                try{
                    client.out.write(((sender.username != null ? sender.username : sender.socket.getPort()) + ": " + message + "\n").getBytes());
                }catch(IOException e){
                    System.out.println("Error writing message to client " + client.socket.getPort() + ": " + e);
                    removeClient(client);
                    i--;
                }
            }
        }
    }
    
    static void leave(ClientThread c) throws Exception {
        if (v == null || c == null) return;
        synchronized(v) {
            for(int i = 0; i < v.size(); i++){
                ClientThread client = v.get(i);
                try{
                    client.out.write((">>> "+(c.username != null ? c.username : c.socket.getPort()) + " has left the chat\n").getBytes());
                }catch(IOException e){
                    System.out.println("Error writing leave to client " + client.socket.getPort() + ": " + e);
                    removeClient(client);
                    i--;
                }
            }
            broadcastUserList();
        }
    }

    static void broadcastUserList() {
        if (v == null) return;
        synchronized(v) {
            StringBuilder sb = new StringBuilder();
            sb.append("/users ");
            boolean first = true;
            for (int i = 0; i < v.size(); i++) {
                ClientThread client = v.get(i);
                if (client == null) continue;
                String name = client.username;
                if (name == null || name.trim().isEmpty()) {
                    try { name = String.valueOf(client.socket.getPort()); } catch(Exception e) { name = "unknown"; }
                }
                if (!first) sb.append(",");
                sb.append(name);
                first = false;
            }
            sb.append("\n");

            for (int i = 0; i < v.size(); i++) {
                ClientThread client = v.get(i);
                if (client == null) continue;
                try {
                    client.out.write(sb.toString().getBytes());
                } catch(IOException e) {
                    System.out.println("Error writing users list to client " + client.socket.getPort() + ": " + e);
                    removeClient(client);
                    i--;
                }
            }
        }
    }
}