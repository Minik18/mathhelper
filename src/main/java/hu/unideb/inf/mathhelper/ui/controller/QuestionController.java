package hu.unideb.inf.mathhelper.ui.controller;

import hu.unideb.inf.mathhelper.dao.*;
import hu.unideb.inf.mathhelper.exception.QuestionFileNotFoundException;
import hu.unideb.inf.mathhelper.model.question.*;
import hu.unideb.inf.mathhelper.service.UserHandleService;
import hu.unideb.inf.mathhelper.ui.util.QuestionBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class QuestionController implements Controller {

    private static final String CORRECT_ANSWER_IMAGE_NAME = "correct.png";
    private static final String WRONG_ANSWER_IMAGE_NAME = "wrong.png";

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private UserHandleService userHandleService;

    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private QuestionBuilder questionBuilder;

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

    @Override
    public void setup(Stage stage) {
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
            Question question = getQuestion(list);
            questionBuilder.buildQuestionPane(question,middleAnchor);
        } catch (QuestionFileNotFoundException e) {
            e.printStackTrace();
            //TODO
        }
    }

    private Question getQuestion(List<Question> list) {
        Question question;
        boolean contains = false;
        do {
            Random random = new Random();
            int index = random.nextInt(list.size());
            question = list.get(index);
            for (Category selectedCategory : selectedCategories) {
                for (Category actualCategory : question.getCategories().getCategoryList()) {
                    if (selectedCategory.equals(actualCategory)) {
                        contains = true;
                        break;
                    }
                }
            }
        } while (!contains);
        return question;
    }

    private void validate() {
        int index = 0;
        for (Map.Entry<TextField, ImageView> entry : questionBuilder.getUsedFields().entrySet()) {
            if (questionBuilder.getAnswers().get(index).getAnswerList().contains(entry.getKey().getText())) {
                entry.getValue().setImage(new Image(locationDAO.getUiPictureFilePath(CORRECT_ANSWER_IMAGE_NAME)));
            } else {

                entry.getValue().setImage(new Image(locationDAO.getUiPictureFilePath(WRONG_ANSWER_IMAGE_NAME)));
            }
            entry.getValue().setVisible(true);
            entry.getKey().setDisable(true);
            questionBuilder.getHelpButtons().get(index).setDisable(true);
            index++;
        }
        //TODO For every successfully answered question add points to user
    }
}
