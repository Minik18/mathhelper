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
    private final UserTrackService userTrackService;
    private final User user;

    @Autowired
    public UserHandleServiceImpl(UserTrackService userTrackService, LevelDAO levelDAO,
                                 LocationDAO locationDAO) {
        this.userTrackService = userTrackService;
        user = userTrackService.getCurrentUser();
        levels = levelDAO.getLevelSystem(locationDAO.getLevelSystemFilePath());
    }

    @Override
    public void incrementXp(Integer amount) {
        if (amount >= 1) {
            user.incrementXp(amount);
            Level currentLevel = levels.get(user.getLevel()-1);
            Integer requiredXp = currentLevel.getRequiredXp();
            if (requiredXp <= user.getXp()) {
                user.decrementXp(requiredXp);
                user.incrementRewardPoints(currentLevel.getRewardPoints());
                user.incrementHelpPoints(currentLevel.getHelpPoints());
                user.incrementLevel();
            }
            updateUser();
        }
    }

    @Override
    public void updateNickname(String newNickname) {
        if (validNickname(newNickname)) {
            user.setNickname(newNickname);
            updateUser();
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
                .withCountOfFinals(user.getCountOfFinals())
                .build();
    }

    @Override
    public void incrementCompletedFinalQuestions() {
        user.incrementCountOfFinals();
        updateUser();
    }

    @Override
    public void addCompletedQuestionId(String questionId) {
        if (!user.getCompletedQuestionIds().contains(questionId)) {
            user.addCompletedQuestionIds(questionId);
            user.incrementNumberOfCompletedQuestions();
            updateUser();
        }
    }

    @Override
    public void decrementHelpPoints(Integer amount) {
        user.decrementHelpPoints(amount);
        updateUser();
    }

    @Override
    public void decrementRewardPoints(Integer amount) {
        user.decrementRewardPoints(amount);
        updateUser();
    }

    private void updateUser() {
        userTrackService.updateCurrentUser(user);
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
