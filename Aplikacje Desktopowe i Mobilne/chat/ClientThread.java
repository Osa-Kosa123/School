import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;


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
			
                        out.write((socket.toString()+"\0").getBytes());
			while(!done){
				sb  =new StringBuffer();
				while((k=in.read())!=-1 && k!='\0') sb.append((char)k);
                                String so = sb.toString().trim();
				if(so.equals("/end")){
                                    done = true;
                                }else{
                                    ServerThread.start(socket);
                                    out.write((socket.getPort()+": "+so+"\0").getBytes());
                                }
			}
			in.close();
			out.close();
			socket.close();
		}catch(IOException e){
			System.out.println(e);
		}
	}
	
}