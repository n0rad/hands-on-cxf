package fr.n0rad.hands.on.cxf.i;

import java.util.HashMap;
import java.util.Map;
import fr.n0rad.hands.on.cxf.global.User;

public class I_UserService implements I_UserResource {

    private final Map<Long, User> users = new HashMap<>();

    public I_UserService() {
        users.put(42l, new User("Guillaume", "Balaine"));
    }

    @Override
    public User getUser(long id) {
        return users.get(id);
    }

}
