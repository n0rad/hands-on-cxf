package fr.n0rad.hands.on.cxf.i;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import fr.n0rad.hands.on.cxf.global.User;

@Path("/users")
public interface I_UsersResource {

    @GET
    @Path("/default")
    User getDefaultUser();

}
