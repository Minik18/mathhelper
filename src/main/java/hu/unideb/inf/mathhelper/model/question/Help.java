package hu.unideb.inf.mathhelper.model.question;

import javax.xml.bind.annotation.XmlElement;

public class Help {

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "needed_points")
    private Integer neededPoints;

    public String getDescription() {
        return description;
    }

    public Integer getNeededPoints() {
        return neededPoints;
    }
}
