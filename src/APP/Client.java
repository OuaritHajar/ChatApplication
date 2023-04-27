package APP;
import java.io.IOException;
import java.io.*;
import java.net.Socket;
import java.util.*;
public class Client {
	private static Socket socket;
    private static final int PORT = 1255;
    private static BufferedReader input;
    private static BufferedWriter output;
    private static String clientname;
    
    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", PORT);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez votre nom d'utilisateur : ");
            clientname = scanner.nextLine();
            output.write(clientname);
            output.newLine();
            output.flush();
            System.out.println("Connecté au serveur.");
            Thread readThread = new Thread(new ReadThread());
            readThread.start();
            while (true) {
                String message = scanner.nextLine();
                output.write(message);
                output.newLine();
                output.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                	input.close();
                }
                if (output != null) {
                	output.close();
                }
                if (socket != null) {
                	socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static class ReadThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    String message = input.readLine();
                    System.out.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
