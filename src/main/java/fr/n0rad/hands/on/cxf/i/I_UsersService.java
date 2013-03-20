package fr.n0rad.hands.on.cxf.i;

import fr.n0rad.hands.on.cxf.global.User;

public class I_UsersService implements I_UsersResource {

    @Override
    public User getDefaultUser() {
        return new User("Arnaud", "Lemaire");
    }

}
