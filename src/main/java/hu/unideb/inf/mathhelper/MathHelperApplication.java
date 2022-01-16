package hu.unideb.inf.mathhelper;

import hu.unideb.inf.mathhelper.controller.UiController;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class MathHelperApplication extends Application {

    @Override
    public void start(Stage stage) {
        ApplicationContext applicationContext = SpringApplication.run(hu.unideb.inf.mathhelper.Application.class);
        UiController uiController = applicationContext.getBean(UiController.class);

        uiController.initializeStage(stage);
        uiController.start();
    }
}
