package net.n0rad.hands.on.cxf;

import static org.fest.assertions.api.Assertions.assertThat;
import java.util.ArrayList;
import net.n0rad.hands.on.cxf.ab.A_SimpleServer;
import net.n0rad.hands.on.cxf.global.User;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class B_SimpleJsonServerInstanceTest {

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
    public void should_read_json_server_response() throws Exception {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpUriRequest getRequest = new HttpGet("http://127.0.0.1:8080/users/default");
        getRequest.setHeader("Accept", "application/json");
        HttpResponse response = client.execute(getRequest);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);

        User user = new ObjectMapper().readValue(response.getEntity().getContent(), User.class);

        assertThat(user.getFirstname()).isEqualTo("Arnaud");
        assertThat(user.getLastname()).isEqualTo("Lemaire");
    }
}
