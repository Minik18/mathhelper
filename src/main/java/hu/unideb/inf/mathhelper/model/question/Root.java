package hu.unideb.inf.mathhelper.model.question;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
