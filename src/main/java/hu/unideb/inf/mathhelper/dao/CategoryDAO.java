package hu.unideb.inf.mathhelper.dao;

import hu.unideb.inf.mathhelper.model.question.Category;

import java.util.Map;

public interface CategoryDAO {

    Map<String, Category> getCategoryMap();

}
