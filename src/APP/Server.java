package APP;
import java.io.*;
import java.net.*;
public class Server {
	private static ServerSocket serverSocket;
	private static final int PORT = 1255;
	public static void main(String[] args)
	throws IOException{
	    try{
	        serverSocket = new ServerSocket(PORT);
	       }
	       catch (IOException ioEx){
	             System.out.println("\nImpossible de configurer le port!");
	             System.exit(1);
	       }
	       do{
	          Socket client = serverSocket.accept();
	          System.out.println("\n nouveau client accepté.\n");
	          ClientHandler handler = new ClientHandler(client);
	          Thread thread = new Thread(handler);
	          thread.start();
	         }while (true);
	}
	

}
