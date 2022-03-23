package hu.unideb.inf.mathhelper.ui.util;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.model.question.Answers;
import hu.unideb.inf.mathhelper.service.UserHandleService;
import hu.unideb.inf.mathhelper.ui.model.FinalQuestion;
import hu.unideb.inf.mathhelper.ui.observer.PlayerObserver;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class QuestionValidator {

    private static final String CORRECT_ANSWER_IMAGE_NAME = "correct.png";
    private static final String WRONG_ANSWER_IMAGE_NAME = "wrong.png";

    private final LocationDAO locationDAO;
    private final UserHandleService userHandleService;
    private final PlayerObserver playerObserver;

    @Autowired
    public QuestionValidator(LocationDAO locationDAO, UserHandleService userHandleService,
                             PlayerObserver playerObserver) {
        this.locationDAO = locationDAO;
        this.userHandleService = userHandleService;
        this.playerObserver = playerObserver;
    }

    public void validateQuestion(FinalQuestion finalQuestion) {
        List<Answers> answers = finalQuestion.getAnswers();
        List<Button> helpButtons = finalQuestion.getHelpButtons();
        Map<TextField,ImageView> usedFields = finalQuestion.getUsedFields();
        List<String> ids = userHandleService.getUserData().getCompletedQuestionIds();
        int index = 0;
        int correctAnswers = 0;
        for (Map.Entry<TextField, ImageView> entry : usedFields.entrySet()) {
            String answer = entry.getKey().getText();
            if (answers.get(index).getAnswerList().contains(answer)) {
                entry.getValue().setImage(new Image(locationDAO.getUiPictureFilePath(CORRECT_ANSWER_IMAGE_NAME)));
                finalQuestion.getQuestion().getSubQuestion().getSubQuestionList().get(index).setResult(true);
                correctAnswers++;
            } else {
                entry.getValue().setImage(new Image(locationDAO.getUiPictureFilePath(WRONG_ANSWER_IMAGE_NAME)));
                finalQuestion.getQuestion().getSubQuestion().getSubQuestionList().get(index).setResult(false);
            }
            entry.getValue().setVisible(true);
            entry.getKey().setDisable(true);
            helpButtons.get(index).setDisable(true);
            index++;
        }
        if (correctAnswers == finalQuestion.getQuestion().getSubQuestion().getSubQuestionList().size()) {
            if(!ids.contains(finalQuestion.getQuestion().getId())) {
                userHandleService.addCompletedQuestionId(finalQuestion.getQuestion().getId());
                userHandleService.incrementXp(finalQuestion.getQuestion().getXp());
                playerObserver.updateUserInformation();
            }
        }
    }

}
