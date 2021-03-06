package fr.n0rad.hands.on.cxf.e;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import fr.n0rad.hands.on.cxf.global.User;
import fr.n0rad.hands.on.cxf.global.error.UserNotFoundException;

@Path("/users")
public interface E_UsersResource {

    @GET
    public User getDefaultUser() throws UserNotFoundException;

}
