package pck;

import com.prosysopc.ua.ApplicationIdentity;
import com.prosysopc.ua.PkiFileBasedCertificateValidator;
import com.prosysopc.ua.SecureIdentityException;
import com.prosysopc.ua.ServiceException;
import com.prosysopc.ua.StatusException;
import com.prosysopc.ua.UserIdentity;
import com.prosysopc.ua.client.AddressSpace;
import com.prosysopc.ua.client.ConnectException;
import com.prosysopc.ua.client.InvalidServerEndpointException;
import com.prosysopc.ua.client.UaClient;
import com.prosysopc.ua.nodes.UaNode;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.Locale;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.ApplicationType;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.transport.https.HttpsSettings;
import org.opcfoundation.ua.transport.security.HttpsSecurityPolicy;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.SecurityMode;

public class Client {
	
	 private final String CLIENT_APPLICATION_NAME = "Client_OPCUA";
	  
	 private final String SERVER_URI = "opc.tcp://localhost:52520/OPCUA/Server_OPCUA";
	 private UaClient serviceConsumer;
	 private PkiFileBasedCertificateValidator appValidator;
	 private ApplicationDescription appDescription;
     private ApplicationIdentity appIdentity;
     private File privateCertificatePath;
	 private KeyPair issuerCertificate;
	 private String hostName;
	  
	 public void initializeClient()
			    throws URISyntaxException, SecureIdentityException, IOException, InvalidServerEndpointException, ConnectException, ServiceException, StatusException, com.prosysopc.ua.client.AddressSpaceException
			  {
			    System.out.println("CLient connecting to opc.tcp://localhost:52520/OPCUA/Server_OPCUA");
			    UaClient serviceConsumer = new UaClient("opc.tcp://localhost:52520/OPCUA/Server_OPCUA");
			    
			    appValidator = new PkiFileBasedCertificateValidator();
			    serviceConsumer.setCertificateValidator(appValidator);
			    
			    appDescription = new ApplicationDescription();
			    appDescription.setApplicationName(new org.opcfoundation.ua.builtintypes.LocalizedText("Client_OPCUA", Locale.ENGLISH));
			    appDescription.setApplicationUri("urn:localhost:UA:ConsoleClient");
			    appDescription.setProductUri("urn:localhost:UA:ConsoleClient");
			    appDescription.setApplicationType(ApplicationType.Client);
			    
			    appIdentity = ApplicationIdentity.loadOrCreateCertificate(appDescription, "Proiect_CAIFAOS_123", "pass123", 
			      new File(appValidator.getBaseDir(), "configs"), false, new String[0]);
			    
			    privateCertificatePath = new File(appValidator.getBaseDir(), "configs");
			    issuerCertificate = ApplicationIdentity.loadOrCreateIssuerCertificate("Proiect_CAIFAOS_certificate_123", 
			      privateCertificatePath, "pass123", 3650, false);
			    
			    hostName = InetAddress.getLocalHost().getHostName();
			    
			    appIdentity.setHttpsCertificate(ApplicationIdentity.loadOrCreateHttpsCertificate(appDescription, hostName, 
			      "pass123", issuerCertificate, privateCertificatePath, false));
			    
			    serviceConsumer.setSecurityMode(SecurityMode.NONE);
			    
			    serviceConsumer.getHttpsSettings().setHttpsSecurityPolicies(HttpsSecurityPolicy.ALL);
			    
			    serviceConsumer.getHttpsSettings().setCertificateValidator(appValidator);
			    serviceConsumer.getHttpsSettings()
			      .setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			    
			    serviceConsumer.setUserIdentity(new UserIdentity("OPC_Client", "opcua"));
			    
			    serviceConsumer.setApplicationIdentity(appIdentity);
			    
			    serviceConsumer.connect();
			    System.out.println("Client connected!");
			  }

}
