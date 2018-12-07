/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andreadisst
 */
public class IncidentReportBody {
    
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
    Position position;
    List<Attachment> attachments = new ArrayList<>();
    
    public IncidentReportBody(){
        
    }
    
    public List<Attachment> getAttachments(){
        return attachments;
    }
    
    public Position getPosition(){
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
    
    public List<String> getAnalysisTasks(){
        return analysisTasks;
    }
    
}
