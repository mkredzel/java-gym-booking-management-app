
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

    static final int PORT = 8888;
    static Socket socket;
    static ServerSocket serverSocket;
    
    /**
     * Server is able to serve multiple clients by using Threads 
     * from HandleClient class.
     * 
     * @param args 
     */
    public static void main(String[] args) {
            try {
                serverSocket = new ServerSocket(PORT);
                while(true){
                    socket = MyServer.serverSocket.accept();
                    new Thread(new HandleClient(socket)).start();
                }
            } catch (IOException ex) {
            System.err.println("Server Error: " + ex.getMessage());
            }
    }
}