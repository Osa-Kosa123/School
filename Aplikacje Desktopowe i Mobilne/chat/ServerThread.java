
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
public class ServerThread {
    static Vector v;
    InputStream in;
    OutputStream out;
    
    ServerThread(Vector tv)throws IOException{
        v = tv;
    }
    public void run(){
	try{
            int i = 1;
	}catch(Exception e){
            int i = 0;
	}
    }

    static void start(Socket s)throws Exception{
        for(int i=0; i<v.size(); i++){
            Socket vs = v.get(i).socket;
            if(vs.getPort() != s.getPort()){
                v.get(i)
            }
        }
    }
}
