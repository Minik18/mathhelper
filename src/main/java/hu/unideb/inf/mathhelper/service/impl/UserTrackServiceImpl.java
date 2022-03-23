package hu.unideb.inf.mathhelper.service.impl;

import hu.unideb.inf.mathhelper.dao.UserRepositoryDAO;
import hu.unideb.inf.mathhelper.model.User;
import hu.unideb.inf.mathhelper.service.UserTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTrackServiceImpl implements UserTrackService {

    private User currentUser;
    private final UserRepositoryDAO userRepositoryDAO;

    @Autowired
    public UserTrackServiceImpl(UserRepositoryDAO userRepositoryDAO) {
        currentUser = userRepositoryDAO.getUser();
        this.userRepositoryDAO = userRepositoryDAO;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void updateCurrentUser(User user) {
        currentUser = user;
        userRepositoryDAO.save(user);
    }
}
