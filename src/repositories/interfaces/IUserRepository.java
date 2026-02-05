package repositories.interfaces;

import models.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    boolean register(User user);

    default Optional<User> login(String name, String password) {
        return null;
    }

    Optional<User> getUser(int id);

    List<User> getAllUsers();
}
