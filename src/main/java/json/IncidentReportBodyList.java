package json;

import java.util.ArrayList;
import java.util.List;

public class IncidentReportBodyList {
    String incidentOriginator;
    String incidentID;
    String language;
    String incidentCategory;
    String incidentType;
    String priority;
    String severity;
    String certainty;
    String startTimeUTC;
    String expirationTimeUTC;
    String title;
    String description;
    List<String> analysisTasks;
    boolean EvacuationStop;
    PositionDA position;
    List<Attachment> attachments = new ArrayList<>();

    public IncidentReportBodyList(){
    }

    public List<Attachment> getAttachments(){
        return attachments;
    }

    public PositionDA getPosition(){
        return position;
    }
    public String getIncidentID(){
        return incidentID;
    }

    public String getLanguage(){
        return language;
    }

    public String getDescription(){
        return description;
    }

    public String getIncidentType(){
        return incidentType;
    }

    public List<String> getAnalysisTasks(){ return analysisTasks;}
    public boolean getEvacuationStop(){ return EvacuationStop; }
}
