package hu.unideb.inf.mathhelper.service.impl;

import hu.unideb.inf.mathhelper.dao.LevelDAO;
import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.model.User;
import hu.unideb.inf.mathhelper.model.UserData;
import hu.unideb.inf.mathhelper.model.level.Level;
import hu.unideb.inf.mathhelper.service.UserHandleService;
import hu.unideb.inf.mathhelper.service.UserTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserHandleServiceImpl implements UserHandleService {

    private static final int MAX_LENGTH = 60;
    private static final int MIN_LENGTH = 5;

    private final List<Level> levels;
    private final User user;

    @Autowired
    public UserHandleServiceImpl(UserTrackService userTrackService, LevelDAO levelDAO,
                                 LocationDAO locationDAO) {
        user = userTrackService.getCurrentUser();
        levels = levelDAO.getLevelSystem(locationDAO.getLevelSystemFilePath());
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
                .withRewardPoints(user.getRewardPoints())
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
