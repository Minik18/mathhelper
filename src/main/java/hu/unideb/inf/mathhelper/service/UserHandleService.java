package hu.unideb.inf.mathhelper.service;

import hu.unideb.inf.mathhelper.model.UserData;

public interface UserHandleService {

    void incrementXp(Integer amount);

    void updateNickname(String newNickname);

    UserData getUserData();

    void incrementHelpPoint(Integer amount);

    void addCompletedQuestionId(String questionId);

}
