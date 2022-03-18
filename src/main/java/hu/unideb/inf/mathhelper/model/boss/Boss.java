package hu.unideb.inf.mathhelper.model.boss;

import javax.xml.bind.annotation.XmlElement;

public class Boss {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "picture")
    private String picturePath;

    @XmlElement(name = "difficulty")
    private Integer difficulty;

    @XmlElement(name = "about")
    private String about;

    public String getName() {
        return name;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public String getAbout() {
        return about;
    }
}
