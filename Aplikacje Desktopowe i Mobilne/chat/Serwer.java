import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class Serwer {
	public static void main(String[] args) {
                Vector<ServerThread> v = new Vector<ServerThread>();
		try{
			
			ServerSocket s = new ServerSocket(2011);
			System.out.println("Serwer Run...");
			
			while(true){
				Socket incoming = s.accept();
				
				ServerThread thread = new ServerThread(incoming);
                                v.addElement(thread);
				
			}
			//s.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
