package hu.unideb.inf.mathhelper.service.impl;

import hu.unideb.inf.mathhelper.model.User;
import hu.unideb.inf.mathhelper.service.UserTrackService;

public class UserTrackServiceImpl implements UserTrackService {

    private User currentUser;

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void updateCurrentUser(User user) {
        currentUser = user;
    }
}
