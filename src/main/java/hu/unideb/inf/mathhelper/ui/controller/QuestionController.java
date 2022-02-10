package hu.unideb.inf.mathhelper.ui.controller;

import hu.unideb.inf.mathhelper.dao.*;
import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.exception.QuestionFileNotFoundException;
import hu.unideb.inf.mathhelper.model.question.*;
import hu.unideb.inf.mathhelper.service.UserHandleService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class QuestionController implements Controller {

    private static final String CORRECT_ANSWER_IMAGE_NAME = "correct.png";
    private static final String WRONG_ANSWER_IMAGE_NAME = "wrong.png";

    @Value("${ui.text.point}")
    private String points;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private UserHandleService userHandleService;

    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private PanelDAO panelDAO;

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

    private Map<TextField, ImageView> usedFields;
    private List<Answers> answers;
    private List<Button> helpButtons;
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
        usedFields = new HashMap<>();
        answers = new ArrayList<>();
        helpButtons = new ArrayList<>();
        List<Question> list;
        try {
            list = questionDAO.loadQuestionsIntoList(locationDAO.getQuestionFolderPath().getPath());
            Question question = getQuestion(list);
            build(question);
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

    private void openImage(Node button, String name) {
        button.setVisible(true);
        button.setOnMouseClicked(event -> {
            Stage stage = new Stage();
            stage.setTitle(FilenameUtils.getBaseName(name));
            stage.setScene(new Scene(new AnchorPane(new ImageView(new Image(locationDAO.getQuestionPictureFilePath(name))))));
            stage.show();
        });
    }

    private void validate() {
        int index = 0;
        for (Map.Entry<TextField, ImageView> entry : usedFields.entrySet()) {
            if (answers.get(index).getAnswerList().contains(entry.getKey().getText())) {
                entry.getValue().setImage(new Image(locationDAO.getUiPictureFilePath(CORRECT_ANSWER_IMAGE_NAME)));
            } else {

                entry.getValue().setImage(new Image(locationDAO.getUiPictureFilePath(WRONG_ANSWER_IMAGE_NAME)));
            }
            entry.getValue().setVisible(true);
            entry.getKey().setDisable(true);
            helpButtons.get(index).setDisable(true);
            index++;
        }
        //TODO For every successfully answered question add points to user
    }

    private void build(Question question) {
        List<SubQuestion> subQuestionList = question.getSubQuestion().getSubQuestionList();
        BorderPane root;
        try {
            root = panelDAO.loadSampleQuestionPane();
            middleAnchor.getChildren().clear();

            HBox titleLine = (HBox) root.getTop();
            ((Label) (titleLine.getChildren().get(0))).setText(question.getDescription());
            Button questionImage = (Button) (titleLine.getChildren().get(2));
            if (question.hasImage()) {
                openImage(questionImage, question.getImage());
            } else {
                questionImage.setVisible(false);
            }

            ScrollPane scrollPane = (ScrollPane) root.getCenter();
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            VBox vbox = (VBox) ((AnchorPane) (scrollPane.getContent())).getChildren().get(0);
            int index = 0;
            for (SubQuestion subQuestion : subQuestionList) {
                HBox main = (HBox) vbox.getChildren().get(index);
                VBox boxInMain = (VBox) main.getChildren().get(0);
                Label desc = (Label) boxInMain.getChildren().get(1);
                desc.setText(subQuestion.getDescription());

                HBox secondLine = (HBox) boxInMain.getChildren().get(2);
                HBox result = (HBox) secondLine.getChildren().get(0);
                Button pictureButton = (Button) secondLine.getChildren().get(1);
                VBox helpBox = (VBox) secondLine.getChildren().get(2);
                Label helpLabel = (Label) secondLine.getChildren().get(3);
                if (subQuestion.hasImage()) {
                    openImage(pictureButton, subQuestion.getImage());
                } else {
                    pictureButton.setVisible(false);
                }

                ImageView image = (ImageView) result.getChildren().get(2);
                image.setVisible(false);

                usedFields.put((TextField) result.getChildren().get(1), image);
                answers.add(subQuestion.getAnswer());


                Text cost = (Text) helpBox.getChildren().get(0);
                Button button = (Button) helpBox.getChildren().get(1);
                Help help = subQuestion.getHelp();
                if (help.getNeededPoints() != 0) {
                    cost.setText(help.getNeededPoints() + points);
                    button.setOnMouseClicked(event -> {
                        //TODO Retract points
                        helpLabel.setVisible(true);
                        helpLabel.setText(help.getDescription());
                        secondLine.getChildren().remove(helpBox);
                    });
                } else {
                    secondLine.getChildren().remove(helpBox);
                }
                helpButtons.add(button);
                main.setVisible(true);
                index++;
            }

            AnchorPane.setLeftAnchor(root,0.0);
            AnchorPane.setRightAnchor(root,0.0);
            AnchorPane.setTopAnchor(root,0.0);
            AnchorPane.setBottomAnchor(root,0.0);

            middleAnchor.getChildren().add(root);

        } catch (FXMLFileNotFoundException e) {
            //TODO handle error
            e.printStackTrace();
        }
    }
}
