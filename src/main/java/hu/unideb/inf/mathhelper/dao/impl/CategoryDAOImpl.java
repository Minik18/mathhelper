package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.CategoryDAO;
import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.model.question.Category;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    private LocationDAO locationDAO;

    @Override
    public Map<String, Category> getCategoryMap() {
        Map<String, Category> result = new HashMap<>();
        try {
            CompositeConfiguration config = new CompositeConfiguration();
            config.addConfiguration(new SystemConfiguration());
            config.addConfiguration(new PropertiesConfiguration(locationDAO.getCategoryFilePath()));
            for(Category category : Category.values()) {
                result.put(config.getString(category.name()),category);
            }
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        return result;
    }
}
