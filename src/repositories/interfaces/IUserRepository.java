package repositories.interfaces;

import models.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    boolean register(User user);

    default User login(String name, String password) {
        return null;
    }

    User getUser(int id);

    List<User> getAllUsers();
}
