package net.n0rad.hands.on.cxf;

import java.util.ArrayList;
import java.util.Arrays;
import javax.ws.rs.core.MediaType;
import net.n0rad.hands.on.cxf.e.E_UserNotFoundExceptionMapper;
import net.n0rad.hands.on.cxf.e.E_UsersResource;
import net.n0rad.hands.on.cxf.e.E_UsersService;
import net.n0rad.hands.on.cxf.g.G_ClientExceptionMapper;
import net.n0rad.hands.on.cxf.global.error.UserNotFoundException;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class G_CustomJsonExceptionManagementTest {

    @Before
    public void initServer() {
        LoggingInInterceptor inLogger;
        LoggingOutInterceptor outLogger;
        inLogger = new LoggingInInterceptor();
        inLogger.setPrettyLogging(true);
        inLogger.setOutputLocation("<stdout>");
        outLogger = new LoggingOutInterceptor();
        outLogger.setPrettyLogging(true);
        outLogger.setOutputLocation("<stderr>");

        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        ArrayList<Object> resources = new ArrayList<>();
        resources.add(new E_UsersService());
        sf.setProviders(Arrays.asList(inLogger, outLogger, new E_UserNotFoundExceptionMapper(),
                new JacksonJaxbJsonProvider()));
        sf.setServiceBeans(resources);
        sf.setAddress("http://127.0.0.1:8080");
        sf.create();
    }

    @Test(expected = UserNotFoundException.class)
    public void should_receive_custom_exception_in_json() throws Exception {
        LoggingInInterceptor inLogger;
        LoggingOutInterceptor outLogger;
        inLogger = new LoggingInInterceptor();
        inLogger.setPrettyLogging(true);
        inLogger.setOutputLocation("<stdout>");
        outLogger = new LoggingOutInterceptor();
        outLogger.setPrettyLogging(true);
        outLogger.setOutputLocation("<stderr>");

        JAXRSClientFactoryBean cf = new JAXRSClientFactoryBean();
        cf.setAddress("http://127.0.0.1:8080");
        JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
        cf.setProviders(Arrays.asList(inLogger, outLogger, jsonProvider, new G_ClientExceptionMapper(jsonProvider)));
        cf.setResourceClass(E_UsersResource.class);
        BindingFactoryManager manager = cf.getBus().getExtension(BindingFactoryManager.class);
        JAXRSBindingFactory factory = new JAXRSBindingFactory();
        factory.setBus(cf.getBus());
        manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, factory);
        E_UsersResource service = cf.create(E_UsersResource.class);
        Client c = WebClient.client(service);
        c.accept(MediaType.APPLICATION_JSON_TYPE).type(MediaType.APPLICATION_JSON_TYPE);

        service.getDefaultUser();
    }

}
