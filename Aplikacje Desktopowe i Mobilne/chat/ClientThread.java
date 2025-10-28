import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientThread extends Thread {
    Socket socket;
    InputStream in;
    OutputStream out;
	
    ClientThread(Socket socket)throws IOException{
	this.socket = socket;
	this.in = socket.getInputStream();
	this.out = socket.getOutputStream();
	start();
    }
    
    public void run(){
    try{
            boolean done = false;

            int k;
            StringBuffer sb;

            while(!done){
                sb  =new StringBuffer();
                while((k=in.read())!=-1 && k!='\n') sb.append((char)k);
                String so = sb.toString().trim();
                if(so.equals("/end")){
                    ServerThread.leave(socket);
                    done = true;
                }else{
                    ServerThread.message(socket, so);
                }
            }
    }catch (Exception ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try { if(in != null) in.close(); } catch(IOException e) {}
            try { if(out != null) out.close(); } catch(IOException e) {}
            try { if(socket != null && !socket.isClosed()) socket.close(); } catch(IOException e) {}
            try { ServerThread.removeClient(this); } catch(Exception e) { /* swallow */ }
        }
    }	
}


