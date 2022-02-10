package hu.unideb.inf.mathhelper.config;

import hu.unideb.inf.mathhelper.dao.*;
import hu.unideb.inf.mathhelper.dao.impl.*;
import hu.unideb.inf.mathhelper.repository.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public LevelDAO levelDAO() {
        return new LevelDAOImpl();
    }

    @Bean
    public PanelDAO panelDAO() {
        return new PanelDAOImpl();
    }

    @Bean
    public LocationDAO locationDAO() {
        return new LocationDAOImpl();
    }

    @Bean
    public PicturesDAO picturesDAO() {
        return new PicturesDAOImpl();
    }

    @Bean
    public QuestionDAO questionDAO() {
        return new QuestionDAOImpl();
    }

    @Bean
    public CategoryDAO categoryDAO() {
        return new CategoryDAOImpl();
    }

    @Bean
    public UserRepositoryDAO userRepositoryDAO(UserRepository userRepository) {
        return new UserRepositoryDAOImpl(userRepository);
    }

    @Bean
    public SceneDAO sceneDAO(ApplicationContext applicationContext) {
        return new SceneDAOImpl(applicationContext);
    }
}
