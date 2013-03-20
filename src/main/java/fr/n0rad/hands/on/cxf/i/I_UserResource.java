package fr.n0rad.hands.on.cxf.i;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import fr.n0rad.hands.on.cxf.global.User;

@Path("/users/{id}")
public interface I_UserResource {

    @GET
    User getUser(@PathParam("id") long id);
}
