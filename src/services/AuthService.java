package services;

import dto.UserDTO;
import models.User;
import repositories.UserRepository;

public class AuthService {
    private final UserRepository userRepo;

    public AuthService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserDTO login(String email, String password) {
        User user = userRepo.findByEmailAndPassword(email, password);
        if (user == null) return null;

        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getName()
        );
    }

    public boolean register(String name, String email, String password) {
        return userRepo.createUser(name, email, password);
    }
}