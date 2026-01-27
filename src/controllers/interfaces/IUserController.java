package controllers.interfaces;

import models.User;

public interface IUserController {
    String register(String name, String password);
    User login(String name, String password);
    String getUser(int id);
    String getAllUsers();

}
