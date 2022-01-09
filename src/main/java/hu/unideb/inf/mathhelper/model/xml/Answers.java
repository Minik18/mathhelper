package hu.unideb.inf.mathhelper.model.xml;

import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

public class Answers {

    @XmlElement(name = "answer")
    private List<String> answerList;

    public List<String> getAnswerList() {
        return answerList;
    }
}
