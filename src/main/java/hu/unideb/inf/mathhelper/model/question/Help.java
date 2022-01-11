package hu.unideb.inf.mathhelper.model.question;

import jakarta.xml.bind.annotation.XmlElement;

public class Help {

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "needed_points")
    private Integer neededPoints;

}
