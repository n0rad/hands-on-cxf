package net.n0rad.hands.on.cxf.g;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;
import net.n0rad.hands.on.cxf.global.error.MyError;
import org.apache.cxf.jaxrs.client.ResponseExceptionMapper;

public class G_ClientExceptionMapper implements ResponseExceptionMapper<Exception> {

    private static final String SERVER_ERROR = "Error from server";
    private final MessageBodyReader<MyError> reader;

    @SuppressWarnings("unchecked")
    public G_ClientExceptionMapper(@SuppressWarnings("rawtypes") MessageBodyReader jsonReader) {
        this.reader = jsonReader;
    }

    @SuppressWarnings("unchecked")
    public static <T, U> MultivaluedMap<T, U> cast(MultivaluedMap<?, ?> p, Class<T> t, Class<U> u) {
        return (MultivaluedMap<T, U>) p;
    }

    private MyError findError(Response r) {
        String contentType = (String) r.getMetadata().getFirst(HttpHeaders.CONTENT_TYPE);

        ResponseReader reader = new ResponseReader();

        //        String stream = null;
        //        try {
        //            stream = IOUtils.readStringFromStream((InputStream) r.getEntity());
        //        } catch (IllegalStateException | IOException e1) {
        //            // TODO Auto-generated catch block
        //            e1.printStackTrace();
        //        }
        try {
            return reader.readFrom(MyError.class, MyError.class, new Annotation[] {},
                    MediaType.APPLICATION_JSON_TYPE, cast(r.getMetadata(), String.class, String.class),
                    (InputStream) r.getEntity());
        } catch (Exception e) {
            throw new RuntimeException("Unparsable json errorModel content", e);
        }
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
