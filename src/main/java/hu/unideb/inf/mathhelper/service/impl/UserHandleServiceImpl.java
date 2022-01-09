package hu.unideb.inf.mathhelper.service.impl;

import hu.unideb.inf.mathhelper.dao.LevelDAO;
import hu.unideb.inf.mathhelper.model.User;
import hu.unideb.inf.mathhelper.model.UserData;
import hu.unideb.inf.mathhelper.service.UserHandleService;
import hu.unideb.inf.mathhelper.service.UserTrackService;

import java.util.Map;

public class UserHandleServiceImpl implements UserHandleService {

    private static final int MAX_LENGTH = 60;
    private static final int MIN_LENGTH = 5;

    private final Map<Integer,Integer> levels;
    private final User user;

    public UserHandleServiceImpl(UserTrackService userTrackService, LevelDAO levelDAO) {
        user = userTrackService.getCurrentUser();
        levelDAO.loadLevel();
        levels = levelDAO.getLevelSystem();
    }

    @Override
    public void incrementXp(Integer amount) {
        if (amount >= 1) {
            user.incrementXp(amount);
            //TODO: Handle level up
        }
    }

    @Override
    public void updateNickname(String newNickname) {
        if (validNickname(newNickname)) {
            user.setNickname(newNickname);
        }
    }

    @Override
    public UserData getUserData() {
        return new UserData.Builder()
                .withCompletedQuestionIds(user.getCompletedQuestionIds())
                .withHelpPoints(user.getHelpPoints())
                .withNickname(user.getNickname())
                .withXp(user.getXp())
                .withNumberOfCompletedQuestions(user.getNumberOfCompletedQuestions())
                .withLevel(user.getLevel())
                .build();
    }

    @Override
    public void incrementHelpPoint(Integer amount) {
        if (amount >= 1) {
            user.incrementHelpPoints(amount);
        }
    }

    @Override
    public void addCompletedQuestionId(String questionId) {
        if (!user.getCompletedQuestionIds().contains(questionId)) {
            user.addCompletedQuestionIds(questionId);
        }
    }

    private boolean validNickname(String newNickname) {
        boolean valid = true;
        if (newNickname.length() > MAX_LENGTH) {
            valid = false;
        } else {
            newNickname = newNickname.replace(" ","");
            if(newNickname.length() < MIN_LENGTH) {
                valid = false;
            }
        }
        return valid;
    }
}
