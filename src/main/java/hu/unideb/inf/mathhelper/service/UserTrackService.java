package hu.unideb.inf.mathhelper.service;

import hu.unideb.inf.mathhelper.model.User;

public interface UserTrackService {

    User getCurrentUser();

    void updateCurrentUser(User user);

}
