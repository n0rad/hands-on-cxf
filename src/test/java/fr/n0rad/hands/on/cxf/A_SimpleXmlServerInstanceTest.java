package fr.n0rad.hands.on.cxf;

import static org.fest.assertions.api.Assertions.assertThat;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Before;
import org.junit.Test;
import fr.n0rad.hands.on.cxf.ab.A_SimpleServer;
import fr.n0rad.hands.on.cxf.global.Address;
import fr.n0rad.hands.on.cxf.global.User;

public class A_SimpleXmlServerInstanceTest {

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
        resources.add(new A_SimpleServer());
        sf.setServiceBeans(resources);
        sf.setAddress("http://127.0.0.1:8080");
        sf.create();
    }

    @Test
    public void should_read_xml_server_response() throws Exception {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpUriRequest getRequest = new HttpGet("http://127.0.0.1:8080/users/default");

        HttpResponse response = client.execute(getRequest);

        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);

        JAXBContext jc = JAXBContext.newInstance(User.class, Address.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        User user = (User) unmarshaller.unmarshal(response.getEntity().getContent());

        assertThat(user.getFirstname()).isEqualTo("Arnaud");
        assertThat(user.getLastname()).isEqualTo("Lemaire");
    }

}
