package hu.unideb.inf.mathhelper.model.level;

import javax.xml.bind.annotation.XmlElement;

public class Level {

    @XmlElement(name = "rank")
    private Integer level;

    @XmlElement(name = "help_points")
    private Integer helpPoints;

    @XmlElement(name = "required_xp")
    private Integer requiredXp;

    @XmlElement(name = "reward_points")
    private Integer rewardPoints;

    public Integer getLevel() {
        return level;
    }

    public Integer getHelpPoints() {
        return helpPoints;
    }

    public Integer getRequiredXp() {
        return requiredXp;
    }

    public Integer getRewardPoints() {
        return rewardPoints;
    }
}
