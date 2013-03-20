package fr.n0rad.hands.on.cxf.k;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import fr.n0rad.hands.on.cxf.global.User;

@Validated
public class K_UsersService implements K_UsersResource {

    private final List<User> users = new ArrayList<>();

    @Override
    public void createUser(User user) {
        users.add(user);
    }

}
