package fr.n0rad.hands.on.cxf;

import static java.util.Arrays.asList;
import static org.fest.assertions.api.Assertions.assertThat;
import net.awired.ajsl.ws.rest.RestContext;
import org.junit.Test;
import fr.n0rad.hands.on.cxf.global.User;
import fr.n0rad.hands.on.cxf.i.I_UserResource;
import fr.n0rad.hands.on.cxf.i.I_UserService;
import fr.n0rad.hands.on.cxf.i.I_UsersResource;
import fr.n0rad.hands.on.cxf.i.I_UsersService;

public class I_RegexpTest {

    private String url = "http://127.0.0.1:8080";
    private RestContext context = new RestContext();

    @Test
    public void should_call_valid_method_when_url_colision() throws Exception {
        context.prepareServer(url, asList(new I_UsersService(), new I_UserService()));

        I_UsersResource users = context.prepareClient(I_UsersResource.class, url, null, false);
        I_UserResource user = context.prepareClient(I_UserResource.class, url, null, false);

        assertThat(users.getDefaultUser()).isEqualsToByComparingFields(new User("Arnaud", "Lemaire"));
        assertThat(user.getUser(42)).isEqualsToByComparingFields(new User("Guillaume", "Balaine"));
    }
}
