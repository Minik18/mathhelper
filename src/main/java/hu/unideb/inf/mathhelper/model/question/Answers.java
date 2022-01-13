package hu.unideb.inf.mathhelper.model.question;

import javax.xml.bind.annotation.XmlElement;


import java.util.List;

public class Answers {

    @XmlElement(name = "answer")
    private List<String> answerList;

    public List<String> getAnswerList() {
        return answerList;
    }
}
