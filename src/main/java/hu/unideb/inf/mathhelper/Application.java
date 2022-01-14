package hu.unideb.inf.mathhelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "hu.unideb.inf.mathhelper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        javafx.application.Application.launch(MathHelperApplication.class, args);
    }
}
