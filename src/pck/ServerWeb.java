package pck;

import org.apache.cxf.frontend.ServerFactoryBean;

public class ServerWeb {

	  protected ServerWeb() throws Exception {
	      ServerFactoryBean factory = new ServerFactoryBean();
	      factory.setServiceClass(WebServiceInterface.class);
	      factory.setAddress("http://localhost:5000/Hello");
	      factory.setServiceBean(new WebServiceInterfaceImpl());
	      factory.create();
	   }
}
