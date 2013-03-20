package fr.n0rad.hands.on.cxf.g;

import java.io.InputStream;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.ResponseExceptionMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.n0rad.hands.on.cxf.global.error.MyError;

public class G_ClientExceptionMapper implements ResponseExceptionMapper<Exception> {

    private static final String SERVER_ERROR = "Error from server";
    ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public static <T, U> MultivaluedMap<T, U> cast(MultivaluedMap<?, ?> p, Class<T> t, Class<U> u) {
        return (MultivaluedMap<T, U>) p;
    }

    private MyError findError(Response r) {
        MyError readValue;
        try {
            readValue = mapper.readValue((InputStream) r.getEntity(), MyError.class);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot parse Error response", e);
        }
        return readValue;
    }

    @Override
    public Exception fromResponse(Response r) {
        MyError error = findError(r);
        Exception exception = null;
        if (error.getErrorClass() != null) {
            try {
                exception = error.getErrorClass().getConstructor(String.class)
                        .newInstance(error.getMessage() == null ? null : error.getMessage());
            } catch (Exception e) {
                throw new RuntimeException("Cannot Create Exception from Error : " + error, e);
            }
        } else {
            exception = new RuntimeException(error.getMessage() == null ? SERVER_ERROR : error.getMessage());
        }
        return exception;
    }
}
