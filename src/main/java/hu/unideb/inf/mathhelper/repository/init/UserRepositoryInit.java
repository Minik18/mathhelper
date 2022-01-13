package hu.unideb.inf.mathhelper.repository.init;

import hu.unideb.inf.mathhelper.model.User;
import hu.unideb.inf.mathhelper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
@Profile("init")
public class UserRepositoryInit {

    private final UserRepository userRepository;
    private final User user = new User("default");

    @Autowired
    public UserRepositoryInit(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void init() {
        userRepository.save(user);
    }
}
