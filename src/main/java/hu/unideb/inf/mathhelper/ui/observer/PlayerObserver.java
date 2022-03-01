package hu.unideb.inf.mathhelper.ui.observer;

import hu.unideb.inf.mathhelper.model.FinalResult;
import hu.unideb.inf.mathhelper.ui.controller.finalTest.FinalSceneController;
import hu.unideb.inf.mathhelper.ui.controller.MainSceneController;
import hu.unideb.inf.mathhelper.ui.model.FinalQuestion;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerObserver {

    private MainSceneController mainController;
    private FinalSceneController finalSceneController;
    private List<FinalQuestion> finalQuestions;
    private FinalQuestion unwantedQuestion;
    private FinalResult finalResult;

    public void setMainController(MainSceneController mainController) {
        this.mainController = mainController;
    }

    public void setFinalSceneController(FinalSceneController finalSceneController) {
        this.finalSceneController = finalSceneController;
    }

    public void setUnwantedQuestion(FinalQuestion unwantedQuestion) {
        this.unwantedQuestion = unwantedQuestion;
    }

    public List<FinalQuestion> getFinalQuestions() {
        return finalQuestions;
    }

    public FinalQuestion getUnwantedQuestion() {
        return unwantedQuestion;
    }

    public void finishTest() {
        finalSceneController.finishTest();
    }

    public void setResults(List<FinalQuestion> finalQuestionList) {
        finalQuestions = finalQuestionList;
    }

    public void endOfFinalTest() {
        mainController.unlockButtons();
        mainController.updateUserInformation();
    }

    public void startOfFinalTest() {
        mainController.lockButtons();
    }

    public void setFinalResult(FinalResult result) {
        this.finalResult = result;
    }

    public FinalResult getFinalResult() {
        return finalResult;
    }
}
