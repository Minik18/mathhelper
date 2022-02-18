package hu.unideb.inf.mathhelper.ui.model;

import hu.unideb.inf.mathhelper.model.question.Answers;
import hu.unideb.inf.mathhelper.model.question.Question;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinalQuestion {

    private Map<TextField, ImageView> usedFields;
    private List<Answers> answers;
    private List<Button> helpButtons;
    private Question question;
    private AnchorPane rootOfGraphicalUiElements;

    public FinalQuestion() {
        rootOfGraphicalUiElements = new AnchorPane();
    }

    public FinalQuestion setUsedFields(Map<TextField, ImageView> usedFields) {
        this.usedFields = usedFields;
        return this;
    }

    public FinalQuestion setAnswers(List<Answers> answers) {
        this.answers = answers;
        return this;
    }

    public FinalQuestion setHelpButtons(List<Button> helpButtons) {
        this.helpButtons = helpButtons;
        return this;
    }

    public FinalQuestion setQuestion(Question question) {
        this.question = question;
        return this;
    }

    public Map<TextField, ImageView> getUsedFields() {
        return usedFields;
    }

    public List<Answers> getAnswers() {
        return answers;
    }

    public List<Button> getHelpButtons() {
        return helpButtons;
    }

    public Question getQuestion() {
        return question;
    }

    public AnchorPane getRootOfGraphicalUiElements() {
        return rootOfGraphicalUiElements;
    }
}
