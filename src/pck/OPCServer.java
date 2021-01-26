package pck;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.Locale;

import com.prosysopc.ua.ApplicationIdentity;
import com.prosysopc.ua.PkiFileBasedCertificateValidator;
import com.prosysopc.ua.SecureIdentityException;
import com.prosysopc.ua.StatusException;
import com.prosysopc.ua.UaAddress;
import com.prosysopc.ua.UaApplication;
import com.prosysopc.ua.UaApplication.Protocol;
import com.prosysopc.ua.nodes.UaObjectType;
import com.prosysopc.ua.nodes.UaType;
import com.prosysopc.ua.server.NodeManagerRoot;
import com.prosysopc.ua.server.NodeManagerUaNode;
import com.prosysopc.ua.server.UaServer;
import com.prosysopc.ua.server.UaServerException;
import com.prosysopc.ua.server.nodes.PlainVariable;
import com.prosysopc.ua.server.nodes.UaObjectTypeNode;
import com.prosysopc.ua.types.opcua.server.FolderTypeNode;

import org.opcfoundation.ua.builtintypes.LocalizedText;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.ApplicationDescription;
import org.opcfoundation.ua.core.ApplicationType;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.core.UserTokenPolicy;
import org.opcfoundation.ua.transport.https.HttpsSettings;
import org.opcfoundation.ua.transport.security.KeyPair;
import org.opcfoundation.ua.transport.security.SecurityMode;
import org.opcfoundation.ua.utils.EndpointUtil;

public class OPCServer {
	
	//  Credentials.
	public String server_name = "no_name";
	public String password = "no_password";
	
	//  Application details.
	private ApplicationIdentity identity;
	private ApplicationDescription applicationDescription;
	
	//  Access related stuff.
	private PkiFileBasedCertificateValidator validator;
	private File privatePath;
	private KeyPair issuerCertificate;
	private String hostName;
	private UaServer server;
	
	public OPCServer(String server_name, String password) {
		this.server_name = server_name;
		this.password = password;
	}
	
    private void setServerCertificates() throws SecureIdentityException, IOException
    {
        System.out.println("Creating server certificates");
		    
	    identity = ApplicationIdentity.loadOrCreateCertificate(
	        applicationDescription, this.server_name, this.password,
		    new File(validator.getBaseDir(), "configs"), true, new String[0]);
		    
		privatePath = new File(validator.getBaseDir(), "configs");
		issuerCertificate = ApplicationIdentity.loadOrCreateIssuerCertificate(
				"Proiect_CAIFAOS_certificate_123", privatePath, this.password, 3650, true);
		    
		hostName = ApplicationIdentity.getActualHostName();
		identity.setHttpsCertificate(
			ApplicationIdentity.loadOrCreateHttpsCertificate(
		        applicationDescription, hostName, this.password,
		        issuerCertificate, privatePath, true));
    }
    
    public void initializeServer() throws SecureIdentityException,IOException, UaServerException, URISyntaxException
    {
        server = new UaServer();
    	validator = new PkiFileBasedCertificateValidator();
    	server.setCertificateValidator(validator);
    	
    	System.out.println("Setting application description");
    	applicationDescription = new ApplicationDescription();
    	applicationDescription.setApplicationName(
    			new LocalizedText("Server_OPCUA", Locale.ENGLISH));
    	applicationDescription.setApplicationUri(
    			"urn:localhost:UA:ConsoleServer");
    	applicationDescription.setProductUri("urn:localhost:UA:ConsoleServer");
    	applicationDescription.setApplicationType(ApplicationType.Server);
    	    
	    setServerCertificates();
	    System.out.println("Setting application identity");
	    server.setApplicationIdentity(identity);
	    
	    System.out.println("Setting server ports for OpcTcp and Https");
	    server.setPort(UaApplication.Protocol.OpcTcp, 52520);
	    server.setPort(UaApplication.Protocol.Https, 52443);
    	    
	    server.setServerName("OPCUA/Server_OPCUA");
	    server.setBindAddresses(EndpointUtil.getInetAddresses());
	    server.setSecurityModes(SecurityMode.ALL);
	    server.getHttpsSettings().setHttpsSecurityPolicies(
	    		org.opcfoundation.ua.transport.security.HttpsSecurityPolicy.ALL);
	    server.getHttpsSettings().setCertificateValidator(validator);
	    server.addUserTokenPolicy(UserTokenPolicy.ANONYMOUS);
	    server.addUserTokenPolicy(UserTokenPolicy.SECURE_USERNAME_PASSWORD);
    	    
	    UaAddress discoveryAddress = new UaAddress("opc.tcp://localhost:4840");
	    server.setDiscoveryServerAddress(discoveryAddress);
	    
	    System.out.println("Initializing server...");
	    server.init();
	    System.out.println("Server initialized!");
	    
	    System.out.println("Staring server...");
	    server.start();
	    System.out.println("Server started!");
    }
    
    protected void stopServer()
    {
      server.shutdown(
    		  5, new LocalizedText("Server is shutting down!", Locale.ENGLISH));
    }

}
