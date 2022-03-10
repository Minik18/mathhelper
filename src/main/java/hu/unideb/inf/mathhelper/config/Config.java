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
    public SettingsDAO settingsDAO(LocationDAO locationDAO) {
        return new SettingsDAOImpl(locationDAO);
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
    public SceneDAO sceneDAO(ApplicationContext applicationContext, LocationDAO locationDAO) {
        return new SceneDAOImpl(applicationContext, locationDAO);
    }

    @Bean
    public PanelDAO panelDAO(ApplicationContext applicationContext, LocationDAO locationDAO) {
        return new PanelDAOImpl(locationDAO, applicationContext);
    }
}
