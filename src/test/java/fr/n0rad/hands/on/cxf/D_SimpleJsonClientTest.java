package fr.n0rad.hands.on.cxf;

import static org.fest.assertions.api.Assertions.assertThat;
import java.util.ArrayList;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import fr.n0rad.hands.on.cxf.ab.A_SimpleServer;
import fr.n0rad.hands.on.cxf.cd.C_SimpleServer;
import fr.n0rad.hands.on.cxf.global.User;

public class D_SimpleJsonClientTest {

    @Before
    public void initServer() {
        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        ArrayList<Object> resources = new ArrayList<>();
        resources.add(new A_SimpleServer());
        sf.setProvider(new JacksonJaxbJsonProvider());
        sf.setServiceBeans(resources);
        sf.setAddress("http://127.0.0.1:8080");
        sf.create();
    }

    @Test
    public void should_read_server_json_with_cxf_client() throws Exception {
        JAXRSClientFactoryBean cf = new JAXRSClientFactoryBean();
        cf.setAddress("http://127.0.0.1:8080");
        cf.setProvider(new JacksonJaxbJsonProvider());
        cf.setResourceClass(C_SimpleServer.class);
        BindingFactoryManager manager = cf.getBus().getExtension(BindingFactoryManager.class);
        JAXRSBindingFactory factory = new JAXRSBindingFactory();
        factory.setBus(cf.getBus());
        manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, factory);
        C_SimpleServer service = cf.create(C_SimpleServer.class);
        Client c = WebClient.client(service);
        c.accept(MediaType.APPLICATION_JSON_TYPE).type(MediaType.APPLICATION_JSON_TYPE);

        User user = service.getDefaultUser();

        assertThat(user.getFirstname()).isEqualTo("Arnaud");
        assertThat(user.getLastname()).isEqualTo("Lemaire");
    }
}
