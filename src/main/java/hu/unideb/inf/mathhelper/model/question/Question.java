package hu.unideb.inf.mathhelper.model.question;

import javax.xml.bind.annotation.XmlElement;

public class Question {

    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "part")
    private Part part;

    @XmlElement(name = "categories")
    private Categories categories;

    @XmlElement(name = "point_sum")
    private Integer points;

    @XmlElement(name = "xp_sum")
    private Integer xp;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "image")
    private String image;

    @XmlElement(name = "sub_questions")
    private SubQuestions subQuestion;

    public boolean hasImage() {
        return !image.equals("null");
    }

    public String getId() {
        return id;
    }

    public Categories getCategories() {
        return categories;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getXp() {
        return xp;
    }

    public String getDescription() {
        return description;
    }

    public SubQuestions getSubQuestion() {
        return subQuestion;
    }

    public Part getPart() {
        return part;
    }

    public String getImage() {
        return image;
    }
}
