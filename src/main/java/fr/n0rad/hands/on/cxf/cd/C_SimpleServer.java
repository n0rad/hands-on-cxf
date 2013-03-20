package fr.n0rad.hands.on.cxf.cd;

import java.text.ParseException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import fr.n0rad.hands.on.cxf.global.User;

@Path("/users")
public interface C_SimpleServer {

    @GET
    @Path("/default")
    User getDefaultUser() throws ParseException;

}
