package hu.unideb.inf.mathhelper.model.xml;

import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

public class Categories {

    @XmlElement(name = "category")
    private List<Category> categoryList;

    public List<Category> getCategoryList() {
        return categoryList;
    }
}
