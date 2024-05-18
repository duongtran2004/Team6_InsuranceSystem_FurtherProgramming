package Entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class ActionHistory implements Serializable {

    //attributes
    private String actionId;
    private String actionDescription;
    private Timestamp time;

    //constructor

    public ActionHistory(String actionId, String actionDescription, Timestamp time) {
        this.actionId = actionId;
        this.actionDescription = actionDescription;
        this.time = time;
    }


    //getter setter

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }


    //toString

    @Override
    public String   toString() {
        return "ActionHistory{" +
                "actionId='" + actionId + '\'' +
                ", content='" + actionDescription + '\'' +
                ", time=" + time +
                '}';
    }
}
