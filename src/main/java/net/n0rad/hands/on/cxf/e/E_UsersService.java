package net.n0rad.hands.on.cxf.e;

import net.n0rad.hands.on.cxf.global.User;
import net.n0rad.hands.on.cxf.global.error.UserNotFoundException;

public class E_UsersService implements E_UsersResource {

    @Override
    public User getDefaultUser() throws UserNotFoundException {
        throw new UserNotFoundException("Cannot find default user");
    }

}
