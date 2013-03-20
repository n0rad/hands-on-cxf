package fr.n0rad.hands.on.cxf.e;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fr.n0rad.hands.on.cxf.global.error.MyError;
import fr.n0rad.hands.on.cxf.global.error.UserNotFoundException;

@Provider
public class E_UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException> {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String RESPOND = "Respond an Exception";

    @Override
    public Response toResponse(UserNotFoundException exception) {
        if (log.isDebugEnabled()) {
            log.debug(RESPOND, exception);
        } else {
            log.info(RESPOND, exception.getMessage());
        }

        MyError error = new MyError();
        error.setMessage(exception.getMessage());
        error.setErrorClass(exception.getClass());
        return Response.status(400).entity(error).build();
    }
}
