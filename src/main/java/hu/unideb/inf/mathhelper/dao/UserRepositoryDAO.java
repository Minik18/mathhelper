package hu.unideb.inf.mathhelper.dao;

import hu.unideb.inf.mathhelper.model.User;

public interface UserRepositoryDAO {

    void save(User user);

    User getUser();

}
