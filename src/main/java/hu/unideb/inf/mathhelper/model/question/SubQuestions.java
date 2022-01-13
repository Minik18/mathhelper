package hu.unideb.inf.mathhelper.model.question;

import javax.xml.bind.annotation.XmlElement;

import java.util.List;

public class SubQuestions {

    @XmlElement(name = "sub_question")
    private List<SubQuestion> subQuestionList;

    public List<SubQuestion> getSubQuestionList() {
        return subQuestionList;
    }
}
