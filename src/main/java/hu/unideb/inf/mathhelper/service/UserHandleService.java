package hu.unideb.inf.mathhelper.service;

import hu.unideb.inf.mathhelper.model.UserData;

public interface UserHandleService {

    void incrementXp(Integer amount);

    void updateNickname(String newNickname);

    UserData getUserData();

    void incrementCompletedFinalQuestions();

    void addCompletedQuestionId(String questionId);

    void decrementHelpPoints(Integer amount);

    void decrementRewardPoints(Integer amount);

}
