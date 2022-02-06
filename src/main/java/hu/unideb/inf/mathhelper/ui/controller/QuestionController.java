package hu.unideb.inf.mathhelper.ui.controller;

import hu.unideb.inf.mathhelper.dao.CategoryDAO;
import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.QuestionDAO;
import hu.unideb.inf.mathhelper.dao.SceneDAO;
import hu.unideb.inf.mathhelper.exception.FXMLFileNotFoundException;
import hu.unideb.inf.mathhelper.exception.QuestionFileNotFoundException;
import hu.unideb.inf.mathhelper.model.question.*;
import hu.unideb.inf.mathhelper.service.UserHandleService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.skin.ScrollPaneSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class QuestionController implements Controller{

    private static final String CORRECT_ANSWER_IMAGE_NAME = "correct.png";
    private static final String WRONG_ANSWER_IMAGE_NAME = "wrong.png";

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
    private ScrollPane scrollPane;

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

    private void load() {
        usedFields = new HashMap<>();
        answers = new ArrayList<>();
        helpButtons = new ArrayList<>();
        List<Question> list;
        scrollPane.setVvalue(0);
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
                mainPicture.setDisable(true);
                mainPicture.setVisible(false);
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
        button.setFocusTraversable(false);
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
        VBox root;
        try {
            root = sceneDAO.loadSampleQuestionPane();
            anchorPane.getChildren().clear();

            int index = 0;
            for (SubQuestion subQuestion : subQuestionList) {
                HBox main = (HBox) root.getChildren().get(index);
                VBox boxInMain = (VBox) main.getChildren().get(0);
                Label desc = (Label) boxInMain.getChildren().get(1);
                desc.setText(subQuestion.getDescription());

                HBox secondLine = (HBox) boxInMain.getChildren().get(2);
                Node picture;
                HBox result;
                VBox helpBox;
                Label helpLabel;
                if (subQuestion.hasImage()) {
                    picture = new Button(openPicture);
                    openImage(picture, subQuestion.getImage());
                    secondLine.getChildren().add(0, picture);
                    result = (HBox) secondLine.getChildren().get(1);
                    helpBox = (VBox) secondLine.getChildren().get(2);
                    helpLabel = (Label) secondLine.getChildren().get(3);
                } else {
                    result = (HBox) secondLine.getChildren().get(0);
                    helpBox = (VBox) secondLine.getChildren().get(1);
                    helpLabel = (Label) secondLine.getChildren().get(2);
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
            anchorPane.getChildren().add(root);

        } catch (FXMLFileNotFoundException e) {
            //TODO handle error
            e.printStackTrace();
        }
    }
}
