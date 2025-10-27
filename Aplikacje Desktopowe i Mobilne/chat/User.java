import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class User {
    public static void main(String[] args) {
	try{
            BufferedReader keyboardStream = new BufferedReader(new InputStreamReader(System.in));
            Socket socket = new Socket("localhost", 2011);
            
            InputStream inStream = socket.getInputStream();
            OutputStream outStream = socket.getOutputStream();
			
            boolean done = false;
            
            Thread readerThread = new Thread(() -> {
                try (BufferedReader serverIn = new BufferedReader(new InputStreamReader(inStream))) {
                    String line;
                    while ((line = serverIn.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                }
            });
            readerThread.setDaemon(true);
            readerThread.start();
            while(!done){	
		String ops = keyboardStream.readLine();
		outStream.write((ops+"\n").getBytes());
		if(ops.equals("/end")){
                    done = true;
		}else{
                    done = false;
		}	
            }
            inStream.close();
            outStream.close();
            socket.close();				
           }catch(IOException e){	
        }
    }
}
