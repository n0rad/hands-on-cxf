package net.n0rad.hands.on.cxf.ab;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import net.n0rad.hands.on.cxf.global.User;

@Path("/users")
public class A_SimpleServer {

    @GET
    @Path("/default")
    public User getDefaultUser() {
        return new User("Arnaud", "Lemaire");
    }

}
