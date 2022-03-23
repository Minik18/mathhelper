package hu.unideb.inf.mathhelper.model.question;

import javax.xml.bind.annotation.XmlElement;

public class SubQuestion {

    @XmlElement(name = "point")
    private Integer point;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "image")
    private String image;

    @XmlElement(name = "answers")
    private Answers answer;

    @XmlElement(name = "help")
    private Help help;

    private boolean result;

    public boolean isRight() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Integer getPoint() {
        return point;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public boolean hasImage() {
        return !image.equals("null");
    }

    public Answers getAnswer() {
        return answer;
    }

    public Help getHelp() {
        return help;
    }
}
