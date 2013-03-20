package fr.n0rad.hands.on.cxf;

import java.util.ArrayList;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import fr.n0rad.hands.on.cxf.e.E_UserNotFoundExceptionMapper;
import fr.n0rad.hands.on.cxf.e.E_UsersResource;
import fr.n0rad.hands.on.cxf.e.E_UsersService;
import fr.n0rad.hands.on.cxf.f.F_ClientExceptionMapper;
import fr.n0rad.hands.on.cxf.global.error.UserNotFoundException;

public class F_CustomXmlExceptionManagementTest {

    @Before
    public void initServer() {
        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        ArrayList<Object> resources = new ArrayList<>();
        resources.add(new E_UsersService());
        sf.setProvider(new E_UserNotFoundExceptionMapper());
        sf.setServiceBeans(resources);
        sf.setAddress("http://127.0.0.1:8080");
        sf.create();
    }

    @Test(expected = UserNotFoundException.class)
    public void should_receive_custom_exception_in_xml() throws Exception {
        JAXRSClientFactoryBean cf = new JAXRSClientFactoryBean();
        cf.setAddress("http://127.0.0.1:8080");
        cf.setProvider(new JacksonJaxbJsonProvider());
        cf.setProvider(new F_ClientExceptionMapper());
        cf.setResourceClass(E_UsersResource.class);
        BindingFactoryManager manager = cf.getBus().getExtension(BindingFactoryManager.class);
        JAXRSBindingFactory factory = new JAXRSBindingFactory();
        factory.setBus(cf.getBus());
        manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, factory);
        E_UsersResource service = cf.create(E_UsersResource.class);

        service.getDefaultUser();
    }

}
