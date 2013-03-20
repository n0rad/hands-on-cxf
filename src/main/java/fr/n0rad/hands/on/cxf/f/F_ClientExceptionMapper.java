package fr.n0rad.hands.on.cxf.f;

import java.io.InputStream;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.cxf.jaxrs.client.ResponseExceptionMapper;
import fr.n0rad.hands.on.cxf.global.error.MyError;

public class F_ClientExceptionMapper implements ResponseExceptionMapper<Exception> {

    private static final String SERVER_ERROR = "Error from server";

    private MyError findError(Response r) {
        try {
            JAXBContext jc = JAXBContext.newInstance(MyError.class);
            Unmarshaller u = jc.createUnmarshaller();
            return (MyError) u.unmarshal((InputStream) r.getEntity());
        } catch (JAXBException e) {
            throw new RuntimeException("Unparsable xml error content", e);
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
