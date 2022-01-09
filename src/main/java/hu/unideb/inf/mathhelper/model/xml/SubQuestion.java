package hu.unideb.inf.mathhelper.model.xml;

import jakarta.xml.bind.annotation.XmlElement;

public class SubQuestion {

    @XmlElement(name = "point")
    private Integer point;

    @XmlElement(name = "xp")
    private Integer xp;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "image")
    private String image;

    @XmlElement(name = "answers")
    private Answers answer;

}
