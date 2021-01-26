package pck;

import java.io.IOException;
import java.net.URISyntaxException;

import com.prosysopc.ua.SecureIdentityException;
import com.prosysopc.ua.ServiceException;
import com.prosysopc.ua.StatusException;
import com.prosysopc.ua.client.AddressSpaceException;
import com.prosysopc.ua.client.ConnectException;
import com.prosysopc.ua.client.InvalidServerEndpointException;
import com.prosysopc.ua.server.UaServerException;

public class Main {

	public static void main(String[] args) throws Exception {
	    //  TODO Auto-generated method stub
		//  Instantiate server object.
       // OPCServer server = new OPCServer("Proiect_CAIFAOS_123", "pass123");
       // server.initializeServer();
        
        //Client client = new Client();
		//client.initializeClient();
     
		ServerWeb serverWeb = new ServerWeb();
	    System.out.println("Listening on port 5000 ...");
	    Thread.sleep(5 * 60 * 1000);
	    System.out.println("Server exiting ...");
	    System.exit(0);
		  
       // server.stopServer();
	}
}
