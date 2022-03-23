package hu.unideb.inf.mathhelper.config;

import hu.unideb.inf.mathhelper.dao.*;
import hu.unideb.inf.mathhelper.dao.impl.*;
import hu.unideb.inf.mathhelper.repository.UserRepository;
import hu.unideb.inf.mathhelper.service.RunTypeTracker;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public LevelDAO levelDAO(LocationDAO locationDAO) {
        return new LevelDAOImpl(locationDAO);
    }

    @Bean
    public SettingsDAO settingsDAO(LocationDAO locationDAO) {
        return new SettingsDAOImpl(locationDAO);
    }

    @Bean
    public LocationDAO locationDAO(RunTypeTracker runTypeTracker) {
        return new LocationDAOImpl(runTypeTracker);
    }

    @Bean
    public BossDAO bossDAO(LocationDAO locationDAO) {
        return new BossDAOImpl(locationDAO);
    }

    @Bean
    public PicturesDAO picturesDAO() {
        return new PicturesDAOImpl();
    }

    @Bean
    public QuestionDAO questionDAO(LocationDAO locationDAO, RunTypeTracker runTypeTracker) {
        return new QuestionDAOImpl(locationDAO, runTypeTracker);
    }

    @Bean
    public CategoryDAO categoryDAO(LocationDAO locationDAO) {
        return new CategoryDAOImpl(locationDAO);
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
