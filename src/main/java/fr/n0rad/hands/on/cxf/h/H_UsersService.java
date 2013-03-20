package fr.n0rad.hands.on.cxf.h;

import fr.n0rad.hands.on.cxf.e.E_UsersResource;
import fr.n0rad.hands.on.cxf.global.User;
import fr.n0rad.hands.on.cxf.global.error.UserNotFoundException;

public class H_UsersService implements E_UsersResource {

    @Override
    public User getDefaultUser() throws UserNotFoundException {
        throw new IllegalStateException("this should not happen!");
    }

}
