package hu.unideb.inf.mathhelper.ui.util;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.QuestionDAO;
import hu.unideb.inf.mathhelper.exception.QuestionFileNotFoundException;
import hu.unideb.inf.mathhelper.model.question.Part;
import hu.unideb.inf.mathhelper.model.question.Question;
import hu.unideb.inf.mathhelper.ui.model.FinalQuestion;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class FinalQuestionBuilder {

    @Autowired
    private QuestionBuilder questionBuilder;

    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private QuestionDAO questionDAO;

    public List<FinalQuestion> getFinalTestList() {
        try {
            List<Question> allQuestion = questionDAO.loadQuestionsIntoList(locationDAO.getQuestionFolderPath().getPath());
            List<FinalQuestion> result = new ArrayList<>();
            List<Question> chosenQuestions = getRandomQuestionList(allQuestion);
            for(Question question : chosenQuestions) {
                FinalQuestion finalQuestion = new FinalQuestion();
                questionBuilder.buildQuestionPane(question,finalQuestion.getRootOfGraphicalUiElements());
                finalQuestion.setQuestion(question)
                        .setAnswers(questionBuilder.getAnswers())
                        .setHelpButtons(questionBuilder.getHelpButtons())
                        .setUsedFields(questionBuilder.getUsedFields());
                AnchorPane rootElement = finalQuestion.getRootOfGraphicalUiElements();
                AnchorPane.setRightAnchor(rootElement, 0.0);
                AnchorPane.setLeftAnchor(rootElement, 0.0);
                AnchorPane.setTopAnchor(rootElement, 0.0);
                AnchorPane.setBottomAnchor(rootElement, 0.0);
                result.add(finalQuestion);
            }
            return result;
        } catch (QuestionFileNotFoundException e) {
            //TODO error
            e.printStackTrace();
        }
        return null;
    }

    private List<Question> getRandomQuestionList(List<Question> questions) {
        List<Question> result = new ArrayList<>();
        //Part 1
        for (int i = 0; i < 12; i++) {
            getRandomQuestionInPart(questions, result, Part.FIRST);
        }
        //Part 2 A
        for (int i = 0; i < 3; i++) {
            getRandomQuestionInPart(questions, result, Part.SECOND_A);
        }
        //Part 2 B
        for (int i = 0; i < 3; i++) {
            getRandomQuestionInPart(questions, result, Part.SECOND_B);
        }
        return result;
    }

    private void getRandomQuestionInPart(List<Question> allQuestions, List<Question> currentQuestions, Part part) {
        Question chosen = null;
        do{
            Random random = new Random();
            int index = random.nextInt(allQuestions.size());
            Question temp = allQuestions.get(index);
            if (temp.getPart().equals(part) && !currentQuestions.contains(temp)) {
                chosen = temp;
            }
        } while(chosen == null);
        currentQuestions.add(chosen);
    }

}
