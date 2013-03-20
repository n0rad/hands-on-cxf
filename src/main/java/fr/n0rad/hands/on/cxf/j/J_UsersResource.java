package fr.n0rad.hands.on.cxf.j;

import javax.validation.Valid;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import fr.n0rad.hands.on.cxf.global.User;

@Path("/users")
public interface J_UsersResource {

    @PUT
    void createUser(@Valid User user);

}
