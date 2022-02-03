package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.CategoryDAO;
import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.model.question.Category;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    private LocationDAO locationDAO;

    @Override
    public Map<String, Category> getCategoryMap() {
        Map<String, Category> result = new HashMap<>();
        FileInputStream input;
        try {
            input = new FileInputStream(locationDAO.getCategoryFilePath().getPath());
            Properties properties = new Properties();
            properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));
            for(Category category : Category.values()) {
                result.put(properties.getProperty(category.name()),category);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
