package repositories.interfaces;

import models.User;

import java.util.List;

public interface IUserRepository {
    boolean register(User user);
    User login(String name, String password);
    User getUser(int id);
    List<User> getAllUsers();
}
