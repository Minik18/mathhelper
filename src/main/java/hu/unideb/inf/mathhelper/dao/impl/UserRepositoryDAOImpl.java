package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.UserRepositoryDAO;
import hu.unideb.inf.mathhelper.model.User;
import hu.unideb.inf.mathhelper.repository.UserRepository;

public class UserRepositoryDAOImpl implements UserRepositoryDAO {

    private final UserRepository userRepository;

    public UserRepositoryDAOImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser() {
        return userRepository.findAll().iterator().next();
    }
}
