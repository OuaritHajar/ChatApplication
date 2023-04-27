package APP;
import java.io.IOException;
import java.io.*;
import java.net.Socket;
import java.util.*;


public class ClientHandler extends Thread {
	public static ArrayList<ClientHandler> handlers = new ArrayList<>();
	private Socket client;
	private BufferedReader input;
	private BufferedWriter output;
	private String clientname;
	public ClientHandler(Socket socket){
	    this.client = socket;
	    try{
	        this.input = new BufferedReader(new InputStreamReader(client.getInputStream()));
	        this.output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
	        this.clientname = input.readLine();
	        handlers.add(this);
	        broadcastMessage(clientname+ " est connecté");
	       }
	       catch(IOException e){
	    	   closeEverything(client,input,output);
	       }
	    
    }
	public void run(){
	    String received;
	    while(client.isConnected()) {
	    	try {
	    		received = input.readLine();
		 	    broadcastMessage(received);
	    	}catch(Exception e){
	    		closeEverything(client,input,output);
	    		break;
	        }
	    	
	    }
	}
	public void broadcastMessage(String messageToSend) {
		for(ClientHandler handler : handlers) {
			try {
			if(!handler.clientname.equals(clientname)) {
				handler.output.write(messageToSend);
				handler.output.newLine();
				handler.output.flush();
				}}catch(Exception e){
		    	   closeEverything(client,input,output);
		        }
		}
	}
	public void removeClientHandler() {
		handlers.remove(this);
		broadcastMessage(clientname+"a quitté la convestion");
	}
	public void closeEverything(Socket client,BufferedReader input,BufferedWriter output) {
		removeClientHandler();
		try {
			if(client!=null)
			   {
			     client.close();
			   }
			if(input!=null)
			  {
			    client.close();
			  }
			if(output!=null)
			  {
			    client.close();
			  }
		}catch(IOException e){
			e.printStackTrace();
	    }
		
	}
	

}
