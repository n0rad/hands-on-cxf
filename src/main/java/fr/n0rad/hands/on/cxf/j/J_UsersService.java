package fr.n0rad.hands.on.cxf.j;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import fr.n0rad.hands.on.cxf.global.User;

@Validated
@Service
public class J_UsersService implements J_UsersResource {

    private final List<User> users = new ArrayList<>();

    @Override
    public void createUser(User user) {
        users.add(user);
    }

}
