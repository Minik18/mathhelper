package hu.unideb.inf.mathhelper.model.question;

import javax.xml.bind.annotation.XmlElement;

import java.util.List;

public class Questions {

    @XmlElement(name = "question")
    private List<Question> questionList;

    public List<Question> getQuestionList() {
        return questionList;
    }
}
