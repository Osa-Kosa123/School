import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.charset.StandardCharsets;

public class ClientThread extends Thread {
    Socket socket;
    InputStream in;
    OutputStream out;
    String username;
	
    ClientThread(Socket socket)throws IOException{
	this.socket = socket;
	this.in = socket.getInputStream();
	this.out = socket.getOutputStream();
	start();
    }
    
    @Override
    public void run(){
    try{

            boolean done = false;
            int k;
            StringBuffer sb;

            sb = new StringBuffer();
            while((k=in.read())!=-1 && k!='\n') sb.append((char)k);
            String firstLine = sb.toString().trim();
            if(firstLine.startsWith("/user ")){
                username = firstLine.substring(6).trim();
            } else {
                username = "User " + socket.getPort();
            }

            try {
                if (ServerThread.v != null) {
                    synchronized (ServerThread.v) {
                        boolean duplicate = false;
                        for (ClientThread ct : ServerThread.v) {
                            if (ct == null || ct == this) continue;
                            try {
                                if (ct.socket != null && this.socket != null && ct.socket.getPort() == this.socket.getPort()) continue;
                            } catch (Exception ignore) {}
                            if (ct.username != null && ct.username.equals(username)) {
                                duplicate = true;
                                break;
                            }
                        }
                        if (duplicate) {
                            username = username + socket.getPort();
                        }
                    }
                }
            } catch (Exception e) {
                username = "User " + socket.getPort();
                System.out.println("Warning while checking duplicate username: " + e);
            }

            try {
                if (out != null) {
                    out.write(("/username " + username + "\n").getBytes(StandardCharsets.UTF_8));
                    out.flush();
                }
            } catch (IOException e) {
                System.out.println("Error sending assigned username to client " + socket.getPort() + ": " + e);
            }

            ServerThread.join(this);

            while(!done){
                sb = new StringBuffer();
                while((k=in.read())!=-1 && k!='\n') sb.append((char)k);
                String so = sb.toString().trim();
                if(so.equals("/end")){
                    ServerThread.leave(this);
                    done = true;
                }else{
                    ServerThread.message(this, so);
                }
            }
    }catch (Exception e) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if(in != null) in.close(); 
            }catch(IOException e){
            }
            try {
                if(out != null) out.close(); 
            }catch(IOException e){
            }
            try {
                if(socket != null && !socket.isClosed()) socket.close(); 
            }catch(IOException e) {
            }
            try {
                ServerThread.removeClient(this); 
            }catch(Exception e) {
            }
        }
    }
}


