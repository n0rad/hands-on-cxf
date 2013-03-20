package fr.n0rad.hands.on.cxf;

import static org.fest.assertions.api.Assertions.assertThat;
import java.util.ArrayList;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.junit.Before;
import org.junit.Test;
import fr.n0rad.hands.on.cxf.cd.C_SimpleServer;
import fr.n0rad.hands.on.cxf.cd.C_SimpleServerImpl;
import fr.n0rad.hands.on.cxf.global.User;

public class C_SimpleXmlClientTest {

    @Before
    public void initServer() {
        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        ArrayList<Object> resources = new ArrayList<>();
        resources.add(new C_SimpleServerImpl());
        sf.setServiceBeans(resources);
        sf.setAddress("http://127.0.0.1:8080");
        sf.create();
    }

    @Test
    public void should_read_server_xml_with_cxf_client() throws Exception {
        JAXRSClientFactoryBean cf = new JAXRSClientFactoryBean();
        cf.setAddress("http://127.0.0.1:8080");
        //        prepareFactory(url, cf);
        cf.setResourceClass(C_SimpleServer.class);
        BindingFactoryManager manager = cf.getBus().getExtension(BindingFactoryManager.class);
        JAXRSBindingFactory factory = new JAXRSBindingFactory();
        factory.setBus(cf.getBus());
        manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, factory);
        C_SimpleServer service = cf.create(C_SimpleServer.class);

        User user = service.getDefaultUser();

        assertThat(user.getFirstname()).isEqualTo("Arnaud");
        assertThat(user.getLastname()).isEqualTo("Lemaire");
    }
}
