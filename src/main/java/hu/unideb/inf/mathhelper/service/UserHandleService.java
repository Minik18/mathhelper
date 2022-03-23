package hu.unideb.inf.mathhelper.service;

import hu.unideb.inf.mathhelper.exception.InvalidUsernameException;
import hu.unideb.inf.mathhelper.model.UserData;

public interface UserHandleService {

    void incrementXp(Integer amount);

    void updateNickname(String newNickname) throws InvalidUsernameException;

    UserData getUserData();

    void incrementCompletedFinalQuestions();

    void addCompletedQuestionId(String questionId);

    void decrementHelpPoints(Integer amount);

    void resetUserData();

    void updateProfilePicture(String name);

    void incrementStudentKnowledgePoints(Integer amount);

    void incrementBossLevel();

}
