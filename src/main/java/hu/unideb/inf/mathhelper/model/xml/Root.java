package hu.unideb.inf.mathhelper.model.xml;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Root {

    @XmlElement(name = "questions")
    private Questions questions;

    @XmlElement(name = "id")
    private String id;

    public Questions getQuestionsObject() {
        return questions;
    }

    public String getId() {
        return id;
    }
}
