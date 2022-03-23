package hu.unideb.inf.mathhelper;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "hu.unideb.inf.mathhelper")
public class Application {

    public static void main(String[] args) {
        javafx.application.Application.launch(MathHelperApplication.class, args);
    }
}
