import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Serwer {
    public static void main(String[] args) throws Exception {
        Vector<ClientThread> v = new Vector<>();
        
        try{
            ServerSocket s = new ServerSocket(2011);
            System.out.println("Serwer Run...");
            ServerThread sThread = new ServerThread(v);
                         
            while(true){
                Socket incoming = s.accept();
                ClientThread cThread = new ClientThread(incoming);
                v.addElement(cThread);
                System.out.println(incoming.getPort()+" has connected.");
                ServerThread.join(cThread.socket);
            }	
        }catch(IOException e){
        }
    }
}