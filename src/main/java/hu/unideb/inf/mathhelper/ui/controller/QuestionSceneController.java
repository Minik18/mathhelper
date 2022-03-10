package hu.unideb.inf.mathhelper.ui.controller;

import hu.unideb.inf.mathhelper.dao.*;
import hu.unideb.inf.mathhelper.exception.QuestionFileNotFoundException;
import hu.unideb.inf.mathhelper.model.question.*;
import hu.unideb.inf.mathhelper.service.UserHandleService;
import hu.unideb.inf.mathhelper.ui.model.FinalQuestion;
import hu.unideb.inf.mathhelper.ui.util.QuestionValidator;
import hu.unideb.inf.mathhelper.ui.util.QuestionBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class QuestionSceneController implements PanelController {

    private final QuestionDAO questionDAO;
    private final CategoryDAO categoryDAO;
    private final UserHandleService userHandleService;
    private final LocationDAO locationDAO;
    private final QuestionValidator questionValidator;
    private final QuestionBuilder questionBuilder;
    private final SettingsDAO settingsDAO;

    @FXML
    private AnchorPane middleAnchor;

    @FXML
    private Button start;

    @FXML
    private Button selectAll;

    @FXML
    private Button restart;

    @FXML
    private Button next;

    @FXML
    private Button check;

    @FXML
    private ListView<String> listView;

    private List<Category> selectedCategories;
    private Map<String, Category> categoryMap;
    private Question question;

    @Autowired
    public QuestionSceneController(QuestionDAO questionDAO, CategoryDAO categoryDAO,
                                   UserHandleService userHandleService, LocationDAO locationDAO,
                                   QuestionValidator questionValidator, QuestionBuilder questionBuilder,
                                   SettingsDAO settingsDAO) {
        this.questionDAO = questionDAO;
        this.categoryDAO = categoryDAO;
        this.userHandleService = userHandleService;
        this.locationDAO = locationDAO;
        this.questionValidator = questionValidator;
        this.questionBuilder = questionBuilder;
        this.settingsDAO = settingsDAO;
    }

    @Override
    public void setup() {
        restart.setDisable(true);
        next.setDisable(true);
        check.setDisable(true);
        loadCategories();
        selectAll.setOnMouseClicked(event -> {
            selectedCategories = new ArrayList<>(categoryMap.values());
            listView.getSelectionModel().selectAll();
            start();
        });
        start.setOnMouseClicked(event -> {
            selectedCategories = convertStringToCategory(listView.getSelectionModel().getSelectedItems());
            if (selectedCategories.size() != 0) {
                start();
            }
        });
        check.setOnMouseClicked(event -> {
            next.setDisable(false);
            check.setDisable(true);
            validate();
        });
        next.setOnMouseClicked(event -> {
            next.setDisable(true);
            check.setDisable(false);
            load();
        });
        restart.setOnMouseClicked(event -> {
            middleAnchor.getChildren().clear();
            restart.setDisable(true);
            next.setDisable(true);
            check.setDisable(true);
            start.setDisable(false);
            listView.setDisable(false);
            selectAll.setDisable(false);
            listView.getSelectionModel().clearSelection();
        });
    }

    private void start() {
        start.setDisable(true);
        check.setDisable(false);
        restart.setDisable(false);
        listView.setDisable(true);
        selectAll.setDisable(true);
        load();
    }

    private List<Category> convertStringToCategory(List<String> selectedItems) {
        List<Category> result = new ArrayList<>();
        for (String category : selectedItems) {
            result.add(categoryMap.get(category));
        }
        return result;
    }

    private void loadCategories() {
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        categoryMap = categoryDAO.getCategoryMap();
        for (Map.Entry<String, Category> entry : categoryMap.entrySet()) {
            listView.getItems().add(entry.getKey());
        }
    }

    private void load() {
        List<Question> list;
        try {
            list = questionDAO.loadQuestionsIntoList(locationDAO.getQuestionFolderPath().getPath());
            Optional<Question> optionalQuestion = getQuestion(list);
            if (optionalQuestion.isPresent()) {
                question = optionalQuestion.get();
                questionBuilder.buildQuestionPane(question, middleAnchor);
            } else {
                reportToUser();
            }
        } catch (QuestionFileNotFoundException e) {
            e.printStackTrace();
            //TODO
        }
    }

    private void reportToUser() {
        Label label = new Label("Az összes feladat megoldva! A beállításokban lehetőség van a már megoldott feladatok listázására.");
        restart.fire();
        AnchorPane.setBottomAnchor(label, 0.0);
        AnchorPane.setTopAnchor(label, 0.0);
        AnchorPane.setRightAnchor(label, 0.0);
        AnchorPane.setLeftAnchor(label, 0.0);
        middleAnchor.getChildren().add(label);
    }

    private Optional<Question> getQuestion(List<Question> list) {
        List<String> ids = userHandleService.getUserData().getCompletedQuestionIds();
        List<Question> questions;
        if(!settingsDAO.getSettings().isUseSolvedQuestions()) {
            questions = list.stream()
                    .filter(question1 -> {
                        List<Category> categories = question1.getCategories().getCategoryList();
                        Optional<Category> category = selectedCategories.stream()
                                .filter(categories::contains)
                                .findFirst();
                        return category.isPresent();
                    })
                    .filter(question1 -> !ids.contains(question1.getId()))
                    .toList();
        } else {
            questions = list.stream()
                    .filter(question1 -> {
                        List<Category> categories = question1.getCategories().getCategoryList();
                        Optional<Category> category = selectedCategories.stream()
                                .filter(categories::contains)
                                .findFirst();
                        return category.isPresent();
                    })
                    .toList();
        }
        if (questions.size() == 0) {
            return Optional.empty();
        } else {
            Random random = new Random();
            return Optional.of(questions.get(random.nextInt(questions.size())));
        }
    }

    private void validate() {
        FinalQuestion finalQuestion = new FinalQuestion();
        finalQuestion.setQuestion(question);
        finalQuestion.setHelpButtons(questionBuilder.getHelpButtons());
        finalQuestion.setAnswers(questionBuilder.getAnswers());
        finalQuestion.setUsedFields(questionBuilder.getUsedFields());
        questionValidator.validateQuestion(finalQuestion);
    }
}
