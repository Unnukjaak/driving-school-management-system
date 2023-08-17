package ee.drivingschool.service;

import ee.drivingschool.model.User;
import ee.drivingschool.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void save(User user) {
        userRepository.save(user);
    }
}
