package hu.unideb.inf.mathhelper.model.question;

import javax.xml.bind.annotation.XmlElement;

import java.util.List;

public class Categories {

    @XmlElement(name = "category")
    private List<Category> categoryList;

    public List<Category> getCategoryList() {
        return categoryList;
    }
}
