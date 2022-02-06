package hu.unideb.inf.mathhelper.ui.controller;

import hu.unideb.inf.mathhelper.dao.CategoryDAO;
import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.QuestionDAO;
import hu.unideb.inf.mathhelper.dao.SceneDAO;
import hu.unideb.inf.mathhelper.exception.QuestionFileNotFoundException;
import hu.unideb.inf.mathhelper.exception.SceneNotFoundException;
import hu.unideb.inf.mathhelper.model.question.*;
import hu.unideb.inf.mathhelper.service.UserHandleService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
public class QuestionController implements Controller{

    private static final String CORRECT_ANSWER_IMAGE_NAME = "correct.png";
    private static final String WRONG_ANSWER_IMAGE_NAME = "wrong.png";
    private static final String SAMPLE_FILE_NAME = "sample.fxml";

    @Value("${ui.text.open_picture}")
    private String openPicture;

    @Value("${ui.text.no_picture}")
    private String noPicture;

    @Value("${ui.text.point}")
    private String points;

    @Value("${ui.text.no_help}")
    private String noHelp;

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private UserHandleService userHandleService;

    @Autowired
    private LocationDAO locationDAO;

    @Autowired
    private SceneDAO sceneDAO;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label mainDescription;

    @FXML
    private Button mainPicture;

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

    private Map<TextField,ImageView> usedFields;
    private List<Answers> answers;
    private List<Button> helpButtons;
    private List<Category> selectedCategories;
    private Map<String,Category> categoryMap;

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
            start();
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
            anchorPane.getChildren().clear();
            restart.setDisable(true);
            next.setDisable(true);
            check.setDisable(true);
            start.setDisable(false);
            mainDescription.setVisible(false);
            listView.setDisable(false);
            selectAll.setDisable(false);
            mainPicture.setVisible(false);
            listView.getSelectionModel().clearSelection();
        });
    }

    private void start() {
        start.setDisable(true);
        mainDescription.setVisible(true);
        mainPicture.setVisible(true);
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
        for (Map.Entry<String,Category> entry : categoryMap.entrySet()) {
            listView.getItems().add(entry.getKey());
        }
    }

    private VBox loadSample() {
        AnchorPane anchorPane;
        try {
            anchorPane =  (AnchorPane)sceneDAO.loadScene(locationDAO.getPaneFilePath(SAMPLE_FILE_NAME)).getRoot();
            return (VBox) anchorPane.getChildren().get(0);
        } catch (SceneNotFoundException e) {
            //TODO
            throw new RuntimeException();
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
            build(question.getSubQuestion().getSubQuestionList());
            mainDescription.setText(question.getDescription());
            if (question.hasImage()) {
                openImage(mainPicture, question.getImage());
                mainPicture.setText(openPicture);
                mainPicture.setDisable(false);
            } else {
                mainPicture.setText(noPicture);
                mainPicture.setDisable(true);
            }
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

    private void openImage(Node button ,String name) {
        button.setOnMouseClicked(event -> {
            Stage stage = new Stage();
            stage.setTitle(FilenameUtils.getBaseName(name));
            stage.setScene(new Scene(new AnchorPane(new ImageView(new Image(locationDAO.getQuestionPictureFilePath(name))))));
            stage.show();
        });
    }

    private void validate() {
        int index = 0;
        for(Map.Entry<TextField,ImageView> entry : usedFields.entrySet()) {
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

    private void build(List<SubQuestion> subQuestionList) {
        VBox root = loadSample();

        anchorPane.getChildren().clear();

        int index = 0;
        for (SubQuestion subQuestion : subQuestionList) {
            HBox main = (HBox) root.getChildren().get(index);
            VBox boxInMain = (VBox) main.getChildren().get(0);
            Label desc = (Label) boxInMain.getChildren().get(0);
            desc.setText(subQuestion.getDescription());

            HBox secondLine = (HBox) boxInMain.getChildren().get(1);
            Node picture;
            if (subQuestion.hasImage()) {
                picture = new Button(openPicture);
                openImage(picture, subQuestion.getImage());
            } else {
                picture = new Text(noPicture);
            }
            secondLine.getChildren().add(0,picture);

            HBox result = (HBox) secondLine.getChildren().get(1);

            ImageView image = (ImageView) result.getChildren().get(2);
            image.setVisible(false);

            usedFields.put( (TextField) result.getChildren().get(1), image);
            answers.add(subQuestion.getAnswer());

            VBox helpBox = (VBox) secondLine.getChildren().get(2);
            Text cost = (Text) helpBox.getChildren().get(0);
            Button button = (Button) helpBox.getChildren().get(1);
            Help help = subQuestion.getHelp();
            if (help.getNeededPoints() != 0) {
                cost.setText(help.getNeededPoints() + points);
                button.setOnMouseClicked(event -> {
                    button.setDisable(true);
                    //TODO Retract points
                    Label label = (Label) secondLine.getChildren().get(3);
                    label.setVisible(true);
                    label.setText(help.getDescription());
                });
            } else {
                cost.setText(help.getNeededPoints() + points);
                button.setText(noHelp);
                button.setDisable(true);
            }
            helpButtons.add(button);
            main.setVisible(true);
            index++;
        }
        anchorPane.getChildren().add(root);
    }
}
