package net.n0rad.hands.on.cxf.global.error;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "my-error")
@XmlAccessorType(XmlAccessType.FIELD)
public class MyError {

    private Class<? extends Exception> errorClass;

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Class<? extends Exception> getErrorClass() {
        return errorClass;
    }

    public void setErrorClass(Class<? extends Exception> errorClass) {
        this.errorClass = errorClass;
    }

}
